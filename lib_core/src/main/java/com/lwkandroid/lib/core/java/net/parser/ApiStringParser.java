package com.lwkandroid.lib.core.java.net.parser;


import com.lwkandroid.lib.core.java.net.RxHttp;
import com.lwkandroid.lib.core.java.net.bean.ApiException;
import com.lwkandroid.lib.core.java.net.bean.IApiRestfulResult;
import com.lwkandroid.lib.core.java.net.constants.ApiExceptionCode;
import com.lwkandroid.lib.core.java.utils.StringUtils;
import com.lwkandroid.lib.core.java.utils.json.JsonUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * 将String类型的网络请求结果转换为{@link IApiRestfulResult}的实现类
 *
 * @author LWK
 */
public class ApiStringParser implements IApiStringParser
{
    @Override
    public <T> ObservableTransformer<String, T> parseCustomDataObject(final Class<T> dataClass)
    {
        return upstream -> upstream.map((Function<String, T>) s -> dataClass == String.class ? (T) s : JsonUtils.fromJson(s, dataClass));
    }

    @Override
    public <T> ObservableTransformer<String, T> parseRestfulDataObject(final Class<T> dataClass)
    {
        return upstream -> upstream.map((Function<String, T>) s -> {
            String dataJsonString = parseRestfulDataJson(s);
            return StringUtils.isNotEmpty(dataJsonString) ?
                    JsonUtils.fromJson(dataJsonString, dataClass) : dataClass.newInstance();
        });
    }

    @Override
    public <T> ObservableTransformer<String, List<T>> parseRestfulDataList(final Class<T> dataClass)
    {
        return upstream -> upstream.map((Function<String, List<T>>) s -> {
            String dataJsonString = parseRestfulDataJson(s);
            return StringUtils.isNotEmpty(dataJsonString) ?
                    JsonUtils.fromJson(dataJsonString, JsonUtils.getListType(dataClass)) : new ArrayList<T>();
        });
    }

    @Override
    public <T> ObservableTransformer<String, List<T>> parseCustomDataList(final Class<T> dataClass)
    {
        return upstream -> upstream.map((Function<String, List<T>>) s -> {
            return JsonUtils.fromJson(s, JsonUtils.getListType(dataClass));
        });
    }

    /**
     * 将获取Restful中Data数据的Json
     *
     * @param response 网络请求String结果
     * @return
     * @throws ApiException
     */
    private String parseRestfulDataJson(String response) throws ApiException
    {
        IApiRestfulResult<Object> result = JsonUtils.fromJson(response, RxHttp.getGlobalOptions().getApiRestfulResultType());
        if (result == null)
        {
            throw new ApiException(ApiExceptionCode.RESPONSE_EMPTY, "Could not get any Response");
        }
        if (!result.isResultOK())
        {
            throw new ApiException(result.getCode(), result.getMessage());
        }
        Object resultData = result.getData();
        return resultData != null ? JsonUtils.toJson(result.getData()) : null;
    }
}
