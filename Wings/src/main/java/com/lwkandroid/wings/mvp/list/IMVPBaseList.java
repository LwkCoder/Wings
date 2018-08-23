package com.lwkandroid.wings.mvp.list;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lwkandroid.rcvadapter.RcvMultiAdapter;
import com.lwkandroid.wings.mvp.base.IMVPBaseView;

import java.util.List;

/**
 * Created by LWK
 * TODO 定义列表界面公共逻辑方法
 */

public interface IMVPBaseList
{
    interface IViewCommon<D> extends IMVPBaseView
    {
        void onRefreshSuccess(int pageIndex, List<D> dataList);

        void onRefreshFail(String errorMsg);

        void onLoadMoreSuccess(int pageIndex, List<D> dataList);

        void onLoadMoreFail(String errorMsg);

        MVPListOptions getListOptions();

        SwipeRefreshLayout getRefreshLayout();

        RecyclerView getRecyclerView();

        RcvMultiAdapter<D> getAdapter();
    }

    interface IViewSubClass<D>
    {
        MVPListOptions setListOptions();

        RcvMultiAdapter<D> setAdapter();

        void requestRefresh();

        void requestLoadMore();
    }

    interface ILogicCommon<D>
    {
        void init(MVPListOptions options, View contentView, RcvMultiAdapter<D> adapter);
    }

}
