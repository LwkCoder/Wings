package com.lwkandroid.app.project.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lwkandroid.app.R;
import com.lwkandroid.app.app.AppBaseActivity;

/**
 * 开屏页View层
 */
public class SplashActivity extends AppBaseActivity<SplashPresenter> implements SplashContract.IView
{
    @Override
    protected void getIntentData(Intent intent, boolean newIntent)
    {

    }

    @Override
    protected int getContentViewId()
    {
        return R.layout.activity_splash;
    }

    @Override
    protected void initUI(View contentView)
    {

    }

    @Override
    protected void initData(Bundle savedInstanceState)
    {

    }

    @Override
    public void onClick(int id, View view)
    {

    }
}
