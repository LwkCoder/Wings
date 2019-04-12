package com.lwkandroid.wings.rx.utils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava 工具类
 */

public final class RxSchedulers
{
    /**
     * 绑定某个Dispose到IO_MAIN调度中
     */
    public static <T> ObservableTransformer<T, T> applyIo2MainAsObservable()
    {
        return applyAsObservable(Schedulers.io(), AndroidSchedulers.mainThread());
    }

    /**
     * 绑定某个Dispose到IO_MAIN调度中
     */
    public static <T> SingleTransformer<T, T> applyIo2MainAsSingle()
    {
        return applyAsSingle(Schedulers.io(), AndroidSchedulers.mainThread());
    }

    /**
     * 绑定某个Dispose到COMPUTATION_MAIN调度中
     */
    public static <T> ObservableTransformer<T, T> applyComputation2MainAsObservable()
    {
        return applyAsObservable(Schedulers.computation(), AndroidSchedulers.mainThread());
    }

    /**
     * 绑定某个Dispose到COMPUTATION_MAIN调度中
     */
    public static <T> SingleTransformer<T, T> applyComputation2MainAsSingle()
    {
        return applyAsSingle(Schedulers.computation(), AndroidSchedulers.mainThread());
    }

    /**
     * 绑定某个Dispose到NEWTHREAD_MAIN调度中
     */
    public static <T> ObservableTransformer<T, T> applyNewThread2MainAsObservable()
    {
        return applyAsObservable(Schedulers.newThread(), AndroidSchedulers.mainThread());
    }

    /**
     * 绑定某个Dispose到NEWTHREAD_MAIN调度中
     */
    public static <T> SingleTransformer<T, T> applyNewThread2MainAsSingle()
    {
        return applyAsSingle(Schedulers.newThread(), AndroidSchedulers.mainThread());
    }

    /**
     * 绑定某个Dispose到任意两个线程
     */
    public static <T> ObservableTransformer<T, T> applyAsObservable(final Scheduler up, final Scheduler down)
    {
        return new ObservableTransformer<T, T>()
        {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream)
            {
                return upstream.subscribeOn(up).observeOn(down);
            }
        };
    }

    /**
     * 绑定某个Dispose到任意两个线程
     */
    public static <T> SingleTransformer<T, T> applyAsSingle(final Scheduler up, final Scheduler down)
    {
        return new SingleTransformer<T, T>()
        {
            @Override
            public SingleSource<T> apply(Single<T> upstream)
            {
                return upstream.subscribeOn(up).observeOn(down);
            }
        };
    }
}
