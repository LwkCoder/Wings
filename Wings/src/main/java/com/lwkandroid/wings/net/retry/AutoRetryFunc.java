package com.lwkandroid.wings.net.retry;

import com.socks.library.KLog;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * Created by LWK
 * TODO 网络请求失败自动重试方法
 */

public class AutoRetryFunc implements Function<Observable<? extends Throwable>, Observable<?>>
{
    //重试的Url，用来打日志
    private String mUrl;
    //重试次数
    private int mRetryCount = 0;
    //每次重试间隔时间
    private int mRetryDelay = 500;
    //自动重试判断条件
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
        return observable.zipWith(Observable.range(1, mRetryCount + 1), new BiFunction<Throwable, Integer, ThrowableWrapper>()
        {
            @Override
            public ThrowableWrapper apply(Throwable throwable, Integer integer) throws Exception
            {
                return new ThrowableWrapper(integer, throwable);
            }
        }).flatMap(new Function<ThrowableWrapper, ObservableSource<?>>()
        {
            @Override
            public ObservableSource<?> apply(ThrowableWrapper wrapper) throws Exception
            {
                if (mAutoRetry.judgeAutoRetry(wrapper.getThrowable())
                        && wrapper.getIndex() < mRetryCount + 1)
                {
                    KLog.i("RetryCount=" + wrapper.getIndex() + " Url=" + mUrl);
                    return Observable.timer(mRetryDelay, TimeUnit.MILLISECONDS);
                } else
                {
                    return Observable.error(wrapper.getThrowable());
                }
            }
        });
    }

    private class ThrowableWrapper
    {
        private int index;
        private Throwable throwable;

        public ThrowableWrapper(int index, Throwable throwable)
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
