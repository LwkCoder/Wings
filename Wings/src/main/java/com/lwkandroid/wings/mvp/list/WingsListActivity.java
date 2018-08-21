package com.lwkandroid.wings.mvp.list;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lwkandroid.rcvadapter.RcvMultiAdapter;
import com.lwkandroid.wings.mvp.base.MVPBasePresenter;
import com.lwkandroid.wings.mvp.base.WingsBaseActivity;

import java.util.List;

/**
 * Created by LWK
 * TODO 列表界面基类
 * [xml中include布局layout_common_list]
 */

public abstract class WingsListActivity<P extends MVPBasePresenter, D> extends WingsBaseActivity<P> implements
        IWingsList.ICommon<D>, IWingsList.IView<D>, WingsListImpl.Listener
{
    private WingsListImpl<D> mListImpl = new WingsListImpl<>(this);

    @Override
    protected void initUI(View contentView)
    {
        mListImpl.init(setListOptions(), contentView, setAdapter());
        _initUI(contentView);
    }

    @Override
    protected void initData(Bundle savedInstanceState)
    {
        requestRefresh();
    }

    @Override
    public void requestRefresh()
    {
        mListImpl.onRefresh();
    }

    @Override
    public void onRefreshSuccess(int pageIndex, List<D> dataList)
    {
        mListImpl.onRefreshSuccess(pageIndex, dataList);
    }

    @Override
    public void onRefreshFail(String errorMsg)
    {
        mListImpl.onRefreshFail(errorMsg);
    }

    @Override
    public void requestLoadMore()
    {
        mListImpl.onLoadMoreRequest();
    }

    @Override
    public void onLoadMoreSuccess(int pageIndex, List<D> dataList)
    {
        mListImpl.onLoadMoreSuccess(pageIndex, dataList);
    }

    @Override
    public void onLoadMoreFail(String errorMsg)
    {
        mListImpl.onLoadMoreFail(errorMsg);
    }

    @Override
    public WingsListOptions getListOptions()
    {
        return mListImpl.getListOptions();
    }

    @Override
    public SwipeRefreshLayout getRefreshLayout()
    {
        return mListImpl.getRefreshLayout();
    }

    @Override
    public RecyclerView getRecyclerView()
    {
        return mListImpl.getRecyclerView();
    }

    @Override
    public RcvMultiAdapter<D> getAdapter()
    {
        return mListImpl.getAdapter();
    }

    protected abstract void _initUI(View contentView);
}
