package com.lwkandroid.wings.net.parser;

import com.lwkandroid.wings.net.RxHttp;
import com.lwkandroid.wings.net.bean.ApiException;
import com.lwkandroid.wings.net.bean.IApiRestfulResult;
import com.lwkandroid.wings.net.constants.ApiExceptionCode;
import com.lwkandroid.wings.utils.StringUtils;
import com.lwkandroid.wings.utils.json.JsonUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * 将String类型的网络请求结果转换为{@link IApiRestfulResult}的实现类
 *
 * @author LWK
 */
public class ApiStringParser implements IApiStringParser
{
    @Override
    public <T> ObservableTransformer<String, T> parseDataObjectFromResponse(final Class<T> dataClass)
    {
        return new ObservableTransformer<String, T>()
        {
            @Override
            public ObservableSource<T> apply(Observable<String> upstream)
            {
                return upstream.map(new Function<String, T>()
                {
                    @Override
                    public T apply(String s) throws Exception
                    {
                        return JsonUtils.get().parseJsonObject(s, dataClass);
                    }
                });
            }
        };
    }

    @Override
    public <T> ObservableTransformer<String, T> parseDataObjectFromRestful(final Class<T> dataClass)
    {
        return new ObservableTransformer<String, T>()
        {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<String> upstream)
            {
                return upstream.map(new Function<String, T>()
                {
                    @Override
                    public T apply(@NonNull String s) throws Exception
                    {
                        String dataJsonString = parseApiResultDataJson(s);
                        return StringUtils.isNotEmpty(dataJsonString) ?
                                JsonUtils.get().parseJsonObject(dataJsonString, dataClass) : dataClass.newInstance();
                    }
                });
            }
        };
    }

    @Override
    public <T> ObservableTransformer<String, List<T>> parseDataListFromRestful(final Class<T> dataClass)
    {
        return new ObservableTransformer<String, List<T>>()
        {
            @Override
            public ObservableSource<List<T>> apply(@NonNull Observable<String> upstream)
            {
                return upstream.map(new Function<String, List<T>>()
                {
                    @Override
                    public List<T> apply(@NonNull String s) throws Exception
                    {
                        String dataJsonString = parseApiResultDataJson(s);
                        return StringUtils.isNotEmpty(dataJsonString) ?
                                JsonUtils.get().parseJsonArray(dataJsonString, dataClass) : new ArrayList<T>();
                    }
                });
            }
        };
    }

    @Override
    public <T> ObservableTransformer<String, List<T>> parseDataListFromResponse(final Class<T> dataClass)
    {
        return new ObservableTransformer<String, List<T>>()
        {
            @Override
            public ObservableSource<List<T>> apply(Observable<String> upstream)
            {
                return upstream.map(new Function<String, List<T>>()
                {
                    @Override
                    public List<T> apply(String s) throws Exception
                    {
                        return JsonUtils.get().parseJsonArray(s, dataClass);
                    }
                });
            }
        };
    }

    /**
     * 将获取String请求结果中Data的Json字符串
     *
     * @param response 网络请求String结果
     * @return
     * @throws ApiException
     */
    private String parseApiResultDataJson(String response) throws ApiException
    {
        IApiRestfulResult<Object> result = JsonUtils.get().parseJsonObject(response, RxHttp.getGlobalOptions().getApiRestfulResultType());
        if (result == null)
        {
            throw new ApiException(ApiExceptionCode.RESPONSE_EMPTY, "Could not get any Response");
        }
        if (!result.isResultOK())
        {
            throw new ApiException(result.getCode(), result.getMessage());
        }
        Object resultData = result.getData();
        return resultData != null ? JsonUtils.get().toJson(result.getData()) : null;
    }
}
