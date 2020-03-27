package com.lwkandroid.lib.core.net.requst;


import com.lwkandroid.lib.core.net.ApiService;
import com.lwkandroid.lib.core.net.bean.ResultCacheWrapper;
import com.lwkandroid.lib.core.net.constants.ApiConstants;
import com.lwkandroid.lib.core.net.constants.ApiRequestType;
import com.lwkandroid.lib.core.net.response.ApiStringResponseImpl;
import com.lwkandroid.lib.core.net.response.IApiStringResponse;
import com.lwkandroid.lib.core.net.utils.RequestBodyUtils;
import com.lwkandroid.lib.core.utils.json.JsonUtils;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by LWK
 * Post请求
 *
 * @author LWK
 */
public final class ApiPostRequest extends ApiBaseRequest<ApiPostRequest> implements IApiStringResponse
{
    private ApiStringResponseImpl<ApiPostRequest> mStringResponseImpl;
    private boolean mIsFormDataJson = false;

    public ApiPostRequest(String url)
    {
        super(url, ApiRequestType.POST);
        mStringResponseImpl = new ApiStringResponseImpl<>(this);
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
            String jsonString = JsonUtils.toJson(jsonMap);
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

    @Override
    public <T> Observable<ResultCacheWrapper<T[]>> parseRestfulDataArrayWithCache(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseCustomDataArrayWithCache(tOfClass);
    }

    @Override
    public <T> Observable<T[]> parseRestfulDataArray(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseRestfulDataArray(tOfClass);
    }

    @Override
    public <T> Observable<ResultCacheWrapper<T[]>> parseCustomDataArrayWithCache(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseCustomDataArrayWithCache(tOfClass);
    }

    @Override
    public <T> Observable<T[]> parseCustomDataArray(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseCustomDataArray(tOfClass);
    }
}
