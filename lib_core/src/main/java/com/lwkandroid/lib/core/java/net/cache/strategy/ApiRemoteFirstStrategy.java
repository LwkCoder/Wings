package com.lwkandroid.lib.core.java.net.cache.strategy;


import com.lwkandroid.lib.core.java.net.bean.ApiCacheOptions;
import com.lwkandroid.lib.core.java.net.bean.ResultCacheWrapper;

import io.reactivex.Observable;

/**
 * 先请求网络，失败后读取缓存的策略
 *
 * @author LWK
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
