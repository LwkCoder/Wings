package com.lwkandroid.wings.net.cache.strategy;

import com.lwkandroid.wings.net.bean.ApiCacheOptions;
import com.lwkandroid.wings.net.bean.ApiCacheResultBean;
import com.lwkandroid.wings.net.cache.RxCache;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by LWK
 * TODO 缓存策略基类
 */

public abstract class BaseStrategy implements IStrategy
{
    /**
     * 加载本地缓存
     */
    protected <T> Observable<ApiCacheResultBean<T>> loadCache(ApiCacheOptions options, Class<T> clazz, boolean needEmpty)
    {
        Observable<ApiCacheResultBean<T>> observable = RxCache.loadCache(options.getCacheCore(), clazz, options.getCacheKey(), options.getCacheTime())
                .flatMap(new Function<T, ObservableSource<ApiCacheResultBean<T>>>()
                {
                    @Override
                    public ObservableSource<ApiCacheResultBean<T>> apply(T t) throws Exception
                    {
                        return Observable.just(new ApiCacheResultBean<>(true, t));
                    }
                });

        if (needEmpty)
            observable = observable.onErrorResumeNext(new Function<Throwable, ObservableSource<? extends ApiCacheResultBean<T>>>()
            {
                @Override
                public ObservableSource<? extends ApiCacheResultBean<T>> apply(@NonNull Throwable throwable) throws Exception
                {
                    return Observable.empty();
                }
            });

        return observable;
    }

    /**
     * 请求远程数据
     */
    protected <T> Observable<ApiCacheResultBean<T>> loadRemote(final ApiCacheOptions options, Class<T> clazz, Observable<T> source, boolean needEmpty)
    {
        Observable<ApiCacheResultBean<T>> observable = source.flatMap(new Function<T, ObservableSource<ApiCacheResultBean<T>>>()
        {
            @Override
            public ObservableSource<ApiCacheResultBean<T>> apply(final T t) throws Exception
            {
                return RxCache.saveCache(options.getCacheCore(), t, options.getCacheKey(), options.getCacheTime())
                        .map(new Function<Boolean, ApiCacheResultBean<T>>()
                        {
                            @Override
                            public ApiCacheResultBean<T> apply(Boolean aBoolean) throws Exception
                            {
                                return new ApiCacheResultBean<>(false, t);
                            }
                        }).onErrorReturn(new Function<Throwable, ApiCacheResultBean<T>>()
                        {
                            @Override
                            public ApiCacheResultBean<T> apply(@NonNull Throwable throwable) throws Exception
                            {
                                return new ApiCacheResultBean<T>(false, t);
                            }
                        });
            }
        });

        if (needEmpty)
            observable = observable.onErrorResumeNext(new Function<Throwable, ObservableSource<? extends ApiCacheResultBean<T>>>()
            {
                @Override
                public ObservableSource<? extends ApiCacheResultBean<T>> apply(@NonNull Throwable throwable) throws Exception
                {
                    return Observable.empty();
                }
            });

        return observable;
    }
}
