package com.lwkandroid.wings.net.cache.strategy;

import com.lwkandroid.wings.net.bean.ApiCacheOptions;
import com.lwkandroid.wings.net.bean.ResultCacheWrapper;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 *  无缓存策略
 *
 * @author LWK
 */
public class ApiNoCacheStrategy extends ApiCacheBaseStrategy
{
    @Override
    public <T> Observable<ResultCacheWrapper<T>> execute(ApiCacheOptions options, Observable<T> source, Class<T> clazz)
    {
        return source.map(new Function<T, ResultCacheWrapper<T>>()
        {
            @Override
            public ResultCacheWrapper<T> apply(T t) throws Exception
            {
                return new ResultCacheWrapper<T>(false, t);
            }
        });
    }
}
