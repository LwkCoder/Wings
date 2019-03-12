package com.lwkandroid.wings.net.cache;

import com.lwkandroid.wings.net.bean.ApiCacheOptions;
import com.lwkandroid.wings.net.bean.ApiDiskCacheBean;
import com.lwkandroid.wings.net.bean.ApiResultCacheWrapper;
import com.lwkandroid.wings.net.bean.ApiException;
import com.lwkandroid.wings.net.cache.core.CacheCore;
import com.lwkandroid.wings.net.cache.strategy.CacheAfterRemoteDiffStrategy;
import com.lwkandroid.wings.net.cache.strategy.CacheAfterRemoteStrategy;
import com.lwkandroid.wings.net.cache.strategy.CacheFirstStrategy;
import com.lwkandroid.wings.net.cache.strategy.CacheOnlyStrategy;
import com.lwkandroid.wings.net.cache.strategy.IStrategy;
import com.lwkandroid.wings.net.cache.strategy.NoCacheStrategy;
import com.lwkandroid.wings.net.cache.strategy.RemoteFirstStrategy;
import com.lwkandroid.wings.net.cache.strategy.RemoteOnlyStrategy;
import com.lwkandroid.wings.net.constants.ApiCacheMode;
import com.lwkandroid.wings.net.constants.ApiExceptionCode;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;

/**
 * Created by LWK
 * TODO 缓存功能入口
 */

public class RxCache
{
    /**
     * 根据参数添加缓存关联
     */
    public static <T> ObservableTransformer<T, ApiResultCacheWrapper<T>> transform(final ApiCacheOptions options, final Class<T> clazz)
    {
        final IStrategy strategy = getStrategy(options.getCacheMode());
        return new ObservableTransformer<T, ApiResultCacheWrapper<T>>()
        {
            @Override
            public ObservableSource<ApiResultCacheWrapper<T>> apply(Observable<T> upstream)
            {
                return strategy.excute(options, upstream, clazz);
            }
        };
    }

    /**
     * 读取缓存
     */
    public static <T> Observable<T> loadCache(final CacheCore cacheCore, final Class<T> clazz, final String cacheKey, final long cacheTime)
    {
        return Observable.create(new CacheSubscribe<ApiDiskCacheBean<T>>()
        {
            @Override
            ApiDiskCacheBean<T> execute() throws Throwable
            {
                return cacheCore.load(clazz, cacheKey, cacheTime);
            }
        }).map(new Function<ApiDiskCacheBean<T>, T>()
        {
            @Override
            public T apply(ApiDiskCacheBean<T> bean) throws Exception
            {
                return bean.getData();
            }
        });
    }

    /**
     * 保存缓存
     */
    public static <T> Observable<Boolean> saveCache(final CacheCore cacheCore, final T value, final String cacheKey, final long cacheTime)
    {
        return Observable.create(new CacheSubscribe<Boolean>()
        {
            @Override
            Boolean execute() throws Throwable
            {
                long time;
                if (cacheTime < -1)
                {
                    time = -1;
                } else
                {
                    time = cacheTime;
                }
                ApiDiskCacheBean<T> entity = new ApiDiskCacheBean<>(value, time);
                entity.setUpdateDate(System.currentTimeMillis());
                return cacheCore.save(cacheKey, entity);
            }
        });
    }

    private static abstract class CacheSubscribe<T> implements ObservableOnSubscribe<T>
    {
        @Override
        public void subscribe(@NonNull ObservableEmitter<T> subscriber) throws Exception
        {
            try
            {
                T data = execute();
                if (!subscriber.isDisposed())
                {
                    if (data instanceof ApiDiskCacheBean)
                    {
                        if (((ApiDiskCacheBean) data).getData() != null)
                        {
                            subscriber.onNext(data);
                        } else
                        {
                            subscriber.onError(new ApiException(ApiExceptionCode.CACHE_EMPTY, "cache is empty"));
                        }
                    } else
                    {
                        if (data != null)
                        {
                            subscriber.onNext(data);
                        } else
                        {
                            subscriber.onError(new ApiException(ApiExceptionCode.CACHE_EMPTY, "cache is empty"));
                        }
                    }
                }
            } catch (Throwable e)
            {
                if (!subscriber.isDisposed())
                {
                    subscriber.onError(e);
                }
                Exceptions.throwIfFatal(e);
                return;
            }

            if (!subscriber.isDisposed())
            {
                subscriber.onComplete();
            }
        }

        abstract T execute() throws Throwable;
    }

    private static IStrategy getStrategy(@ApiCacheMode.Mode int mode)
    {
        if (mode == ApiCacheMode.NO_CACHE)
        {
            return new NoCacheStrategy();
        } else if (mode == ApiCacheMode.REMOTE_FIRST_OR_CACHE)
        {
            return new RemoteFirstStrategy();
        } else if (mode == ApiCacheMode.CACHE_FIRST_OR_REMOTE)
        {
            return new CacheFirstStrategy();
        } else if (mode == ApiCacheMode.REMOTE_ONLY)
        {
            return new RemoteOnlyStrategy();
        } else if (mode == ApiCacheMode.CACHE_ONLY)
        {
            return new CacheOnlyStrategy();
        } else if (mode == ApiCacheMode.CACHE_FIRST_AFTER_REMOTE)
        {
            return new CacheAfterRemoteStrategy();
        } else if (mode == ApiCacheMode.CACHE_FIRST_AFTER_REMOTE_IF_DIFF)
        {
            return new CacheAfterRemoteDiffStrategy();
        }
        return null;
    }
}
