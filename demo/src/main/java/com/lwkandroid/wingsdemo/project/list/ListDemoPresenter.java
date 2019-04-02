package com.lwkandroid.wingsdemo.project.list;

import com.lwkandroid.wings.net.bean.ApiException;
import com.lwkandroid.wings.net.observer.ApiBaseObserver;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by LWK
 * TODO
 */

public class ListDemoPresenter extends ListDemoContract.Presenter
{
    @Override
    void doRefresh(long timeStamp, final int pageIndex, int pageSize)
    {
        getModelImpl().getRefreshData(timeStamp, pageIndex, pageSize)
                .delay(2, TimeUnit.SECONDS)
                .compose(this.<List<String>>applyIo2MainWithLifeCycle())
                .subscribe(new ApiBaseObserver<List<String>>()
                {
                    @Override
                    public void subOnNext(List<String> strings)
                    {
                        getViewImpl().onRefreshSuccess(pageIndex, strings);
                    }

                    @Override
                    public void subOnError(ApiException e)
                    {
                        getViewImpl().onRefreshFail(e.getDisplayMessage());
                    }
                });
    }

    @Override
    void doLoadMore(long timeStamp, final int pageIndex, int pageSize, int currentDataCount)
    {
        getModelImpl().getLoadMoreData(timeStamp, pageIndex, pageSize, currentDataCount)
                .delay(2, TimeUnit.SECONDS)
                .compose(this.<List<String>>applyIo2MainWithLifeCycle())
                .subscribe(new ApiBaseObserver<List<String>>()
                {
                    @Override
                    public void subOnNext(List<String> strings)
                    {
                        getViewImpl().onLoadMoreSuccess(pageIndex, strings);
                    }

                    @Override
                    public void subOnError(ApiException e)
                    {
                        getViewImpl().onLoadMoreFail(e.getDisplayMessage());
                    }
                });
    }

    @Override
    public ListDemoContract.Model createModel()
    {
        return new ListDemoModel();
    }
}
