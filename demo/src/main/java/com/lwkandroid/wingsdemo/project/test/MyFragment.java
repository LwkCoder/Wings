package com.lwkandroid.wingsdemo.project.test;

import android.os.Bundle;
import android.view.View;

import com.lwkandroid.wingsdemo.R;
import com.lwkandroid.wingsdemo.app.AppBaseFragment;

import androidx.annotation.Nullable;

/**
 * Description:View层
 *
 * @author
 * @date
 */
public class MyFragment extends AppBaseFragment<MyFragmentPresenter> implements MyFragmentContract.IView
{

    /**
     * 创建该Fragment的静态方法
     */
    public static MyFragment createInstance()
    {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    protected void getArgumentsData(Bundle bundle, Bundle savedInstanceState)
    {

    }

    @Override
    protected int getContentViewId()
    {
        return R.layout.fragment_test;
    }

    @Override
    protected void initUI(View contentView)
    {

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
