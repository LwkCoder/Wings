package com.lwkandroid.lib.core.java.net.cache.strategy;

import com.lwkandroid.lib.core.java.net.bean.ApiCacheOptions;
import com.lwkandroid.lib.core.java.net.bean.ResultCacheWrapper;
import com.lwkandroid.lib.core.java.net.cache.RxCache;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * 缓存策略基类
 *
 * @author LWK
 */
public abstract class ApiCacheBaseStrategy implements IApiCacheStrategy
{
    /**
     * 加载本地缓存
     */
    protected <T> Observable<ResultCacheWrapper<T>> loadCache(ApiCacheOptions options, Class<T> clazz, boolean needEmpty)
    {
        Observable<ResultCacheWrapper<T>> observable = RxCache.loadCache(options.getCacheCore(), clazz, options.getCacheKey(), options.getCacheTime())
                .flatMap((Function<T, ObservableSource<ResultCacheWrapper<T>>>) t -> Observable.just(new ResultCacheWrapper<>(true, t)));

        if (needEmpty)
        {
            observable = observable.onErrorResumeNext(throwable -> {
                return Observable.empty();
            });
        }

        return observable;
    }

    /**
     * 请求远程数据
     */
    protected <T> Observable<ResultCacheWrapper<T>> loadRemote(final ApiCacheOptions options, Class<T> clazz, Observable<T> source, boolean needEmpty)
    {
        Observable<ResultCacheWrapper<T>> observable = source
                .flatMap((Function<T, ObservableSource<ResultCacheWrapper<T>>>) t ->
                        RxCache.saveCache(options.getCacheCore(), t, options.getCacheKey(), options.getCacheTime())
                                .map(aBoolean -> new ResultCacheWrapper<>(false, t))
                                .onErrorReturn(throwable -> new ResultCacheWrapper<T>(false, t)));

        if (needEmpty)
        {
            observable = observable.onErrorResumeNext(throwable -> {
                return Observable.empty();
            });
        }

        return observable;
    }
}
