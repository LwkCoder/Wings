package com.lwkandroid.wings.net.cache.strategy;

import com.lwkandroid.wings.net.bean.ApiCacheOptions;
import com.lwkandroid.wings.net.bean.ApiCacheResultBean;

import io.reactivex.Observable;

/**
 * Created by LWK
 * TODO 先读取缓存，失败或为空请求网络的策略
 */

public class CacheFirstStrategy  extends BaseStrategy
{
    @Override
    public <T> Observable<ApiCacheResultBean<T>> excute(ApiCacheOptions options, Observable<T> source, Class<T> clazz)
    {
        Observable<ApiCacheResultBean<T>> cache = loadCache(options, clazz, true);
        Observable<ApiCacheResultBean<T>> remote = loadRemote(options, clazz, source, false);
        return cache.switchIfEmpty(remote);
    }
}
