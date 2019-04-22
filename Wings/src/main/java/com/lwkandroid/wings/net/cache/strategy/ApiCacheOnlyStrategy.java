package com.lwkandroid.wings.net.cache.strategy;

import com.lwkandroid.wings.net.bean.ApiCacheOptions;
import com.lwkandroid.wings.net.bean.ApiResultCacheWrapper;

import io.reactivex.Observable;

/**
 * Created by LWK
 *  只读取缓存的策略
 */

public class ApiCacheOnlyStrategy extends ApiCacheBaseStrategy
{
    @Override
    public <T> Observable<ApiResultCacheWrapper<T>> execute(ApiCacheOptions options, Observable<T> source, Class<T> clazz)
    {
        return loadCache(options, clazz, true);
    }
}
