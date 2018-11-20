package com.lwkandroid.wings.mvp.list;

import android.view.View;

import com.lwkandroid.rcvadapter.RcvMultiAdapter;
import com.lwkandroid.rcvadapter.listener.RcvLoadMoreListener;
import com.lwkandroid.rcvadapter.ui.RcvDefLoadMoreView;
import com.lwkandroid.wings.R;
import com.lwkandroid.wings.mvp.base.MVPBaseViewImpl;
import com.lwkandroid.wings.utils.StringUtils;
import com.lwkandroid.wings.utils.ToastUtils;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by LWK
 * TODO 列表界面公共部分实现类
 */

public abstract class MVPListImpl<D, RV> implements IMVPBaseList.ILogicCommon<D>, IMVPBaseList.IViewCommon<D>,
        RcvLoadMoreListener, IRefreshWrapper.onRefreshListener
{
    private MVPBaseViewImpl mBaseViewImpl = new MVPBaseViewImpl();
    private IRefreshWrapper<RV> mRefreshWrapper;
    private RecyclerView mRecyclerView;
    private MVPListOptions mOptions;
    private RcvMultiAdapter<D> mAdapter;
    private int mCurrentIndex;
    private Listener mListener;

    public MVPListImpl(Listener listener)
    {
        this.mListener = listener;
    }

    @Override
    public RecyclerView findRecyclerView(MVPListOptions options, View contentView)
    {
        return contentView.findViewById(R.id.id_common_recyclerview);
    }

    @Override
    public void init(MVPListOptions options, View contentView,
                     IRefreshWrapper refreshWrapper, RecyclerView recyclerView,
                     RcvMultiAdapter<D> adapter)
    {
        mOptions = options;
        mAdapter = adapter;
        mRefreshWrapper = refreshWrapper;
        mRecyclerView = recyclerView;

        if (mRefreshWrapper == null)
            throw new IllegalStateException("RefreshLayout can not be null! ");
        if (mRecyclerView == null)
            throw new IllegalStateException("RecyclerView can not be null!");
        if (mAdapter == null)
            throw new IllegalStateException("RecyclerView's Adapter can not be null!");

        mRefreshWrapper.enableRefresh(getListOptions().isEnableRefresh());
        mRefreshWrapper.setOnRefreshListener(this);
        if (mOptions.isEnableLoadMore() && mAdapter.isLoadMoreLayoutEmpty())
            mAdapter.setLoadMoreLayout(new RcvDefLoadMoreView.Builder().build(contentView.getContext()));
        mAdapter.disableLoadMore();//首先先禁止，刷新后再配置
        mRecyclerView.setLayoutManager(getListOptions().getLayoutManager());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void autoRefresh()
    {
        mRefreshWrapper.autoRefresh();
    }

    @Override
    public void onRefreshRequest()
    {
        mAdapter.disableLoadMore();//刷新的时候禁止加载更多
        if (mListener != null)
            mListener.doRefresh(System.currentTimeMillis(), 0, mOptions.getPageSize());
    }

    @Override
    public void onRefreshSuccess(int pageIndex, List<D> dataList)
    {
        this.mCurrentIndex = pageIndex;
        mRefreshWrapper.finishRefresh();//结束刷新
        mAdapter.refreshDatas(dataList);
        if (dataList == null || dataList.size() == 0)
            return;
        mAdapter.enableLoadMore(this);
        if (getListOptions().isEnableLoadMore() &&
                dataList.size() < getListOptions().getPageSize())
            mAdapter.notifyLoadMoreHasNoMoreData();
    }

    @Override
    public void onRefreshFail(String errorMsg)
    {
        mRefreshWrapper.finishRefresh();
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
    public MVPListOptions getListOptions()
    {
        return mOptions == null ? new MVPListOptions() : mOptions;
    }

    @Override
    public IRefreshWrapper<RV> getRefreshWrapper()
    {
        return mRefreshWrapper;
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

    @Override
    public void onDestroy()
    {
        if (getRefreshWrapper() != null)
            getRefreshWrapper().onDestroy();
    }

    public interface Listener
    {
        void doRefresh(long timeStamp, int pageIndex, int pageSize);

        void doLoadMore(long timeStamp, int pageIndex, int pageSize, int currentDataCount);
    }
}
