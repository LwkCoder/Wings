package com.lwkandroid.lib.core.java.net.cache.strategy;


import com.lwkandroid.lib.core.java.net.bean.ApiCacheOptions;
import com.lwkandroid.lib.core.java.net.bean.ResultCacheWrapper;

import io.reactivex.Observable;

/**
 * 先读取缓存，失败或为空请求网络的策略
 *
 * @author LWK
 */
public class ApiCacheFirstStrategy extends ApiCacheBaseStrategy
{
    @Override
    public <T> Observable<ResultCacheWrapper<T>> execute(ApiCacheOptions options, Observable<T> source, Class<T> clazz)
    {
        Observable<ResultCacheWrapper<T>> cache = loadCache(options, clazz, true);
        Observable<ResultCacheWrapper<T>> remote = loadRemote(options, clazz, source, false);
        return cache.switchIfEmpty(remote);
    }
}
