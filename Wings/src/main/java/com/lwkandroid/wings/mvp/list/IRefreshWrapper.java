package com.lwkandroid.wings.mvp.list;

/**
 * Created by LWK
 * TODO 下拉刷新控件的接口
 */

public interface IRefreshWrapper<R>
{
    void wrap(R r);

    void enableRefresh(boolean enable);

    void autoRefresh();

    void finishRefresh();

    R getRefreshView();

    void onDestroy();

    void setOnRefreshListener(onRefreshListener listener);

    interface onRefreshListener
    {
        void onRefreshRequest();
    }
}
