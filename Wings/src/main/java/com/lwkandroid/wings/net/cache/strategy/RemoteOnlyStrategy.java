package com.lwkandroid.wings.net.cache.strategy;

import com.lwkandroid.wings.net.bean.ApiCacheOptions;
import com.lwkandroid.wings.net.bean.ApiCacheResultBean;

import io.reactivex.Observable;

/**
 * Created by LWK
 * TODO 只请求网络，但数据仍然缓存的策略
 */

public class RemoteOnlyStrategy extends BaseStrategy
{
    @Override
    public <T> Observable<ApiCacheResultBean<T>> excute(ApiCacheOptions options, Observable<T> source, Class<T> clazz)
    {
        return loadRemote(options, clazz, source, false);
    }
}
