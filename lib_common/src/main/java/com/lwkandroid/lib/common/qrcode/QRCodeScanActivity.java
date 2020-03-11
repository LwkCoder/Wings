package com.lwkandroid.lib.common.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lwkandroid.lib.common.R;
import com.lwkandroid.lib.common.mvp.MvpBaseActivity;

import androidx.annotation.Nullable;

/**
 * Description:View层
 *
 * @author
 * @date
 */
public class QRCodeScanActivity extends MvpBaseActivity<QRCodeScanPresenter> implements QRCodeScanContract.IView<QRCodeScanPresenter>
{

    @Override
    protected QRCodeScanPresenter createPresenter()
    {
        return new QRCodeScanPresenter(this, new QRCodeScanModel());
    }


    @Override
    protected void getIntentData(Intent intent, boolean newIntent)
    {

    }

    @Override
    protected int getContentViewId()
    {
        return R.layout.activity_qrcode_scan;
    }

    @Override
    protected void initUI(View contentView)
    {

    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {

    }

    @Override
    public void onClick(int id, View view)
    {
        switch (id)
        {
            default:
                break;
        }
    }

    private void requestPermission()
    {

    }
}
