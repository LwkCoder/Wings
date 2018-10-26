package com.lwkandroid.wings.mvp.list;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Created by LWK
 * TODO SwipeRefreshLayout的封装实现
 */

public class SwipeRefreshWrapper implements IRefreshView<SwipeRefreshLayout>, SwipeRefreshLayout.OnRefreshListener
{
    private SwipeRefreshLayout mRefreshLayout;
    private onRefreshListener mListener;

    public SwipeRefreshWrapper(SwipeRefreshLayout layout)
    {
        wrap(layout);
    }

    @Override
    public void wrap(SwipeRefreshLayout swipeRefreshLayout)
    {
        this.mRefreshLayout = swipeRefreshLayout;
        mRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void enableRefresh(boolean enable)
    {
        if (getRefreshView() != null)
            getRefreshView().setRefreshing(enable);
    }

    @Override
    public SwipeRefreshLayout getRefreshView()
    {
        return mRefreshLayout;
    }

    @Override
    public void setOnRefreshListener(onRefreshListener listener)
    {
        mListener = listener;
    }

    @Override
    public void onDestroy()
    {
        mListener = null;
    }

    @Override
    public void onRefresh()
    {
        if (mListener != null)
            mListener.onRefreshRequest();
    }
}
