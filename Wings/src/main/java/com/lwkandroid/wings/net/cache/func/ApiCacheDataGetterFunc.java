package com.lwkandroid.wings.net.cache.func;

import com.lwkandroid.wings.net.bean.ApiResultCacheWrapper;

import io.reactivex.functions.Function;

/**
 * Created by LWK
 * TODO 获取缓存包装体内数据的转换方法
 */

public class ApiCacheDataGetterFunc<T> implements Function<ApiResultCacheWrapper<T>, T>
{
    @Override
    public T apply(ApiResultCacheWrapper<T> result) throws Exception
    {
        return result.getData();
    }
}
