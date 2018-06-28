package com.lwkandroid.wings.net.cache.strategy;

import com.lwkandroid.wings.net.bean.ApiCacheOptions;
import com.lwkandroid.wings.net.bean.ApiCacheResultBean;

import io.reactivex.Observable;

/**
 * Created by LWK
 * TODO 只读取缓存的策略
 */

public class CacheOnlyStrategy  extends BaseStrategy
{
    @Override
    public <T> Observable<ApiCacheResultBean<T>> excute(ApiCacheOptions options, Observable<T> source, Class<T> clazz)
    {
        return loadCache(options, clazz, true);
    }
}
