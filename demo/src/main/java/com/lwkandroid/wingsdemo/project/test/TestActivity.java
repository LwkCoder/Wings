package com.lwkandroid.wingsdemo.project.test;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.lwkandroid.wings.utils.StringUtils;
import com.lwkandroid.wingsdemo.R;
import com.lwkandroid.wingsdemo.app.AppBaseActivity;

/**
 * View层
 */
public class TestActivity extends AppBaseActivity<TestPresenter> implements TestContract.IView
{
    private EditText mEdIp, mEdPort, mEdMessage;
    private UDPSendThread mSendThread;
    private UDPReceivedThread mReceivedThread;

    @Override
    protected void getIntentData(Intent intent, boolean newIntent)
    {
    }

    @Override
    protected int getContentViewId()
    {
        return R.layout.activity_test;
    }

    @Override
    protected void initUI(View contentView)
    {
        mEdIp = find(R.id.ed_test_ip);
        mEdPort = find(R.id.ed_test_port);
        mEdMessage = find(R.id.ed_test_message);

        addClick(R.id.btn_test_send, R.id.btn_test_start);
    }

    @Override
    protected void initData(Bundle savedInstanceState)
    {
        mEdIp.setText(getlocalip());
    }

    private String getlocalip()
    {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        if (ipAddress == 0)
            return "";
        return ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "."
                + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
    }

    @Override
    public void onClick(int id, View view)
    {
        switch (id)
        {
            case R.id.btn_test_send:
                String message = mEdMessage.getText().toString();
                if (StringUtils.isEmpty(message))
                {
                    showShortToast("请输入消息");
                    return;
                }
                mSendThread.addMessage(message);
                break;
            case R.id.btn_test_start:
                String ip = mEdIp.getText().toString().trim();
                String port = mEdPort.getText().toString().trim();
                if (StringUtils.isEmpty(ip))
                {
                    showShortToast("请填写ip");
                    return;
                }
                if (StringUtils.isEmpty(port))
                {
                    showShortToast("请填写port");
                    return;
                }
                mSendThread = new UDPSendThread(ip, Integer.valueOf(port));
                mSendThread.start();
                mReceivedThread = new UDPReceivedThread(ip, Integer.valueOf(port));
                mReceivedThread.start();
                break;
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (mSendThread != null)
            mSendThread.onDestory();
        if (mReceivedThread != null)
            mReceivedThread.onDestory();
    }
}
