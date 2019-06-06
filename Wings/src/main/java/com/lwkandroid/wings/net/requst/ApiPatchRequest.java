package com.lwkandroid.wings.net.requst;

import android.text.TextUtils;

import com.lwkandroid.wings.log.KLog;
import com.lwkandroid.wings.net.ApiService;
import com.lwkandroid.wings.net.bean.ResultCacheWrapper;
import com.lwkandroid.wings.net.constants.ApiRequestType;
import com.lwkandroid.wings.net.response.ApiStringResponseImpl;
import com.lwkandroid.wings.net.response.IApiStringResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by LWK
 * Patch请求
 *
 * @author LWK
 */
public final class ApiPatchRequest extends ApiBaseRequest<ApiPatchRequest> implements IApiStringResponse
{
    private ApiStringResponseImpl<ApiPatchRequest> mStringResponseImpl;

    public ApiPatchRequest(String url)
    {
        super(url, ApiRequestType.PATCH);
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
            KLog.w("RxHttp method PATCH must not have a request body：\n" + objectRequestBody.toString());
        } else if (okHttp3RequestBody != null)
        {
            KLog.w("RxHttp method PATCH must not have a request body：\n" + okHttp3RequestBody.toString());
        } else if (!TextUtils.isEmpty(jsonBody))
        {
            KLog.w("RXHttp method PATCH must not have a request body：\n" + jsonBody);
        }

        return service.patch(getSubUrl(), headersMap, formDataMap);
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
