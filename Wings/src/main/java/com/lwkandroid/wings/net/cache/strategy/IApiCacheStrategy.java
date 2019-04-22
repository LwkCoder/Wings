package com.lwkandroid.wings.net.cache.strategy;

import com.lwkandroid.wings.net.bean.ApiCacheOptions;
import com.lwkandroid.wings.net.bean.ApiResultCacheWrapper;

import io.reactivex.Observable;

/**
 * Created by LWK
 *  实现缓存策略的接口，可以自定义缓存实现方式
 */

public interface IApiCacheStrategy
{
    <T> Observable<ApiResultCacheWrapper<T>> execute(ApiCacheOptions options, Observable<T> source, Class<T> clazz);
}
