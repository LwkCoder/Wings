package com.lwkandroid.wings.mvp.list;

import android.view.View;

import com.lwkandroid.rcvadapter.RcvMultiAdapter;
import com.lwkandroid.wings.mvp.base.IMVPBaseView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by LWK
 * TODO 定义列表界面公共逻辑方法
 */

public interface IMVPBaseList
{
    interface IViewCommon<D> extends IMVPBaseView
    {
        void autoRefresh();

        void onRefreshSuccess(int pageIndex, List<D> dataList);

        void onRefreshFail(String errorMsg);

        void onLoadMoreSuccess(int pageIndex, List<D> dataList);

        void onLoadMoreFail(String errorMsg);

        MVPListOptions getListOptions();

        IRefreshWrapper getRefreshWrapper();

        RecyclerView getRecyclerView();

        RcvMultiAdapter<D> getAdapter();
    }

    interface IViewSubClass<D>
    {
        MVPListOptions setListOptions();

        RcvMultiAdapter<D> setAdapter();

        IRefreshWrapper findRefreshView(MVPListOptions options, View contentView);

        RecyclerView findRecyclerView(MVPListOptions options, View contentView);
    }

    interface ILogicCommon<D>
    {
        IRefreshWrapper findRefreshView(MVPListOptions options, View contentView);

        RecyclerView findRecyclerView(MVPListOptions options, View contentView);

        void init(MVPListOptions options, View contentView,
                  IRefreshWrapper refreshView, RecyclerView recyclerView,
                  RcvMultiAdapter<D> adapter);

        void onDestroy();
    }

}
