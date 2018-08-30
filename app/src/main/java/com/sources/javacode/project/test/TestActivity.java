package com.sources.javacode.project.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sources.javacode.R;
import com.sources.javacode.app.AppBaseActivity;

/**
 * View层
 */
public class TestActivity extends AppBaseActivity<TestPresenter> implements TestContract.IView
{
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
