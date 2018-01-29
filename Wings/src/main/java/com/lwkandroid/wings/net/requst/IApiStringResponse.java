package com.lwkandroid.wings.net.requst;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by LWK
 * TODO 定义字符串网络请求结果的转换方法
 */

public interface IApiStringResponse
{
    /**
     * 将字符串网络请求转换为某一个对象
     */
    <T> Observable<T> parseAsObject(Class<T> tOfClass);

    /**
     * 将字符串网络请求转换为某一个对象的集合
     */
    <T> Observable<List<T>> parseAsList(Class<T> tOfClass);
}
