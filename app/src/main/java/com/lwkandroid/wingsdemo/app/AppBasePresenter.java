package com.lwkandroid.wingsdemo.app;

import com.lwkandroid.wings.rx.constant.RxLifecycle;
import com.lwkandroid.wings.rx.mvp.IRxBaseView;
import com.lwkandroid.wings.rx.mvp.RxBasePresenter;
import com.lwkandroid.wings.rx.utils.RxSchedulers;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

/**
 * Created by LWK
 * TODO 项目定制Presenter层基类,定制公共方法
 */

public abstract class AppBasePresenter<V extends IRxBaseView, M> extends RxBasePresenter<V, M>
{
    /**
     * 线程绑定IO到Main，并监听生命周期OnDestroy
     */
    protected <T> ObservableTransformer<T, T> applyIo2MainWithLifeCycle()
    {
        return new ObservableTransformer<T, T>()
        {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream)
            {
                return upstream.compose(RxSchedulers.<T>applyIo2Main())
                        .takeUntil(withRxLifecycle());
            }
        };
    }

    /**
     * 线程绑定IO到Main，并监听指定生命周期
     */
    protected <T> ObservableTransformer<T, T> applyIo2MainWithLifeCycle(@RxLifecycle.Event final int target)
    {
        return new ObservableTransformer<T, T>()
        {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream)
            {
                return upstream.compose(RxSchedulers.<T>applyIo2Main())
                        .takeUntil(withRxLifecycle(target));
            }
        };
    }

    /**
     * 线程绑定Computation到Main，并监听生命周期OnDestroy
     */
    protected <T> ObservableTransformer<T, T> applyComputation2MainWithLifeCycle()
    {
        return new ObservableTransformer<T, T>()
        {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream)
            {
                return upstream.compose(RxSchedulers.<T>applyComputation2Main())
                        .takeUntil(withRxLifecycle());
            }
        };
    }

    /**
     * 线程绑定Computation到Main，并监听指定生命周期
     */
    protected <T> ObservableTransformer<T, T> applyComputation2MainWithLifeCycle(@RxLifecycle.Event final int target)
    {
        return new ObservableTransformer<T, T>()
        {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream)
            {
                return upstream.compose(RxSchedulers.<T>applyComputation2Main())
                        .takeUntil(withRxLifecycle(target));
            }
        };
    }

    /**
     * 线程绑定NewThread到Main，并监听生命周期OnDestroy
     */
    protected <T> ObservableTransformer<T, T> applyNewThread2MainWithLifeCycle()
    {
        return new ObservableTransformer<T, T>()
        {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream)
            {
                return upstream.compose(RxSchedulers.<T>applyNewThread2Main())
                        .takeUntil(withRxLifecycle());
            }
        };
    }

    /**
     * 线程绑定NewThread到Main，并监听指定生命周期
     */
    protected <T> ObservableTransformer<T, T> applyNewThread2MainWithLifeCycle(@RxLifecycle.Event final int target)
    {
        return new ObservableTransformer<T, T>()
        {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream)
            {
                return upstream.compose(RxSchedulers.<T>applyNewThread2Main())
                        .takeUntil(withRxLifecycle(target));
            }
        };
    }
}
