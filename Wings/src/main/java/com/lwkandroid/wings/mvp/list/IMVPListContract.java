package com.lwkandroid.wings.mvp.list;

import android.view.View;

import com.lwkandroid.rcvadapter.RcvMultiAdapter;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by LWK
 *  定义列表界面公共方法
 */

public interface IMVPListContract
{
    interface IViewCommon<D>
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

    interface IViewSpecial<RV, D>
    {
        MVPListOptions setListOptions();

        IRefreshWrapper<RV> findRefreshWrapper(MVPListOptions options, View contentView);

        RecyclerView findRecyclerView(MVPListOptions options, View contentView);

        RcvMultiAdapter<D> setAdapter();
    }
}
