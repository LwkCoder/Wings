package com.lwkandroid.lib.core.net.retry;


import com.lwkandroid.lib.core.log.KLog;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * 网络请求失败自动重试方法
 *
 * @author LWK
 */
public class AutoRetryFunc implements Function<Observable<? extends Throwable>, Observable<?>>
{
    /**
     * 重试的Url，用来打日志
     */
    private String mUrl;
    /**
     * 重试次数
     */
    private int mRetryCount;
    /**
     * 每次重试间隔时间
     */
    private int mRetryDelay;
    /**
     * 自动重试判断条件
     */
    private IAutoRetry mAutoRetry;

    public AutoRetryFunc(String url, int retryCount, int retryDelay, IAutoRetry autoRetry)
    {
        this.mUrl = url;
        this.mRetryCount = retryCount;
        this.mRetryDelay = retryDelay;
        this.mAutoRetry = autoRetry;
    }

    @Override
    public Observable<?> apply(final Observable<? extends Throwable> observable) throws Exception
    {
        return observable.zipWith(Observable.range(1, mRetryCount + 1),
                (BiFunction<Throwable, Integer, ThrowableWrapper>) (throwable, integer) ->
                        new ThrowableWrapper(integer, throwable))
                .flatMap((Function<ThrowableWrapper, ObservableSource<?>>) wrapper -> {
                    if (mAutoRetry.judgeAutoRetry(wrapper.getThrowable())
                            && wrapper.getIndex() < mRetryCount + 1)
                    {
                        KLog.i("RxHttp RetryCount=" + wrapper.getIndex() + " Url=" + mUrl);
                        return Observable.timer(mRetryDelay, TimeUnit.MILLISECONDS);
                    } else
                    {
                        return Observable.error(wrapper.getThrowable());
                    }
                });
    }

    private class ThrowableWrapper
    {
        private int index;
        private Throwable throwable;

        ThrowableWrapper(int index, Throwable throwable)
        {
            this.index = index;
            this.throwable = throwable;
        }

        public int getIndex()
        {
            return index;
        }

        public Throwable getThrowable()
        {
            return throwable;
        }
    }
}