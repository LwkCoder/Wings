package com.lwkandroid.wings.net.parser;

import java.util.List;

import io.reactivex.ObservableTransformer;

/**
 * Created by LWK
 * 将String类型的网络请求结果转换为具体对象的接口
 */

public interface IApiStringParser
{
    /**
     * 将结果解析为某一Object类型数据
     *
     * @param dataClass 对象的Class
     * @param <T>       对象泛型
     * @return
     */
    <T> ObservableTransformer<String, T> parseCustomDataObject(final Class<T> dataClass);

    /**
     * 将结果解析为{@link com.lwkandroid.wings.net.bean.IApiRestfulResult}，并获取Object类型数据
     *
     * @param dataClass 对象的Class
     * @param <T>       对象泛型
     */
    <T> ObservableTransformer<String, T> parseRestfulDataObject(final Class<T> dataClass);

    /**
     * 将结果解析为某一Object类型集合数据
     *
     * @param dataClass 对象的Class
     * @param <T>       对象泛型
     * @return
     */
    <T> ObservableTransformer<String, List<T>> parseCustomDataList(final Class<T> dataClass);

    /**
     * 将结果解析为{@link com.lwkandroid.wings.net.bean.IApiRestfulResult}，并获取Object类型集合数据
     *
     * @param dataClass 对象的Class
     * @param <T>       对象泛型
     */
    <T> ObservableTransformer<String, List<T>> parseRestfulDataList(final Class<T> dataClass);
}
