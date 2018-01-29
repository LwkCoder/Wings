package com.lwkandroid.wingsdemo.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lwkandroid.wingsdemo.R;
import com.lwkandroid.wingsdemo.app.AppBaseActivity;
import com.lwkandroid.wingsdemo.project.rxhttp.RxHttpDemoActivity;

/**
 * RxHttpDemoActivity
 */
public class MainActivity extends AppBaseActivity<MainPresenter> implements MainConstract.View
{
    @Override
    public int getContentViewId()
    {
        return R.layout.activity_main;
    }

    @Override
    protected void initUI(View contentView)
    {
        addClick(R.id.btn_main_rxhttp, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, RxHttpDemoActivity.class));
            }
        });
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {
    }

    @Override
    protected void onClick(int id, View v)
    {
    }

    @Override
    protected MainPresenter createPresenter()
    {
        return new MainPresenter();
    }
}
