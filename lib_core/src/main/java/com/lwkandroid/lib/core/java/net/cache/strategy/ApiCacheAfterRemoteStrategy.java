package com.lwkandroid.lib.core.java.net.cache.strategy;


import com.lwkandroid.lib.core.java.net.bean.ApiCacheOptions;
import com.lwkandroid.lib.core.java.net.bean.ResultCacheWrapper;

import io.reactivex.Observable;

/**
 *  先返回本地缓存，然后获取网络数据，回调2次的策略
 *
 * @author LWK
 */
public class ApiCacheAfterRemoteStrategy extends ApiCacheBaseStrategy
{
    @Override
    public <T> Observable<ResultCacheWrapper<T>> execute(ApiCacheOptions options, Observable<T> source, Class<T> clazz)
    {
        Observable<ResultCacheWrapper<T>> cache = loadCache(options, clazz, true);
        Observable<ResultCacheWrapper<T>> remote = loadRemote(options, clazz, source, false);
        return Observable.concat(cache, remote)
                .filter(cacheResultBean -> cacheResultBean != null && cacheResultBean.getData() != null);
    }
}
