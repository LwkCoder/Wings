package com.lwkandroid.wings.net.cache.strategy;

import com.lwkandroid.wings.net.bean.ApiCacheOptions;
import com.lwkandroid.wings.net.bean.ApiResultCacheWrapper;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import okio.ByteString;

/**
 * Created by LWK
 * TODO 先返回本地缓存，然后获取网络数据，如果数据一样就忽略的策略
 */

public class ApiCacheAfterRemoteDiffStrategy extends ApiCacheBaseStrategy
{
    @Override
    public <T> Observable<ApiResultCacheWrapper<T>> execute(ApiCacheOptions options, Observable<T> source, Class<T> clazz)
    {
        Observable<ApiResultCacheWrapper<T>> cache = loadCache(options, clazz, true);
        Observable<ApiResultCacheWrapper<T>> remote = loadRemote(options, clazz, source, false);
        return Observable.concat(cache, remote)
                .filter(new Predicate<ApiResultCacheWrapper<T>>()
                {
                    @Override
                    public boolean test(@NonNull ApiResultCacheWrapper<T> tCacheResult) throws Exception
                    {
                        return tCacheResult != null && tCacheResult.getData() != null;
                    }
                }).distinctUntilChanged(new Function<ApiResultCacheWrapper<T>, String>()
                {
                    @Override
                    public String apply(ApiResultCacheWrapper<T> cacheResultBean) throws Exception
                    {
                        return ByteString.of(cacheResultBean.getData().toString().getBytes()).md5().hex();
                    }
                });
    }
}
