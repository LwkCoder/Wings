package com.lwkandroid.demo2.test;

import com.lwkandroid.lib.common.mvp.IMvpBaseModel;
import com.lwkandroid.lib.common.mvp.IMvpBasePresenter;
import com.lwkandroid.lib.common.mvp.IMvpBaseView;
import com.lwkandroid.lib.common.mvp.list.ILoadMoreWrapper;
import com.lwkandroid.lib.common.mvp.list.IRefreshWrapper;

import java.util.List;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;

/**
 * Description:契约层
 *
 * @author
 * @date
 */
interface TestContract
{
    interface IView<P extends LifecycleObserver> extends IMvpBaseView<P>
    {
        boolean isRefreshEnable();

        boolean isLoadMoreEnable();

        IRefreshWrapper getRefreshWrapper();

        ILoadMoreWrapper getLoadMoreWrapper();

        void autoRefresh();

        void onRefreshSuccess(List<String> dataList);

        void onRefreshFail(String message, Throwable throwable);

        void onLoadMoreSuccess(List<String> dataList);

        void onLoadMoreFail(String message, Throwable throwable);

        void onLoadMoreNoMoreData();
    }

    interface IModel extends IMvpBaseModel
    {
        Observable<List<String>> requestData(int pageIndex, int pageSize, long timeStamp, String... args);
    }

    interface IPresenter<V extends LifecycleOwner, M> extends IMvpBasePresenter<V, M>
    {
        void requestRefresh(String... args);

        void requestLoadMore(String... args);
    }
}
