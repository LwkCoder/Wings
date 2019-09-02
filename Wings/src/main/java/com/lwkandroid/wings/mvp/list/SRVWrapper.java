package com.lwkandroid.wings.mvp.list;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Created by LWK
 *  SwipeRefreshLayout的封装实现
 */

public class SRVWrapper implements IRefreshWrapper<SwipeRefreshLayout>, SwipeRefreshLayout.OnRefreshListener
{
    private SwipeRefreshLayout mRefreshLayout;
    private OnRefreshListener mListener;

    public SRVWrapper(SwipeRefreshLayout layout)
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
        {
            getRefreshView().setEnabled(enable);
        }
    }

    @Override
    public void autoRefresh()
    {
        if (getRefreshView() != null)
        {
            getRefreshView().setRefreshing(true);
        }
        onRefresh();
    }

    @Override
    public void finishRefresh()
    {
        if (getRefreshView() != null)
        {
            getRefreshView().setRefreshing(false);
        }
    }

    @Override
    public SwipeRefreshLayout getRefreshView()
    {
        return mRefreshLayout;
    }

    @Override
    public void setOnRefreshListener(OnRefreshListener listener)
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
        {
            mListener.onRefreshRequest();
        }
    }
}
