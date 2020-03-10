package com.lwkandroid.lib.core.net.requst;

import android.text.TextUtils;

import com.lwkandroid.lib.core.log.KLog;
import com.lwkandroid.lib.core.net.ApiService;
import com.lwkandroid.lib.core.net.bean.ResultCacheWrapper;
import com.lwkandroid.lib.core.net.constants.ApiRequestType;
import com.lwkandroid.lib.core.net.response.ApiStringResponseImpl;
import com.lwkandroid.lib.core.net.response.IApiStringResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by LWK
 * Get请求
 *
 * @author LWK
 */
public final class ApiGetRequest extends ApiBaseRequest<ApiGetRequest> implements IApiStringResponse
{
    private ApiStringResponseImpl<ApiGetRequest> mStringResponseImpl;

    public ApiGetRequest(String url)
    {
        super(url, ApiRequestType.GET);
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
            KLog.w("RxHttp method GET must not have a request body：\n" + objectRequestBody.toString());
        } else if (okHttp3RequestBody != null)
        {
            KLog.w("RxHttp method GET must not have a request body：\n" + okHttp3RequestBody.toString());
        } else if (!TextUtils.isEmpty(jsonBody))
        {
            KLog.w("RXHttp method GET must not have a request body：\n" + jsonBody);
        }
        return service.get(getSubUrl(), headersMap, formDataMap);
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
