package com.lwkandroid.wingsdemo.project.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lwkandroid.wingsdemo.R;
import com.lwkandroid.wingsdemo.app.AppBaseActivity;

import androidx.annotation.Nullable;

/**
 * Description:View层
 *
 * @author
 * @date
 */
public class TestActivity extends AppBaseActivity
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
        MyFragment fragment = MyFragment.createInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_test_container, fragment)
                .commit();
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

}
