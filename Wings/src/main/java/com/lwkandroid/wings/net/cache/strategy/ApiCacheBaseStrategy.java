package com.lwkandroid.wings.net.cache.strategy;

import com.lwkandroid.wings.net.bean.ApiCacheOptions;
import com.lwkandroid.wings.net.bean.ApiResultCacheWrapper;
import com.lwkandroid.wings.net.cache.RxCache;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by LWK
 * TODO 缓存策略基类
 */

public abstract class ApiCacheBaseStrategy implements IApiCacheStrategy
{
    /**
     * 加载本地缓存
     */
    protected <T> Observable<ApiResultCacheWrapper<T>> loadCache(ApiCacheOptions options, Class<T> clazz, boolean needEmpty)
    {
        Observable<ApiResultCacheWrapper<T>> observable = RxCache.loadCache(options.getCacheCore(), clazz, options.getCacheKey(), options.getCacheTime())
                .flatMap(new Function<T, ObservableSource<ApiResultCacheWrapper<T>>>()
                {
                    @Override
                    public ObservableSource<ApiResultCacheWrapper<T>> apply(T t) throws Exception
                    {
                        return Observable.just(new ApiResultCacheWrapper<>(true, t));
                    }
                });

        if (needEmpty)
        {
            observable = observable.onErrorResumeNext(new Function<Throwable, ObservableSource<? extends ApiResultCacheWrapper<T>>>()
            {
                @Override
                public ObservableSource<? extends ApiResultCacheWrapper<T>> apply(@NonNull Throwable throwable) throws Exception
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
    protected <T> Observable<ApiResultCacheWrapper<T>> loadRemote(final ApiCacheOptions options, Class<T> clazz, Observable<T> source, boolean needEmpty)
    {
        Observable<ApiResultCacheWrapper<T>> observable = source.flatMap(new Function<T, ObservableSource<ApiResultCacheWrapper<T>>>()
        {
            @Override
            public ObservableSource<ApiResultCacheWrapper<T>> apply(final T t) throws Exception
            {
                return RxCache.saveCache(options.getCacheCore(), t, options.getCacheKey(), options.getCacheTime())
                        .map(new Function<Boolean, ApiResultCacheWrapper<T>>()
                        {
                            @Override
                            public ApiResultCacheWrapper<T> apply(Boolean aBoolean) throws Exception
                            {
                                return new ApiResultCacheWrapper<>(false, t);
                            }
                        }).onErrorReturn(new Function<Throwable, ApiResultCacheWrapper<T>>()
                        {
                            @Override
                            public ApiResultCacheWrapper<T> apply(@NonNull Throwable throwable) throws Exception
                            {
                                return new ApiResultCacheWrapper<T>(false, t);
                            }
                        });
            }
        });

        if (needEmpty)
        {
            observable = observable.onErrorResumeNext(new Function<Throwable, ObservableSource<? extends ApiResultCacheWrapper<T>>>()
            {
                @Override
                public ObservableSource<? extends ApiResultCacheWrapper<T>> apply(@NonNull Throwable throwable) throws Exception
                {
                    return Observable.empty();
                }
            });
        }

        return observable;
    }
}
