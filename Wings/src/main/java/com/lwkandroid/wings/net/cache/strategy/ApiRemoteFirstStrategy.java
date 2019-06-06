package com.lwkandroid.wings.net.cache.strategy;

import com.lwkandroid.wings.net.bean.ApiCacheOptions;
import com.lwkandroid.wings.net.bean.ResultCacheWrapper;

import io.reactivex.Observable;

/**
 * Created by LWK
 *  先请求网络，失败后读取缓存的策略
 */

public class ApiRemoteFirstStrategy extends ApiCacheBaseStrategy
{
    @Override
    public <T> Observable<ResultCacheWrapper<T>> execute(ApiCacheOptions options, Observable<T> source, Class<T> clazz)
    {
        Observable<ResultCacheWrapper<T>> cache = loadCache(options, clazz, false);
        Observable<ResultCacheWrapper<T>> remote = loadRemote(options, clazz, source, true);
        return remote.switchIfEmpty(cache);
    }
}
