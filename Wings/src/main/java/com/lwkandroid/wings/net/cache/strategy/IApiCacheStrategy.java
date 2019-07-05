package com.lwkandroid.wings.net.cache.strategy;

import com.lwkandroid.wings.net.bean.ApiCacheOptions;
import com.lwkandroid.wings.net.bean.ResultCacheWrapper;

import io.reactivex.Observable;

/**
 * 实现缓存策略的接口，可以自定义缓存实现方式
 *
 * @author LWK
 */
public interface IApiCacheStrategy
{
    <T> Observable<ResultCacheWrapper<T>> execute(ApiCacheOptions options, Observable<T> source, Class<T> clazz);
}
