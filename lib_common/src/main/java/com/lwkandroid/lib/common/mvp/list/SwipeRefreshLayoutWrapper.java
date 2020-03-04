package com.lwkandroid.lib.common.mvp.list;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Description:SwipeRefreshLayout实现的下拉刷新机制
 *
 * @author LWK
 * @date 2020/3/2
 */
public class SwipeRefreshLayoutWrapper implements IRefreshWrapper<SwipeRefreshLayout>, SwipeRefreshLayout.OnRefreshListener
{
    private SwipeRefreshLayout mRefreshLayout;
    private OnRefreshRequestedListener mRefreshListener;

    public SwipeRefreshLayoutWrapper(SwipeRefreshLayout layout)
    {
        wrapRefreshView(layout);
    }

    @Override
    public void wrapRefreshView(SwipeRefreshLayout view)
    {
        mRefreshLayout = view;
    }

    @Override
    public SwipeRefreshLayout getRefreshView()
    {
        return mRefreshLayout;
    }

    @Override
    public void enableRefresh(boolean enable)
    {
        if (mRefreshLayout != null)
        {
            mRefreshLayout.setEnabled(enable);
        }
    }

    @Override
    public void autoRefresh()
    {
        callRefreshing();
        onRefresh();
    }

    @Override
    public void callRefreshing()
    {
        if (mRefreshLayout != null)
        {
            mRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void callRefreshSuccess()
    {
        //SwipeRefreshLayout没有区分成功或失败的状态
        if (mRefreshLayout != null)
        {
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void callRefreshFail(Throwable throwable)
    {
        //SwipeRefreshLayout没有区分成功或失败的状态
        if (mRefreshLayout != null)
        {
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void setOnRefreshRequestedListener(OnRefreshRequestedListener listener)
    {
        mRefreshListener = listener;
        if (mRefreshLayout != null)
        {
            mRefreshLayout.setOnRefreshListener(this);
        }
    }

    @Override
    public void onRefresh()
    {
        if (mRefreshListener != null)
        {
            mRefreshListener.onRefreshRequested();
        }
    }
}
