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
import com.lwkandroid.wings.utils.json.JsonUtils;

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
    private boolean mIsFormDataJson = false;

    public ApiPostRequest(String url)
    {
        super(url, ApiRequestType.POST);
    }

    public ApiPostRequest convertToJsonRequest()
    {
        mIsFormDataJson = true;
        return this;
    }

    public boolean isConvertToJsonRequest()
    {
        return mIsFormDataJson;
    }

    @Override
    protected Observable<ResponseBody> buildResponse(Map<String, String> headersMap,
                                                     Map<String, Object> formDataMap,
                                                     Object objectRequestBody,
                                                     RequestBody okHttp3RequestBody,
                                                     String jsonBody,
                                                     ApiService service)
    {
        if (objectRequestBody != null)
        {
            return service.post(getSubUrl(), headersMap, objectRequestBody);
        } else if (okHttp3RequestBody != null)
        {
            return service.post(getSubUrl(), headersMap, okHttp3RequestBody);
        } else if (jsonBody != null)
        {
            RequestBody jsonRequestBody = RequestBodyUtils.createJsonBody(jsonBody);
            headersMap.put(ApiConstants.HEADER_KEY_CONTENT_TYPE, ApiConstants.HEADER_VALUE_JSON);
            headersMap.put(ApiConstants.HEADER_KEY_ACCEPT, ApiConstants.HEADER_VALUE_JSON);
            return service.post(getSubUrl(), headersMap, jsonRequestBody);
        } else if (mIsFormDataJson)
        {
            Map<String, Object> jsonMap = getFormDataMap();
            String jsonString = JsonUtils.get().toJson(jsonMap);
            RequestBody jsonRequestBody = RequestBodyUtils.createJsonBody(jsonString);
            headersMap.put(ApiConstants.HEADER_KEY_CONTENT_TYPE, ApiConstants.HEADER_VALUE_JSON);
            headersMap.put(ApiConstants.HEADER_KEY_ACCEPT, ApiConstants.HEADER_VALUE_JSON);
            return service.post(getSubUrl(), headersMap, jsonRequestBody);
        }
        {
            return service.post(getSubUrl(), headersMap, formDataMap);
        }
    }

    @Override
    public Observable<ApiResultCacheWrapper<String>> returnStringResponseWithCacheWrapped()
    {
        return invokeRequest()
                .compose(ApiResponseConvert.responseToString())
                .compose(RxCache.transform(getFinalCacheOptions(), String.class))
                .compose(new ApiExceptionTransformer<ApiResultCacheWrapper<String>>())
                .retryWhen(new AutoRetryFunc(getSubUrl(), getAutoRetryCount(), getAutoRetryDelay(), getAutoRetryJudge()));
    }

    @Override
    public Observable<String> returnStringResponse()
    {
        return returnStringResponseWithCacheWrapped()
                .map(new ApiCacheDataGetterFunc<String>());
    }

    @Override
    public <T> Observable<ApiResultCacheWrapper<T>> parseAsObjectWithCacheWrapped(final Class<T> tOfClass)
    {
        return returnStringResponseWithCacheWrapped()
                .flatMap(new ApiCacheDataParseAsDataFunc<T>(getApiStringParser(), tOfClass));
    }

    @Override
    public <T> Observable<T> parseAsObject(Class<T> tOfClass)
    {
        return parseAsObjectWithCacheWrapped(tOfClass)
                .map(new ApiCacheDataGetterFunc<T>());
    }

    @Override
    public <T> Observable<ApiResultCacheWrapper<List<T>>> parseAsListWithCacheWrapped(final Class<T> tOfClass)
    {
        return returnStringResponseWithCacheWrapped()
                .flatMap(new ApiCacheDataParseAsListFunc<T>(getApiStringParser(), tOfClass));
    }

    @Override
    public <T> Observable<List<T>> parseAsList(Class<T> tOfClass)
    {
        return parseAsListWithCacheWrapped(tOfClass)
                .map(new ApiCacheDataGetterFunc<List<T>>());
    }
}
