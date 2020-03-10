package com.lwkandroid.lib.core.net.requst;

import android.text.TextUtils;

import com.lwkandroid.lib.core.net.ApiService;
import com.lwkandroid.lib.core.net.bean.ResultCacheWrapper;
import com.lwkandroid.lib.core.net.constants.ApiConstants;
import com.lwkandroid.lib.core.net.constants.ApiRequestType;
import com.lwkandroid.lib.core.net.response.ApiStringResponseImpl;
import com.lwkandroid.lib.core.net.response.IApiStringResponse;
import com.lwkandroid.lib.core.net.utils.RequestBodyUtils;

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
    public Observable<ResultCacheWrapper<String>> returnStringResponseCache()
    {
        return mStringResponseImpl.returnStringResponseCache();
    }

    @Override
    public Observable<String> returnStringResponse()
    {
        return mStringResponseImpl.returnStringResponse();
    }

    @Override
    public <T> Observable<ResultCacheWrapper<T>> parseRestfulDataObjectWithCache(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseRestfulDataObjectWithCache(tOfClass);
    }

    @Override
    public <T> Observable<T> parseRestfulDataObject(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseRestfulDataObject(tOfClass);
    }

    @Override
    public <T> Observable<ResultCacheWrapper<T>> parseCustomDataObjectWithCache(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseCustomDataObjectWithCache(tOfClass);
    }

    @Override
    public <T> Observable<T> parseCustomDataObject(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseCustomDataObject(tOfClass);
    }

    @Override
    public <T> Observable<ResultCacheWrapper<List<T>>> parseRestfulDataListWithCache(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseRestfulDataListWithCache(tOfClass);
    }

    @Override
    public <T> Observable<List<T>> parseRestfulDataList(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseRestfulDataList(tOfClass);
    }

    @Override
    public <T> Observable<ResultCacheWrapper<List<T>>> parseCustomDataListWithCache(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseCustomDataListWithCache(tOfClass);
    }

    @Override
    public <T> Observable<List<T>> parseCustomDataList(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseCustomDataList(tOfClass);
    }
}
