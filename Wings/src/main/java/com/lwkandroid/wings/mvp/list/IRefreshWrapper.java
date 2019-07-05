package com.lwkandroid.wings.mvp.list;

/**
 * 下拉刷新控件的接口
 *
 * @author LWK
 */
public interface IRefreshWrapper<RV>
{
    void wrap(RV rv);

    void enableRefresh(boolean enable);

    void autoRefresh();

    void finishRefresh();

    RV getRefreshView();

    void onDestroy();

    void setOnRefreshListener(onRefreshListener listener);

    interface onRefreshListener
    {
        void onRefreshRequest();
    }
}
