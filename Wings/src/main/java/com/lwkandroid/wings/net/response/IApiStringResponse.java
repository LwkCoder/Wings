package com.lwkandroid.wings.net.response;

import com.lwkandroid.wings.net.bean.ResultCacheWrapper;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by LWK
 * 定义字符串网络请求结果的转换方法
 */

public interface IApiStringResponse
{
    /**
     * 获取缓存结果包装的字符串类型的网络请求结果
     */
    Observable<ResultCacheWrapper<String>> returnStringResponseCache();

    /**
     * 直接获取字符串类型的网络请求结果
     */
    Observable<String> returnStringResponse();

    /**
     * 获取缓存结果包装的ApiRestfulResult对象内数据的网络请求结果
     */
    <T> Observable<ResultCacheWrapper<T>> parseRestfulDataObjectCache(Class<T> tOfClass);

    /**
     * 直接获取ApiRestfulResult对象内数据的网络请求结果
     */
    <T> Observable<T> parseRestfulDataObject(Class<T> tOfClass);

    /**
     * 获取缓存结果包装的某一个对象的网络请求结果
     */
    <T> Observable<ResultCacheWrapper<T>> parseDataObjectCache(Class<T> tOfClass);

    /**
     * 直接获取某一个对象的网络请求结果
     */
    <T> Observable<T> parseDataObject(Class<T> tOfClass);

    /**
     * 获取缓存结果包装的ApiResult对象内数据集合的网络请求结果
     */
    <T> Observable<ResultCacheWrapper<List<T>>> parseRestfulDataListCache(Class<T> tOfClass);

    /**
     * 直接获取ApiResult对象内数据集合的网络请求结果
     */
    <T> Observable<List<T>> parseRestfulDataList(Class<T> tOfClass);

    /**
     * 获取缓存结果包装的某个一对象的集合的网络请求结果
     */
    <T> Observable<ResultCacheWrapper<List<T>>> parseDataListCache(Class<T> tOfClass);

    /**
     * 直接获取某一个对象的集合的网络请求结果
     */
    <T> Observable<List<T>> parseDataList(Class<T> tOfClass);
}
