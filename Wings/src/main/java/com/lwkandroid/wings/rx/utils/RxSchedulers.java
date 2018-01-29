package com.lwkandroid.wings.rx.utils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava 工具类
 */

public final class RxSchedulers
{

    /**
     * 操作在IO，观察在AndroidMain
     */
    private static ObservableTransformer<Object, Object> IO_MAIN = new ObservableTransformer<Object, Object>()
    {
        @Override
        public ObservableSource<Object> apply(@NonNull Observable<Object> upstream)
        {
            return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
    };

    /**
     * 操作在Computation，观察在AndroidMain
     */
    private static ObservableTransformer<Object, Object> COMPUTATION_MAIN = new ObservableTransformer<Object, Object>()
    {
        @Override
        public ObservableSource<Object> apply(@NonNull Observable<Object> upstream)
        {
            return upstream.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread());
        }
    };

    /**
     * 操作在newThread，观察在AndroidMain
     */
    private static ObservableTransformer<Object, Object> NEWTHREAD_MAIN = new ObservableTransformer<Object, Object>()
    {
        @Override
        public ObservableSource<Object> apply(@NonNull Observable<Object> upstream)
        {
            return upstream.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        }
    };

    /**
     * 绑定某个Dispose到IO_MAIN调度中
     */
    public static <T> ObservableTransformer<T, T> applyIo2Main()
    {
        return (ObservableTransformer<T, T>) IO_MAIN;
    }

    /**
     * 绑定某个Dispose到COMPUTATION_MAIN调度中
     */
    public static <T> ObservableTransformer<T, T> applyComputation2Main()
    {
        return (ObservableTransformer<T, T>) COMPUTATION_MAIN;
    }

    /**
     * 绑定某个Dispose到NEWTHREAD_MAIN调度中
     */
    public static <T> ObservableTransformer<T, T> applyNewThread2Main()
    {
        return (ObservableTransformer<T, T>) NEWTHREAD_MAIN;
    }

    /**
     * 绑定某个Dispose到任意两个线程
     */
    public static <T> ObservableTransformer<T, T> apply(final Scheduler up, final Scheduler down)
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
}
