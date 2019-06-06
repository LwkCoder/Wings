package com.lwkandroid.wings.net.requst;

import android.text.TextUtils;

import com.lwkandroid.wings.net.ApiService;
import com.lwkandroid.wings.net.bean.ResultCacheWrapper;
import com.lwkandroid.wings.net.constants.ApiConstants;
import com.lwkandroid.wings.net.constants.ApiRequestType;
import com.lwkandroid.wings.net.response.ApiStringResponseImpl;
import com.lwkandroid.wings.net.response.IApiStringResponse;
import com.lwkandroid.wings.net.utils.RequestBodyUtils;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by LWK
 * Delete请求
 *
 * @author LWK
 */
public final class ApiDeleteRequest extends ApiBaseRequest<ApiDeleteRequest> implements IApiStringResponse
{
    private ApiStringResponseImpl<ApiDeleteRequest> mStringResponseImpl;

    public ApiDeleteRequest(String url)
    {
        super(url, ApiRequestType.DELETE);
        mStringResponseImpl = new ApiStringResponseImpl<>(this);
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
            return service.delete(getSubUrl(), headersMap, objectRequestBody);
        } else if (okHttp3RequestBody != null)
        {
            return service.delete(getSubUrl(), headersMap, okHttp3RequestBody);
        } else if (!TextUtils.isEmpty(jsonBody))
        {
            RequestBody jsonRequestBody = RequestBodyUtils.createJsonBody(jsonBody);
            headersMap.put(ApiConstants.HEADER_KEY_CONTENT_TYPE, ApiConstants.HEADER_VALUE_JSON);
            headersMap.put(ApiConstants.HEADER_KEY_ACCEPT, ApiConstants.HEADER_VALUE_JSON);
            return service.delete(getSubUrl(), headersMap, jsonRequestBody);
        } else
        {
            return service.delete(getSubUrl(), headersMap, formDataMap);
        }
    }

    @Override
    public Observable<ResultCacheWrapper<String>> returnStringResponseWithCacheWrapped()
    {
        return mStringResponseImpl.returnStringResponseWithCacheWrapped();
    }

    @Override
    public Observable<String> returnStringResponse()
    {
        return mStringResponseImpl.returnStringResponse();
    }

    @Override
    public <T> Observable<ResultCacheWrapper<T>> parseDataObjectFromApiResultWithCacheWrapped(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseDataObjectFromApiResultWithCacheWrapped(tOfClass);
    }

    @Override
    public <T> Observable<T> parseDataObjectFromApiResult(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseDataObjectFromApiResult(tOfClass);
    }

    @Override
    public <T> Observable<ResultCacheWrapper<T>> parseDataObjectFromResponseWithCacheWrapped(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseDataObjectFromResponseWithCacheWrapped(tOfClass);
    }

    @Override
    public <T> Observable<T> parseDataObjectResponseResult(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseDataObjectResponseResult(tOfClass);
    }

    @Override
    public <T> Observable<ResultCacheWrapper<List<T>>> parseDataListFromApiResultWithCacheWrapped(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseDataListFromApiResultWithCacheWrapped(tOfClass);
    }

    @Override
    public <T> Observable<List<T>> parseDataListFromApiResult(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseDataListFromApiResult(tOfClass);
    }

    @Override
    public <T> Observable<ResultCacheWrapper<List<T>>> parseDataListFromResponseWithCacheWrapped(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseDataListFromResponseWithCacheWrapped(tOfClass);
    }

    @Override
    public <T> Observable<List<T>> parseDataListFromResponse(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseDataListFromResponse(tOfClass);
    }
}
