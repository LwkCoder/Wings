package com.lwkandroid.wings.mvp.list;

/**
 * Created by LWK
 * TODO 下拉刷新控件的接口
 */

public interface IRefreshView<R>
{
    void wrap(R r);

    void enableRefresh(boolean enable);

    R getRefreshView();

    void setOnRefreshListener(onRefreshListener listener);

    void onDestroy();

    interface onRefreshListener
    {
        void onRefreshRequest();
    }
}
