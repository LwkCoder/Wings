package com.sources.javacode.project.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sources.javacode.R;
import com.sources.javacode.app.AppBaseActivity;

/**
 * Created by LWK
 *  View层
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
