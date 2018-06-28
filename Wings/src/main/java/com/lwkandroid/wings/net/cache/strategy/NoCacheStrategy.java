package com.lwkandroid.wings.net.cache.strategy;

import com.lwkandroid.wings.net.bean.ApiCacheOptions;
import com.lwkandroid.wings.net.bean.ApiCacheResultBean;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by LWK
 * TODO 无缓存策略
 */

public class NoCacheStrategy  extends BaseStrategy
{
    @Override
    public <T> Observable<ApiCacheResultBean<T>> excute(ApiCacheOptions options, Observable<T> source, Class<T> clazz)
    {
        return source.map(new Function<T, ApiCacheResultBean<T>>()
        {
            @Override
            public ApiCacheResultBean<T> apply(T t) throws Exception
            {
                return new ApiCacheResultBean<T>(false, t);
            }
        });
    }
}
