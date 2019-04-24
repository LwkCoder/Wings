package com.lwkandroid.wings.rx.schedulers;

import io.reactivex.Scheduler;
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
    public static <T> RxSchedulersTransformer<T> applyIo2Main()
    {
        return new RxSchedulersTransformer<T>(Schedulers.io(), AndroidSchedulers.mainThread());
    }

    /**
     * 绑定某个Dispose到COMPUTATION_MAIN调度中
     */
    public static <T> RxSchedulersTransformer<T> applyComputation2Main()
    {
        return new RxSchedulersTransformer<T>(Schedulers.computation(), AndroidSchedulers.mainThread());
    }

    /**
     * 绑定某个Dispose到NEWTHREAD_MAIN调度中
     */
    public static <T> RxSchedulersTransformer<T> applyNewThread2Main()
    {
        return new RxSchedulersTransformer<T>(Schedulers.newThread(), AndroidSchedulers.mainThread());
    }

    /**
     * 绑定某个Dispose到任意两个线程
     */
    public static <T> RxSchedulersTransformer<T> applySchedulers(Scheduler up, Scheduler down)
    {
        return new RxSchedulersTransformer<T>(up, down);
    }
}
