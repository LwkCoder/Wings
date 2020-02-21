package com.lwkandroid.lib.core.java.net.requst;

import android.text.TextUtils;

import com.lwkandroid.lib.core.java.net.ApiService;
import com.lwkandroid.lib.core.java.net.bean.ResultCacheWrapper;
import com.lwkandroid.lib.core.java.net.constants.ApiConstants;
import com.lwkandroid.lib.core.java.net.constants.ApiRequestType;
import com.lwkandroid.lib.core.java.net.response.ApiStringResponseImpl;
import com.lwkandroid.lib.core.java.net.response.IApiStringResponse;
import com.lwkandroid.lib.core.java.net.utils.RequestBodyUtils;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by LWK
 * Put请求
 *
 * @author LWK
 */
public final class ApiPutRequest extends ApiBaseRequest<ApiPutRequest> implements IApiStringResponse
{
    private ApiStringResponseImpl<ApiPutRequest> mStringResponseImpl;

    public ApiPutRequest(String url)
    {
        super(url, ApiRequestType.PUT);
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
            return service.put(getSubUrl(), headersMap, objectRequestBody);
        } else if (okHttp3RequestBody != null)
        {
            return service.put(getSubUrl(), headersMap, okHttp3RequestBody);
        } else if (!TextUtils.isEmpty(jsonBody))
        {
            RequestBody jsonRequestBody = RequestBodyUtils.createJsonBody(jsonBody);
            headersMap.put(ApiConstants.HEADER_KEY_CONTENT_TYPE, ApiConstants.HEADER_VALUE_JSON);
            headersMap.put(ApiConstants.HEADER_KEY_ACCEPT, ApiConstants.HEADER_VALUE_JSON);
            return service.put(getSubUrl(), headersMap, jsonRequestBody);
        } else
        {
            return service.put(getSubUrl(), headersMap, formDataMap);
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
    public <T> Observable<ResultCacheWrapper<T>> parseDataObjectCache(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseDataObjectCache(tOfClass);
    }

    @Override
    public <T> Observable<T> parseDataObject(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseDataObject(tOfClass);
    }

    @Override
    public <T> Observable<ResultCacheWrapper<T>> parseDataObjectByCustomCache(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseDataObjectByCustomCache(tOfClass);
    }

    @Override
    public <T> Observable<T> parseDataObjectByCustom(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseDataObjectByCustom(tOfClass);
    }

    @Override
    public <T> Observable<ResultCacheWrapper<List<T>>> parseDataListCache(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseDataListCache(tOfClass);
    }

    @Override
    public <T> Observable<List<T>> parseDataList(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseDataList(tOfClass);
    }

    @Override
    public <T> Observable<ResultCacheWrapper<List<T>>> parseDataListByCustomCache(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseDataListByCustomCache(tOfClass);
    }

    @Override
    public <T> Observable<List<T>> parseDataListByCustom(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseDataListByCustom(tOfClass);
    }
}