package com.lwkandroid.lib.common.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lwkandroid.lib.common.R;
import com.lwkandroid.lib.common.mvp.MvpBaseActivity;
import com.lwkandroid.lib.common.permission.AndPermissionHelper;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

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
        AndPermission.with(this)
                .runtime()
                .permission(Permission.CAMERA)
                .rationale(AndPermissionHelper.getRuntimeDeniedRationale())
                .onDenied(AndPermissionHelper.getRuntimeDeniedAction())
                .onGranted(new Action<List<String>>()
                {
                    @Override
                    public void onAction(List<String> data)
                    {

                    }
                });
    }
}
