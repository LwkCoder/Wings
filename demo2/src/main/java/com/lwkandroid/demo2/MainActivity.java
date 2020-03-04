package com.lwkandroid.demo2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lwkandroid.demo2.test.TestActivity;
import com.lwkandroid.lib.common.mvp.MvpBaseActivity;

/**
 * Description:
 *
 * @author 20180004
 * @date 2020/1/13
 */
public class MainActivity extends MvpBaseActivity<MainPresenter> implements IMainContract.IView<MainPresenter>
{

    @Override
    protected void setBarColor()
    {

    }

    @Override
    protected MainPresenter createPresenter()
    {
        return new MainPresenter(this, new MainModel());
    }

    @Override
    protected void getIntentData(Intent intent, boolean newIntent)
    {

    }

    @Override
    protected int getContentViewId()
    {
        return R.layout.activity_main;
    }

    @Override
    protected void initUI(View contentView)
    {
        startActivity(new Intent(MainActivity.this, TestActivity.class));
    }

    @Override
    protected void initData(Bundle savedInstanceState)
    {

    }

    @Override
    public void onClick(int id, View view)
    {

    }

    @Override
    public void testRefresh()
    {
        //...
    }
}
