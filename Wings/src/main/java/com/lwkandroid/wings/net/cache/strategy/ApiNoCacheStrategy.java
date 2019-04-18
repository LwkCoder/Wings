package com.lwkandroid.wings.net.cache.strategy;

import com.lwkandroid.wings.net.bean.ApiCacheOptions;
import com.lwkandroid.wings.net.bean.ApiResultCacheWrapper;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by LWK
 * TODO 无缓存策略
 */

public class ApiNoCacheStrategy extends ApiCacheBaseStrategy
{
    @Override
    public <T> Observable<ApiResultCacheWrapper<T>> execute(ApiCacheOptions options, Observable<T> source, Class<T> clazz)
    {
        return source.map(new Function<T, ApiResultCacheWrapper<T>>()
        {
            @Override
            public ApiResultCacheWrapper<T> apply(T t) throws Exception
            {
                return new ApiResultCacheWrapper<T>(false, t);
            }
        });
    }
}
