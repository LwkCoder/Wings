package com.lwkandroid.wings.mvp.list;

import com.lwkandroid.wings.widget.ptr.PTRLayout;

/**
 * Created by LWK
 * TODO PTRLayout的封装实现
 */
public class PTRWrapper implements IRefreshWrapper<PTRLayout>, PTRLayout.OnPullListener
{
    private PTRLayout mPTRlayout;
    private onRefreshListener mListener;

    public PTRWrapper(PTRLayout layout)
    {
        wrap(layout);
    }

    @Override
    public void wrap(PTRLayout layout)
    {
        this.mPTRlayout = layout;
        mPTRlayout.setOnPullListener(this);
    }

    @Override
    public void enableRefresh(boolean enable)
    {
        if (getRefreshView() != null)
            getRefreshView().setEnabled(enable);
    }

    @Override
    public void autoRefresh()
    {
        // 自动刷新
        mPTRlayout.autoRefresh();
    }

    @Override
    public void finishRefresh()
    {
        //结束刷新
        if (getRefreshView() != null)
            getRefreshView().finishRefresh();
    }

    @Override
    public PTRLayout getRefreshView()
    {
        return mPTRlayout;
    }

    @Override
    public void setOnRefreshListener(onRefreshListener listener)
    {
        this.mListener = listener;
    }

    @Override
    public void onDestroy()
    {
        this.mListener = null;
    }

    @Override
    public void onMoveTarget(int offset)
    {

    }

    @Override
    public void onMoveRefreshView(int offset)
    {

    }

    @Override
    public void onRefresh()
    {
        if (mListener != null)
            mListener.onRefreshRequest();
    }
}
