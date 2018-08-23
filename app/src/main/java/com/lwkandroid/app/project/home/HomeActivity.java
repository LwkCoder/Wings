package com.lwkandroid.app.project.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lwkandroid.app.R;
import com.lwkandroid.app.app.AppBaseActivity;

/**
 * 主页View层
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
