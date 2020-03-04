package com.lwkandroid.demo2.test;

import com.lwkandroid.lib.common.mvp.MvpBasePresenterImpl;
import com.lwkandroid.lib.core.net.bean.ApiException;
import com.lwkandroid.lib.core.net.observer.ApiBaseObserver;

import java.util.List;

/**
 * Description:Presenter层
 *
 * @author
 * @date
 */
class TestPresenter extends MvpBasePresenterImpl<TestContract.IView, TestContract.IModel>
        implements TestContract.IPresenter<TestContract.IView, TestContract.IModel>
{
    /**
     * 默认起始页码
     */
    private final int PAGE_INDEX_DEFAULT = 1;
    /**
     * 每页请求数量
     */
    private final int PAGE_SIZE = 20;
    /**
     * 当前页码
     */
    private int mCurrentIndex = PAGE_INDEX_DEFAULT;

    public TestPresenter(TestContract.IView viewImpl, TestContract.IModel modelImpl)
    {
        super(viewImpl, modelImpl);
    }

    @Override
    public void requestRefresh(String... args)
    {
        //下拉刷新
        getModelImpl()
                .requestData(PAGE_INDEX_DEFAULT, PAGE_SIZE, System.currentTimeMillis())
                .compose(applyIo2MainUntilOnDestroy())
                .subscribe(new ApiBaseObserver<List<String>>()
                {
                    @Override
                    public void subOnNext(List<String> list)
                    {
                        //刷新成功后重置页码
                        mCurrentIndex = PAGE_INDEX_DEFAULT;
                        getViewImpl().onRefreshSuccess(list);
                    }

                    @Override
                    public void subOnError(ApiException e)
                    {
                        getViewImpl().onRefreshFail(e.getDisplayMessage(), e);
                    }
                });

    }

    @Override
    public void requestLoadMore(String... args)
    {
        //自动加载
        final int nextPageIndex = mCurrentIndex + 1;
        getModelImpl()
                .requestData(nextPageIndex, PAGE_SIZE, System.currentTimeMillis())
                .compose(applyIo2MainUntilOnDestroy())
                .subscribe(new ApiBaseObserver<List<String>>()
                {
                    @Override
                    public void subOnNext(List<String> list)
                    {
                        //同步页码
                        mCurrentIndex = nextPageIndex;
                        getViewImpl().onLoadMoreSuccess(list);
                        if (list != null && list.size() < PAGE_SIZE)
                        {
                            getViewImpl().onLoadMoreNoMoreData();
                        }
                    }

                    @Override
                    public void subOnError(ApiException e)
                    {
                        getViewImpl().onLoadMoreFail(e.getDisplayMessage(), e);
                    }
                });
    }
}
