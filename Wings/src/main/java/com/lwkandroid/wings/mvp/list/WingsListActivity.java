package com.lwkandroid.wings.mvp.list;

import android.os.Bundle;
import android.view.View;

import com.lwkandroid.rcvadapter.RcvMultiAdapter;
import com.lwkandroid.wings.mvp.base.MVPBasePresenter;
import com.lwkandroid.wings.mvp.base.WingsBaseActivity;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 列表界面基类
 *
 * @author LWK
 */
public abstract class WingsListActivity<P extends MVPBasePresenter, RV, D> extends WingsBaseActivity<P> implements
        IMVPListContract.IViewCommon<D>, IMVPListContract.IViewSpecial<RV, D>, MVPListImpl.Listener
{
    private MVPListImpl<RV, D> mListImpl;

    @Override
    protected void initUI(View contentView)
    {
        mListImpl = new MVPListImpl<RV, D>(this);
        mListImpl.init(setListOptions(), contentView, findRefreshWrapper(getListOptions(), contentView)
                , findRecyclerView(getListOptions(), contentView), setAdapter());
        subInitUI(contentView);
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

    @Override
    public IRefreshWrapper<RV> getRefreshWrapper()
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

    /**
     * 子类初始化UI的方法
     *
     * @param contentView 布局
     */
    protected abstract void subInitUI(View contentView);
}
