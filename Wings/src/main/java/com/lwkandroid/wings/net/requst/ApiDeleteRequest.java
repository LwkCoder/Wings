package com.lwkandroid.wings.net.requst;

import android.text.TextUtils;

import com.lwkandroid.wings.net.ApiService;
import com.lwkandroid.wings.net.bean.ApiCacheResultBean;
import com.lwkandroid.wings.net.cache.RxCache;
import com.lwkandroid.wings.net.constants.ApiConstants;
import com.lwkandroid.wings.net.constants.ApiRequestType;
import com.lwkandroid.wings.net.convert.ApiResponseConvert;
import com.lwkandroid.wings.net.response.IApiStringResponse;
import com.lwkandroid.wings.net.utils.RequestBodyUtils;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by LWK
 * TODO Delete请求
 */

public class ApiDeleteRequest extends ApiBaseRequest<ApiDeleteRequest> implements IApiStringResponse
{
    public ApiDeleteRequest(String url)
    {
        super(url, ApiRequestType.DELETE);
    }

    @Override
    protected Observable<ResponseBody> buildResponse(Map<String, String> headersMap,
                                                     Map<String, String> formDatasMap,
                                                     Object objectRequestBody,
                                                     RequestBody okHttp3RequestBody,
                                                     String jsonBody,
                                                     ApiService service)
    {
        if (objectRequestBody != null)
        {
            return service.delete(getUrl(), headersMap, objectRequestBody);
        } else if (okHttp3RequestBody != null)
        {
            return service.delete(getUrl(), headersMap, okHttp3RequestBody);
        } else if (!TextUtils.isEmpty(jsonBody))
        {
            RequestBody jsonRequestBody = RequestBodyUtils.createJsonBody(jsonBody);
            headersMap.put(ApiConstants.HEADER_KEY_CONTENT_TYPE, ApiConstants.HEADER_VALUE_JSON);
            headersMap.put(ApiConstants.HEADER_KEY_ACCEPT, ApiConstants.HEADER_VALUE_JSON);
            return service.delete(getUrl(), headersMap, jsonRequestBody);
        } else
        {
            return service.delete(getUrl(), headersMap, formDatasMap);
        }
    }

    @Override
    public Observable<ApiCacheResultBean<String>> returnStringResponseWithCacheWraped()
    {
        return invokeRequest()
                .compose(ApiResponseConvert.responseToString())
                .compose(RxCache.transform(getFinalCacheOptions(), String.class));
    }

    @Override
    public Observable<String> returnStringResponse()
    {
        return returnStringResponseWithCacheWraped()
                .map(new Function<ApiCacheResultBean<String>, String>()
                {
                    @Override
                    public String apply(ApiCacheResultBean<String> resultBean) throws Exception
                    {
                        return resultBean.getData();
                    }
                });
    }

    @Override
    public <T> Observable<ApiCacheResultBean<T>> parseAsObjectWithCacheWraped(final Class<T> tOfClass)
    {
        return returnStringResponseWithCacheWraped()
                .flatMap(new Function<ApiCacheResultBean<String>, ObservableSource<ApiCacheResultBean<T>>>()
                {
                    @Override
                    public ObservableSource<ApiCacheResultBean<T>> apply(final ApiCacheResultBean<String> resultBean) throws Exception
                    {
                        return Observable.just(resultBean.getData())
                                .compose(getApiStringParser().parseAsObject(tOfClass))
                                .map(new Function<T, ApiCacheResultBean<T>>()
                                {
                                    @Override
                                    public ApiCacheResultBean<T> apply(T t) throws Exception
                                    {
                                        return new ApiCacheResultBean<>(resultBean.isCache(), t);
                                    }
                                });
                    }
                });
    }

    @Override
    public <T> Observable<T> parseAsObject(Class<T> tOfClass)
    {
        return parseAsObjectWithCacheWraped(tOfClass)
                .map(new Function<ApiCacheResultBean<T>, T>()
                {
                    @Override
                    public T apply(ApiCacheResultBean<T> cacheResultBean) throws Exception
                    {
                        return cacheResultBean.getData();
                    }
                });
    }

    @Override
    public <T> Observable<ApiCacheResultBean<List<T>>> parseAsListWithCacheWraped(final Class<T> tOfClass)
    {
        return returnStringResponseWithCacheWraped()
                .flatMap(new Function<ApiCacheResultBean<String>, ObservableSource<ApiCacheResultBean<List<T>>>>()
                {
                    @Override
                    public ObservableSource<ApiCacheResultBean<List<T>>> apply(final ApiCacheResultBean<String> resultBean) throws Exception
                    {
                        return Observable.just(resultBean.getData())
                                .compose(getApiStringParser().parseAsList(tOfClass))
                                .map(new Function<List<T>, ApiCacheResultBean<List<T>>>()
                                {
                                    @Override
                                    public ApiCacheResultBean<List<T>> apply(List<T> ts) throws Exception
                                    {
                                        return new ApiCacheResultBean<>(resultBean.isCache(), ts);
                                    }
                                });
                    }
                });
    }

    @Override
    public <T> Observable<List<T>> parseAsList(Class<T> tOfClass)
    {
        return parseAsListWithCacheWraped(tOfClass)
                .map(new Function<ApiCacheResultBean<List<T>>, List<T>>()
                {
                    @Override
                    public List<T> apply(ApiCacheResultBean<List<T>> resultBean) throws Exception
                    {
                        return resultBean.getData();
                    }
                });
    }
}
