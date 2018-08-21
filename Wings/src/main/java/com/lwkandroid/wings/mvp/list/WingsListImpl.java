package com.lwkandroid.wings.mvp.list;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lwkandroid.rcvadapter.RcvMultiAdapter;
import com.lwkandroid.rcvadapter.listener.RcvLoadMoreListener;
import com.lwkandroid.rcvadapter.ui.RcvDefLoadMoreView;
import com.lwkandroid.wings.R;
import com.lwkandroid.wings.mvp.base.MVPBaseViewImpl;
import com.lwkandroid.wings.utils.StringUtils;
import com.lwkandroid.wings.utils.ToastUtils;

import java.util.List;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by LWK
 * TODO 列表界面公共部分实现类
 */

class WingsListImpl<D> implements IWingsList.ILogic<D>, IWingsList.ICommon<D>,
        SwipeRefreshLayout.OnRefreshListener, RcvLoadMoreListener
{
    private MVPBaseViewImpl mBaseViewImpl = new MVPBaseViewImpl();
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private WingsListOptions mOptions;
    private RcvMultiAdapter<D> mAdapter;
    private int mCurrentIndex;
    private Listener mListener;

    public WingsListImpl(Listener listener)
    {
        this.mListener = listener;
    }

    @Override
    public void init(WingsListOptions options, View contentView, RcvMultiAdapter<D> adapter)
    {
        mOptions = options;
        mAdapter = adapter;
        mRefreshLayout = contentView.findViewById(R.id.srl_common);
        mRecyclerView = contentView.findViewById((R.id.rcv_common));

        if (mRefreshLayout == null)
            throw new IllegalStateException("RefreshLayout can not be null! You can include the R.layout.layout_common_list into your XML.");
        if (mRecyclerView == null)
            throw new IllegalStateException("RecyclerView can not be null! You can include the R.layout.layout_common_list into your XML.");
        if (mAdapter == null)
            throw new IllegalStateException("RecyclerView's Adapter can not be null!");
        if (mOptions.getLoadMoreView() == null)
            mOptions.setLoadMoreView(new RcvDefLoadMoreView(contentView.getContext()));

        mRefreshLayout.setEnabled(getListOptions().isEnableRefresh());
        mRefreshLayout.setOnRefreshListener(this);
        mAdapter.enableLoadMore(false);//首先先禁止，刷新后再配置
        mRecyclerView.setLayoutManager(getListOptions().getLayoutManager());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh()
    {
        mAdapter.enableLoadMore(false);
        mRefreshLayout.setRefreshing(true);
        if (mListener != null)
            mListener.doRefresh(System.currentTimeMillis(), 0, mOptions.getPageSize());
    }

    @Override
    public void onRefreshSuccess(int pageIndex, List<D> dataList)
    {
        this.mCurrentIndex = pageIndex;
        mRefreshLayout.setRefreshing(false);
        mAdapter.refreshDatas(dataList);
        if (dataList == null || dataList.size() == 0)
            return;
        getListOptions().getLoadMoreView().handleLoadInit();
        mAdapter.enableLoadMore(getListOptions().isEnableLoadMore(), getListOptions().getLoadMoreView(), this);
        if (getListOptions().isEnableLoadMore() &&
                dataList.size() < getListOptions().getPageSize())
            mAdapter.notifyLoadMoreHasNoMoreData();
    }

    @Override
    public void onRefreshFail(String errorMsg)
    {
        mRefreshLayout.setRefreshing(false);
        if (StringUtils.isNotEmpty(errorMsg))
            ToastUtils.showShort(errorMsg);
    }

    @Override
    public void onLoadMoreRequest()
    {
        if (mListener != null)
            mListener.doLoadMore(System.currentTimeMillis(), mCurrentIndex + 1,
                    getListOptions().getPageSize(), mAdapter.getDataSize());
    }

    @Override
    public void onLoadMoreSuccess(int pageIndex, List<D> dataList)
    {
        this.mCurrentIndex = pageIndex;
        mAdapter.notifyLoadMoreSuccess(dataList,
                dataList != null && dataList.size() >= getListOptions().getPageSize());
    }

    @Override
    public void onLoadMoreFail(String errorMsg)
    {
        mAdapter.notifyLoadMoreFail();
        if (StringUtils.isNotEmpty(errorMsg))
            ToastUtils.showShort(errorMsg);
    }

    @Override
    public WingsListOptions getListOptions()
    {
        return mOptions == null ? new WingsListOptions() : mOptions;
    }

    @Override
    public SwipeRefreshLayout getRefreshLayout()
    {
        return mRefreshLayout;
    }

    @Override
    public RecyclerView getRecyclerView()
    {
        return mRecyclerView;
    }

    @Override
    public RcvMultiAdapter<D> getAdapter()
    {
        return mAdapter;
    }

    @Override
    public void showShortToast(int resId)
    {
        mBaseViewImpl.showShortToast(resId);
    }

    @Override
    public void showShortToast(CharSequence message)
    {
        mBaseViewImpl.showShortToast(message);
    }

    @Override
    public void showLongToast(int resId)
    {
        mBaseViewImpl.showLongToast(resId);
    }

    @Override
    public void showLongToast(CharSequence message)
    {
        mBaseViewImpl.showLongToast(message);
    }

    @Override
    public PublishSubject<Integer> getLifecycleSubject()
    {
        return mBaseViewImpl.getLifecycleSubject();
    }

    public interface Listener
    {
        void doRefresh(long timeStamp, int pageIndex, int pageSize);

        void doLoadMore(long timeStamp, int pageIndex, int pageSize, int currentDataCount);
    }
}
