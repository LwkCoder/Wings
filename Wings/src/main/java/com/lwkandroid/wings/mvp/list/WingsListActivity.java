package com.lwkandroid.wings.mvp.list;

import android.os.Bundle;
import android.view.View;

import com.lwkandroid.rcvadapter.RcvMultiAdapter;
import com.lwkandroid.wings.mvp.base.MVPBasePresenter;
import com.lwkandroid.wings.mvp.base.WingsBaseActivity;
import com.lwkandroid.wings.widget.ptr.PTRLayout;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by LWK
 * TODO 列表界面基类
 * [xml中include布局layout_common_list]
 */

public abstract class WingsListActivity<P extends MVPBasePresenter, D> extends WingsBaseActivity<P> implements
        IMVPBaseList.IViewCommon<D>, IMVPBaseList.IViewSubClass<D>, MVPListImpl.Listener
{
    //    private SRVMVPListImpl<D> mListImpl;
    private PTRMVPListImpl<D> mListImpl;

    @Override
    protected void initUI(View contentView)
    {
        //        mListImpl = new SRVMVPListImpl<>(this);
        mListImpl = new PTRMVPListImpl<>(this);
        mListImpl.init(setListOptions(), contentView, findRefreshView(getListOptions(), contentView)
                , findRecyclerView(getListOptions(), contentView), setAdapter());
        _initUI(contentView);
    }

    @Override
    public IRefreshWrapper findRefreshView(MVPListOptions options, View contentView)
    {
        return mListImpl.findRefreshView(options, contentView);
    }

    @Override
    public RecyclerView findRecyclerView(MVPListOptions options, View contentView)
    {
        return mListImpl.findRecyclerView(options, contentView);
    }

    @Override
    protected void initData(Bundle savedInstanceState)
    {
        autoRefresh();
    }

    @Override
    public void autoRefresh()
    {
        mListImpl.autoRefresh();
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
    public MVPListOptions getListOptions()
    {
        return mListImpl.getListOptions();
    }

    //    @Override
    //    public IRefreshWrapper<SwipeRefreshLayout> getRefreshWrapper()
    //    {
    //        return mListImpl.getRefreshWrapper();
    //    }

    @Override
    public IRefreshWrapper<PTRLayout> getRefreshWrapper()
    {
        return mListImpl.getRefreshWrapper();
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

    @Override
    protected void onDestroy()
    {
        mListImpl.onDestroy();
        super.onDestroy();
    }

    protected abstract void _initUI(View contentView);
}
