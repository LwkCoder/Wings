package com.lwkandroid.wings.net.cache.strategy;

import com.lwkandroid.wings.net.bean.ApiCacheOptions;
import com.lwkandroid.wings.net.bean.ApiResultCacheWrapper;

import io.reactivex.Observable;

/**
 * Created by LWK
 * TODO 先读取缓存，失败或为空请求网络的策略
 */

public class ApiCacheFirstStrategy extends ApiCacheBaseStrategy
{
    @Override
    public <T> Observable<ApiResultCacheWrapper<T>> execute(ApiCacheOptions options, Observable<T> source, Class<T> clazz)
    {
        Observable<ApiResultCacheWrapper<T>> cache = loadCache(options, clazz, true);
        Observable<ApiResultCacheWrapper<T>> remote = loadRemote(options, clazz, source, false);
        return cache.switchIfEmpty(remote);
    }
}
