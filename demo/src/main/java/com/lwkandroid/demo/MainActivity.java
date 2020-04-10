package com.lwkandroid.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lwkandroid.demo.pop.PopActivity;
import com.lwkandroid.demo.rxhttp.RxHttpActivity;
import com.lwkandroid.lib.common.mvp.MvpBaseActivity;

import androidx.annotation.Nullable;

/**
 * Description:View层
 *
 * @author
 * @date
 */
public class MainActivity extends MvpBaseActivity<MainPresenter> implements MainContract.IView<MainPresenter>
{

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
        addClick(R.id.btn_main_01);
        addClick(R.id.btn_main_02);
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
            case R.id.btn_main_01:
                startActivity(new Intent(MainActivity.this, RxHttpActivity.class));
                break;
            case R.id.btn_main_02:
                startActivity(new Intent(MainActivity.this, PopActivity.class));
                break;
            default:
                break;
        }
    }

}
