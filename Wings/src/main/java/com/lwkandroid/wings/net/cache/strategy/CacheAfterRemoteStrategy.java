package com.lwkandroid.wings.net.cache.strategy;

import com.lwkandroid.wings.net.bean.ApiCacheOptions;
import com.lwkandroid.wings.net.bean.ApiCacheResultBean;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;

/**
 * Created by LWK
 * TODO 先返回本地缓存，然后获取网络数据，回调2次的策略
 */

public class CacheAfterRemoteStrategy extends BaseStrategy
{
    @Override
    public <T> Observable<ApiCacheResultBean<T>> excute(ApiCacheOptions options, Observable<T> source, Class<T> clazz)
    {
        Observable<ApiCacheResultBean<T>> cache = loadCache(options, clazz, true);
        Observable<ApiCacheResultBean<T>> remote = loadRemote(options, clazz, source, false);
        return Observable.concat(cache, remote)
                .filter(new Predicate<ApiCacheResultBean<T>>()
                {
                    @Override
                    public boolean test(@NonNull ApiCacheResultBean<T> cacheResultBean) throws Exception
                    {
                        return cacheResultBean != null && cacheResultBean.getData() != null;
                    }
                });
    }
}
