package com.lwkandroid.wings.mvp.list;

import androidx.recyclerview.widget.RecyclerView;
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

        IRefreshView getRefreshLayout();

        RecyclerView getRecyclerView();

        RcvMultiAdapter<D> getAdapter();
    }

    interface IViewSubClass<D>
    {
        MVPListOptions setListOptions();

        RcvMultiAdapter<D> setAdapter();

        IRefreshView findRefreshView(MVPListOptions options, View contentView);

        RecyclerView findRecyclerView(MVPListOptions options, View contentView);

        void requestRefresh();

        void requestLoadMore();
    }

    interface ILogicCommon<D>
    {
        IRefreshView findRefreshView(MVPListOptions options, View contentView);

        RecyclerView findRecyclerView(MVPListOptions options, View contentView);

        void init(MVPListOptions options, View contentView,
                  IRefreshView refreshView, RecyclerView recyclerView,
                  RcvMultiAdapter<D> adapter);

        void onDestroy();
    }

}
