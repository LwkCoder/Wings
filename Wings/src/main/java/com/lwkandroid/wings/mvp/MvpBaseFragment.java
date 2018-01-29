package com.lwkandroid.wings.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lwkandroid.wings.app.BaseFragment;

/**
 * MVP模版的Fragment基类
 */
public abstract class MvpBaseFragment<P extends BasePresenter> extends BaseFragment
{
    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (this instanceof IBaseView)
        {
            mPresenter = createPresenter();
            mPresenter.attachToView((IBaseView) this);
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestory();
    }

    public Activity getActivityContext()
    {
        return getActivity();
    }

    /**
     * 创建Presenter
     */
    protected abstract P createPresenter();
}
