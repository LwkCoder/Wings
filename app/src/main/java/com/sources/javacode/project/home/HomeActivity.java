package com.sources.javacode.project.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lwkandroid.lib.common.mvp.MvpBaseActivity;
import com.lwkandroid.lib.common.qrcode.QRCodeScanHelper;
import com.sources.javacode.R;

import androidx.annotation.Nullable;

/**
 * Description:View层
 *
 * @author
 * @date
 */
public class HomeActivity extends MvpBaseActivity<HomePresenter> implements HomeContract.IView<HomePresenter>
{

    @Override
    protected HomePresenter createPresenter()
    {
        return new HomePresenter(this, new HomeModel());
    }


    @Override
    protected void getIntentData(Intent intent, boolean newIntent)
    {

    }

    @Override
    protected int getContentViewId()
    {
        return R.layout.activity_home;
    }

    @Override
    protected void initUI(View contentView)
    {
        addClick(R.id.btn_home_test);
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
            case R.id.btn_home_test:
                QRCodeScanHelper.startScanQRCode(this, 100);
                break;
            default:
                break;
        }
    }

}
