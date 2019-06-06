package com.lwkandroid.wings.net.cache.func;

import com.lwkandroid.wings.net.bean.ResultCacheWrapper;

import io.reactivex.functions.Function;

/**
 * Created by LWK
 *  获取缓存包装体内数据的转换方法
 */

public class CacheDataGetterFunc<T> implements Function<ResultCacheWrapper<T>, T>
{
    @Override
    public T apply(ResultCacheWrapper<T> result) throws Exception
    {
        return result.getData();
    }
}
