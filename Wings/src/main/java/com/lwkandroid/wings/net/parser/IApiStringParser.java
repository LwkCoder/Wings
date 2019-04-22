package com.lwkandroid.wings.net.parser;

import java.util.List;

import io.reactivex.ObservableTransformer;

/**
 * Created by LWK
 *  将String类型的网络请求结果转换为具体对象的接口
 */

public interface IApiStringParser
{
    /**
     * 将结果转为单一对象
     *
     * @param clazz 对象的Class
     * @param <T>   对象泛型
     */
    <T> ObservableTransformer<String, T> parseAsObject(final Class<T> clazz);

    /**
     * 将结果转为某一对象结合
     *
     * @param clazz 对象的Class
     * @param <T>   对象泛型
     */
    <T> ObservableTransformer<String, List<T>> parseAsList(final Class<T> clazz);
}
