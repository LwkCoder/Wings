package com.sources.javacode.project.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sources.javacode.R;
import com.sources.javacode.app.AppBaseActivity;

/**
 * Created by LWK
 * TODO View层
 */
public class HomeActivity extends AppBaseActivity<HomePresenter> implements HomeContract.IView
{

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
