package com.lwkandroid.wings.net.requst;

import com.lwkandroid.wings.net.ApiService;
import com.lwkandroid.wings.net.bean.ApiResultCacheWrapper;
import com.lwkandroid.wings.net.constants.ApiConstants;
import com.lwkandroid.wings.net.constants.ApiRequestType;
import com.lwkandroid.wings.net.response.ApiStringResponseImpl;
import com.lwkandroid.wings.net.response.IApiStringResponse;
import com.lwkandroid.wings.net.utils.RequestBodyUtils;
import com.lwkandroid.wings.utils.json.JsonUtils;

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
        return mStringResponseImpl.returnStringResponseWithCacheWrapped();
    }

    @Override
    public Observable<String> returnStringResponse()
    {
        return mStringResponseImpl.returnStringResponse();
    }

    @Override
    public <T> Observable<ApiResultCacheWrapper<T>> parseAsObjectWithCacheWrapped(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseAsObjectWithCacheWrapped(tOfClass);
    }

    @Override
    public <T> Observable<T> parseAsObject(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseAsObject(tOfClass);
    }

    @Override
    public <T> Observable<ApiResultCacheWrapper<List<T>>> parseAsListWithCacheWrapped(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseAsListWithCacheWrapped(tOfClass);
    }

    @Override
    public <T> Observable<List<T>> parseAsList(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseAsList(tOfClass);
    }
}
