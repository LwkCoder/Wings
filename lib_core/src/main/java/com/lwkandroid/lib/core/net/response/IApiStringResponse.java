package com.lwkandroid.lib.core.net.response;


import com.lwkandroid.lib.core.net.bean.ResultCacheWrapper;

import java.util.List;

import io.reactivex.Observable;

/**
 * 定义字符串网络请求结果的转换方法
 *
 * @author LWK
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
    <T> Observable<ResultCacheWrapper<T>> parseRestfulDataObjectWithCache(Class<T> tOfClass);

    /**
     * 直接获取ApiRestfulResult对象内数据的网络请求结果
     */
    <T> Observable<T> parseRestfulDataObject(Class<T> tOfClass);

    /**
     * 获取缓存结果包装的某一个对象的网络请求结果
     */
    <T> Observable<ResultCacheWrapper<T>> parseCustomDataObjectWithCache(Class<T> tOfClass);

    /**
     * 直接获取某一个对象的网络请求结果
     */
    <T> Observable<T> parseCustomDataObject(Class<T> tOfClass);

    /**
     * 获取缓存结果包装的ApiRestfulResult对象内数据集合的网络请求结果
     */
    <T> Observable<ResultCacheWrapper<List<T>>> parseRestfulDataListWithCache(Class<T> tOfClass);

    /**
     * 直接获取ApiRestfulResult对象内数据集合的网络请求结果
     */
    <T> Observable<List<T>> parseRestfulDataList(Class<T> tOfClass);

    /**
     * 获取缓存结果包装的某个一对象的集合的网络请求结果
     */
    <T> Observable<ResultCacheWrapper<List<T>>> parseCustomDataListWithCache(Class<T> tOfClass);

    /**
     * 直接获取某一个对象的集合的网络请求结果
     */
    <T> Observable<List<T>> parseCustomDataList(Class<T> tOfClass);

    <T> Observable<ResultCacheWrapper<T[]>> parseRestfulDataArrayWithCache(Class<T> tOfClass);

    <T> Observable<T[]> parseRestfulDataArray(Class<T> tOfClass);

    <T> Observable<ResultCacheWrapper<T[]>> parseCustomDataArrayWithCache(Class<T> tOfClass);

    <T> Observable<T[]> parseCustomDataArray(Class<T> tOfClass);
}
