package com.lwkandroid.wings.net.cache.strategy;

import com.lwkandroid.wings.net.bean.ApiCacheOptions;
import com.lwkandroid.wings.net.bean.ResultCacheWrapper;
import com.lwkandroid.wings.net.cache.RxCache;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by LWK
 *  缓存策略基类
 */

public abstract class ApiCacheBaseStrategy implements IApiCacheStrategy
{
    /**
     * 加载本地缓存
     */
    protected <T> Observable<ResultCacheWrapper<T>> loadCache(ApiCacheOptions options, Class<T> clazz, boolean needEmpty)
    {
        Observable<ResultCacheWrapper<T>> observable = RxCache.loadCache(options.getCacheCore(), clazz, options.getCacheKey(), options.getCacheTime())
                .flatMap(new Function<T, ObservableSource<ResultCacheWrapper<T>>>()
                {
                    @Override
                    public ObservableSource<ResultCacheWrapper<T>> apply(T t) throws Exception
                    {
                        return Observable.just(new ResultCacheWrapper<>(true, t));
                    }
                });

        if (needEmpty)
        {
            observable = observable.onErrorResumeNext(new Function<Throwable, ObservableSource<? extends ResultCacheWrapper<T>>>()
            {
                @Override
                public ObservableSource<? extends ResultCacheWrapper<T>> apply(@NonNull Throwable throwable) throws Exception
                {
                    return Observable.empty();
                }
            });
        }

        return observable;
    }

    /**
     * 请求远程数据
     */
    protected <T> Observable<ResultCacheWrapper<T>> loadRemote(final ApiCacheOptions options, Class<T> clazz, Observable<T> source, boolean needEmpty)
    {
        Observable<ResultCacheWrapper<T>> observable = source.flatMap(new Function<T, ObservableSource<ResultCacheWrapper<T>>>()
        {
            @Override
            public ObservableSource<ResultCacheWrapper<T>> apply(final T t) throws Exception
            {
                return RxCache.saveCache(options.getCacheCore(), t, options.getCacheKey(), options.getCacheTime())
                        .map(new Function<Boolean, ResultCacheWrapper<T>>()
                        {
                            @Override
                            public ResultCacheWrapper<T> apply(Boolean aBoolean) throws Exception
                            {
                                return new ResultCacheWrapper<>(false, t);
                            }
                        }).onErrorReturn(new Function<Throwable, ResultCacheWrapper<T>>()
                        {
                            @Override
                            public ResultCacheWrapper<T> apply(@NonNull Throwable throwable) throws Exception
                            {
                                return new ResultCacheWrapper<T>(false, t);
                            }
                        });
            }
        });

        if (needEmpty)
        {
            observable = observable.onErrorResumeNext(new Function<Throwable, ObservableSource<? extends ResultCacheWrapper<T>>>()
            {
                @Override
                public ObservableSource<? extends ResultCacheWrapper<T>> apply(@NonNull Throwable throwable) throws Exception
                {
                    return Observable.empty();
                }
            });
        }

        return observable;
    }
}
