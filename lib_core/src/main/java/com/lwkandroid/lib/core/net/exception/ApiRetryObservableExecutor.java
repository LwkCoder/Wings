package com.lwkandroid.lib.core.net.exception;

import com.lwkandroid.lib.core.log.KLog;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.Function;

/**
 * Description:
 *
 * @author 20180004
 * @date 2020/4/2
 */
public class ApiRetryObservableExecutor implements Function<Observable<? extends Throwable>, Observable<?>>
{
    private RetryConfig mRetryConfigProvider;
    private int mCurrentRetryCount = 0;

    public ApiRetryObservableExecutor(RetryConfig retryConfigProvider)
    {
        this.mRetryConfigProvider = retryConfigProvider;
    }

    @Override
    public Observable<?> apply(Observable<? extends Throwable> observable) throws Exception
    {
        return observable.flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {
            Single<Boolean> single = mRetryConfigProvider.getRetryCondition().onRetryCondition(throwable);
            if (++mCurrentRetryCount <= mRetryConfigProvider.getMaxRetryCount())
            {
                return single.flatMapObservable((Function<Boolean, ObservableSource<?>>) needRetry -> {
                    if (needRetry)
                    {
                        KLog.e("Retry RxHttp request after "
                                + mRetryConfigProvider.getRetryDelay() + " milliseconds,"
                                + "current retry count=" + mCurrentRetryCount
                                + " due to exception:\n"
                                + throwable.toString());
                        return Observable.timer(mRetryConfigProvider.getRetryDelay(), TimeUnit.MILLISECONDS);
                    } else
                    {
                        return Observable.error(throwable);
                    }
                });
            } else
            {
                return Observable.error(throwable);
            }
        });
    }

}
