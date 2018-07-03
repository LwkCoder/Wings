package com.lwkandroid.wings.net.response;

import com.lwkandroid.wings.net.bean.ApiResultCacheWrapper;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by LWK
 * TODO 定义字符串网络请求结果的转换方法
 */

public interface IApiStringResponse
{
    /**
     * 获取缓存结果包装的字符串类型的网络请求结果
     */
    Observable<ApiResultCacheWrapper<String>> returnStringResponseWithCacheWraped();

    /**
     * 直接获取字符串类型的网络请求结果
     */
    Observable<String> returnStringResponse();

    /**
     * 获取缓存结果包装的某一个对象的网络请求结果
     */
    <T> Observable<ApiResultCacheWrapper<T>> parseAsObjectWithCacheWraped(Class<T> tOfClass);

    /**
     * 直接获取某一个对象的网络请求结果
     */
    <T> Observable<T> parseAsObject(Class<T> tOfClass);

    /**
     * 获取缓存结果包装的某个一对象的集合的网络请求结果
     */
    <T> Observable<ApiResultCacheWrapper<List<T>>> parseAsListWithCacheWraped(Class<T> tOfClass);

    /**
     * 直接获取某一个对象的集合的网络请求结果
     */
    <T> Observable<List<T>> parseAsList(Class<T> tOfClass);
}
