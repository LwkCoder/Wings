package com.lwkandroid.wings.net.requst;

import com.lwkandroid.wings.net.ApiService;
import com.lwkandroid.wings.net.bean.ApiResultCacheWrapper;
import com.lwkandroid.wings.net.cache.RxCache;
import com.lwkandroid.wings.net.cache.func.ApiCacheDataGetterFunc;
import com.lwkandroid.wings.net.cache.func.ApiCacheDataParseAsDataFunc;
import com.lwkandroid.wings.net.cache.func.ApiCacheDataParseAsListFunc;
import com.lwkandroid.wings.net.constants.ApiConstants;
import com.lwkandroid.wings.net.constants.ApiRequestType;
import com.lwkandroid.wings.net.convert.ApiResponseConvert;
import com.lwkandroid.wings.net.error.ApiExceptionTransformer;
import com.lwkandroid.wings.net.response.IApiStringResponse;
import com.lwkandroid.wings.net.retry.AutoRetryFunc;
import com.lwkandroid.wings.net.utils.RequestBodyUtils;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by LWK
 * TODO Post请求
 */

public class ApiPostRequest extends ApiBaseRequest<ApiPostRequest> implements IApiStringResponse
{
    public ApiPostRequest(String url)
    {
        super(url, ApiRequestType.POST);
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
            return service.post(getUrl(), headersMap, objectRequestBody);
        } else if (okHttp3RequestBody != null)
        {
            return service.post(getUrl(), headersMap, okHttp3RequestBody);
        } else if (jsonBody != null)
        {
            RequestBody jsonRequestBody = RequestBodyUtils.createJsonBody(jsonBody);
            headersMap.put(ApiConstants.HEADER_KEY_CONTENT_TYPE, ApiConstants.HEADER_VALUE_JSON);
            headersMap.put(ApiConstants.HEADER_KEY_ACCEPT, ApiConstants.HEADER_VALUE_JSON);
            return service.post(getUrl(), headersMap, jsonRequestBody);
        } else
        {
            return service.post(getUrl(), headersMap, formDatasMap);
        }
    }

    @Override
    public Observable<ApiResultCacheWrapper<String>> returnStringResponseWithCacheWraped()
    {
        return invokeRequest()
                .compose(ApiResponseConvert.responseToString())
                .compose(RxCache.transform(getFinalCacheOptions(), String.class))
                .compose(new ApiExceptionTransformer<ApiResultCacheWrapper<String>>())
                .retryWhen(new AutoRetryFunc(getUrl(), getAutoRetryCount(), getAutoRetryDelay(), getAutoRetryJudge()));
    }

    @Override
    public Observable<String> returnStringResponse()
    {
        return returnStringResponseWithCacheWraped()
                .map(new ApiCacheDataGetterFunc<String>());
    }

    @Override
    public <T> Observable<ApiResultCacheWrapper<T>> parseAsObjectWithCacheWraped(final Class<T> tOfClass)
    {
        return returnStringResponseWithCacheWraped()
                .flatMap(new ApiCacheDataParseAsDataFunc<T>(getApiStringParser(), tOfClass));
    }

    @Override
    public <T> Observable<T> parseAsObject(Class<T> tOfClass)
    {
        return parseAsObjectWithCacheWraped(tOfClass)
                .map(new ApiCacheDataGetterFunc<T>());
    }

    @Override
    public <T> Observable<ApiResultCacheWrapper<List<T>>> parseAsListWithCacheWraped(final Class<T> tOfClass)
    {
        return returnStringResponseWithCacheWraped()
                .flatMap(new ApiCacheDataParseAsListFunc<T>(getApiStringParser(), tOfClass));
    }

    @Override
    public <T> Observable<List<T>> parseAsList(Class<T> tOfClass)
    {
        return parseAsListWithCacheWraped(tOfClass)
                .map(new ApiCacheDataGetterFunc<List<T>>());
    }
}
