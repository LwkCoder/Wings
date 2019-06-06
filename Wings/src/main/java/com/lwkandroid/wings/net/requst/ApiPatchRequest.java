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
    public <T> Observable<ResultCacheWrapper<T>> parseResutfulDataObjectCache(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseResutfulDataObjectCache(tOfClass);
    }

    @Override
    public <T> Observable<T> parseResutfulDataObject(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseResutfulDataObject(tOfClass);
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
    public <T> Observable<ResultCacheWrapper<List<T>>> parseRestfulDataListCache(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseRestfulDataListCache(tOfClass);
    }

    @Override
    public <T> Observable<List<T>> parseRestfulDataList(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseRestfulDataList(tOfClass);
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
}
