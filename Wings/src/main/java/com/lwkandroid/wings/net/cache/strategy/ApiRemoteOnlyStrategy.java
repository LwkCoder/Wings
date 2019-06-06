package com.lwkandroid.wings.net.cache.strategy;

import com.lwkandroid.wings.net.bean.ApiCacheOptions;
import com.lwkandroid.wings.net.bean.ResultCacheWrapper;

import io.reactivex.Observable;

/**
 * Created by LWK
 *  只请求网络，但数据仍然缓存的策略
 */

public class ApiRemoteOnlyStrategy extends ApiCacheBaseStrategy
{
    @Override
    public <T> Observable<ResultCacheWrapper<T>> execute(ApiCacheOptions options, Observable<T> source, Class<T> clazz)
    {
        return loadRemote(options, clazz, source, false);
    }
}
