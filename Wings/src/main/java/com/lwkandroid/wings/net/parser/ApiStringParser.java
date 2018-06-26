package com.lwkandroid.wings.net.parser;

import com.lwkandroid.wings.net.RxHttp;
import com.lwkandroid.wings.net.bean.ApiException;
import com.lwkandroid.wings.net.bean.IApiResult;
import com.lwkandroid.wings.net.constants.ApiExceptionCode;
import com.lwkandroid.wings.utils.StringUtils;
import com.lwkandroid.wings.utils.json.IJsonStrategy;
import com.lwkandroid.wings.utils.json.JsonUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by LWK
 * TODO 将String类型的网络请求结果转换为{@link com.lwkandroid.wings.net.bean.IApiResult}的实现类
 */

public class ApiStringParser implements IApiStringParser
{
    protected static final IJsonStrategy JSON_PARSER = JsonUtils.get();

    @Override
    public <T> ObservableTransformer<String, T> parseAsObject(final Class<T> clazz)
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
                        IApiResult<Object> result = JSON_PARSER.parseJsonObject(s, RxHttp.getGlobalOptions().getApiResultType());
                        if (result == null)
                            throw new ApiException(ApiExceptionCode.RESPONSE_EMPTY, "Could not get any Response", "Could not get any Response");
                        if (!result.isResultOK())
                            throw new ApiException(result.getCode(), result.getMessage(), result.getMessage());

                        String dataJsonString = result.getData() != null ?
                                JSON_PARSER.toJson(result.getData()) : null;
                        if (clazz == String.class)
                            return StringUtils.isNotEmpty(dataJsonString) ?
                                    (T) dataJsonString : (T) "";
                        else
                            return StringUtils.isNotEmpty(dataJsonString) ?
                                    JSON_PARSER.parseJsonObject(dataJsonString, clazz) : clazz.newInstance();
                    }
                });
            }
        };
    }

    @Override
    public <T> ObservableTransformer<String, List<T>> parseAsList(final Class<T> clazz)
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
                        IApiResult<Object> result = JSON_PARSER.parseJsonObject(s, RxHttp.getGlobalOptions().getApiResultType());
                        if (result == null)
                            throw new ApiException(ApiExceptionCode.RESPONSE_EMPTY, "Could not get any Response", "Could not get any Response");
                        if (!result.isResultOK())
                            throw new ApiException(result.getCode(), result.getMessage(), result.getMessage());

                        String dataJsonString = result.getData() != null ?
                                JSON_PARSER.toJson(result.getData()) : null;
                        return StringUtils.isNotEmpty(dataJsonString) ?
                                JSON_PARSER.parseJsonArray(dataJsonString, clazz) : new ArrayList<T>();
                    }
                });
            }
        };
    }
}
