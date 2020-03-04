package com.lwkandroid.demo2.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lwkandroid.demo2.R;
import com.lwkandroid.lib.common.mvp.MvpBaseActivity;
import com.lwkandroid.lib.common.mvp.list.ILoadMoreWrapper;
import com.lwkandroid.lib.common.mvp.list.IRefreshWrapper;
import com.lwkandroid.lib.common.mvp.list.RcvLoadMoreWrapper;
import com.lwkandroid.lib.common.mvp.list.SwipeRefreshLayoutWrapper;
import com.lwkandroid.rcvadapter.RcvMultiAdapter;
import com.lwkandroid.rcvadapter.RcvSingleAdapter;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Description:View层
 *
 * @author
 * @date
 */
public class TestActivity extends MvpBaseActivity<TestPresenter> implements TestContract.IView<TestPresenter>,
        IRefreshWrapper.OnRefreshRequestedListener, ILoadMoreWrapper.OnLoadMoreRequestedListener
{
    private IRefreshWrapper<SwipeRefreshLayout> mRefreshWrapper;
    private ILoadMoreWrapper<RcvMultiAdapter> mLoadMoreWrapper;
    private RecyclerView mRecyclerView;
    private RcvSingleAdapter<String> mAdapter;

    @Override
    protected void setBarColor()
    {

    }

    @Override
    protected TestPresenter createPresenter()
    {
        return new TestPresenter(this, new TestModel());
    }


    @Override
    protected void getIntentData(Intent intent, boolean newIntent)
    {

    }

    @Override
    protected int getContentViewId()
    {
        return R.layout.activity_test;
    }

    @Override
    protected void initUI(View contentView)
    {
        //关联刷新控件，由包装类代理
        mRefreshWrapper = new SwipeRefreshLayoutWrapper(find(R.id.swipeRefreshLayout));
        mRecyclerView = find(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mAdapter = new TestAdapter(this);
        //关联自动加载，由包装类代理
        mLoadMoreWrapper = new RcvLoadMoreWrapper(mAdapter);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {
        //默认开启下拉刷新功能
        mRefreshWrapper.enableRefresh(isRefreshEnable());
        mRefreshWrapper.setOnRefreshRequestedListener(this);
        //默认自动刷新一次
        autoRefresh();
    }

    @Override
    public void onClick(int id, View view)
    {
        switch (id)
        {
            default:
                break;
        }
    }

    @Override
    public boolean isRefreshEnable()
    {
        return true;
    }

    @Override
    public boolean isLoadMoreEnable()
    {
        return true;
    }

    @Override
    public IRefreshWrapper getRefreshWrapper()
    {
        return mRefreshWrapper;
    }

    @Override
    public ILoadMoreWrapper getLoadMoreWrapper()
    {
        return mLoadMoreWrapper;
    }

    @Override
    public void autoRefresh()
    {
        mRefreshWrapper.autoRefresh();
    }

    @Override
    public void onRefreshRequested()
    {
        //下拉刷新的时候禁止自动加载
        mLoadMoreWrapper.enableLoadMore(false);
        getPresenter().requestRefresh();
    }

    @Override
    public void onRefreshSuccess(List<String> dataList)
    {
        mRefreshWrapper.callRefreshSuccess();
        mAdapter.refreshDatas(dataList);
        //配置自动加载
        mLoadMoreWrapper.enableLoadMore(isLoadMoreEnable());
        mLoadMoreWrapper.setOnLoadMoreRequestedListener(this);
    }

    @Override
    public void onRefreshFail(String message, Throwable throwable)
    {
        mRefreshWrapper.callRefreshFail(throwable);
        //去做具体提示
        showShortToast(message);
    }

    @Override
    public void onLoadMoreRequested()
    {
        getPresenter().requestLoadMore();
    }

    @Override
    public void onLoadMoreSuccess(List<String> dataList)
    {
        mLoadMoreWrapper.callLoadMoreSuccess();
        //由于自动加载是Adapter实现的，没有只显示状态的方法
        //所以这里需要处理加载后的数据
        mAdapter.notifyLoadMoreSuccess(dataList, true);
    }

    @Override
    public void onLoadMoreFail(String message, Throwable throwable)
    {
        mLoadMoreWrapper.callLoadMoreFail(throwable);
        //去做具体提示
        showShortToast(message);
    }

    @Override
    public void onLoadMoreNoMoreData()
    {
        mLoadMoreWrapper.callLoadMoreNoMoreData();
    }
}
