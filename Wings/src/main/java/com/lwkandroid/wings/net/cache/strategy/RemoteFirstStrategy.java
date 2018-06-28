package com.lwkandroid.wings.net.cache.strategy;

import com.lwkandroid.wings.net.bean.ApiCacheOptions;
import com.lwkandroid.wings.net.bean.ApiCacheResultBean;

import io.reactivex.Observable;

/**
 * Created by LWK
 * TODO 先请求网络，失败后读取缓存的策略
 */

public class RemoteFirstStrategy extends BaseStrategy
{
    @Override
    public <T> Observable<ApiCacheResultBean<T>> excute(ApiCacheOptions options, Observable<T> source, Class<T> clazz)
    {
        Observable<ApiCacheResultBean<T>> cache = loadCache(options, clazz, false);
        Observable<ApiCacheResultBean<T>> remote = loadRemote(options, clazz, source, true);
        return remote.switchIfEmpty(cache);
    }
}
