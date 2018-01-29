package com.lwkandroid.wings.mvp;

import android.app.Activity;
import android.os.Bundle;

import com.lwkandroid.wings.app.BaseActivity;

/**
 * MVP模版的Activity基类
 */

public abstract class MvpBaseActivity<P extends BasePresenter> extends BaseActivity
{
    protected P mPresenter;

    @Override
    protected void beforeOnCreate(Bundle savedInstanceState)
    {
        super.beforeOnCreate(savedInstanceState);
        if (this instanceof IBaseView)
        {
            //创建Presenter
            mPresenter = createPresenter();
            mPresenter.attachToView((IBaseView) this);
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestory();
    }

    public Activity getActivityContext()
    {
        return this;
    }

    /**
     * 创建Presenter
     */
    protected abstract P createPresenter();
}
