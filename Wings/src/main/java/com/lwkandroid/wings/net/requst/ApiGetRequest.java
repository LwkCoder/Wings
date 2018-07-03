package com.lwkandroid.wings.net.requst;

import android.text.TextUtils;

import com.lwkandroid.wings.net.ApiService;
import com.lwkandroid.wings.net.bean.ApiResultCacheWrapper;
import com.lwkandroid.wings.net.cache.RxCache;
import com.lwkandroid.wings.net.cache.func.ApiCacheDataGetterFunc;
import com.lwkandroid.wings.net.cache.func.ApiCacheDataParseAsDataFunc;
import com.lwkandroid.wings.net.cache.func.ApiCacheDataParseAsListFunc;
import com.lwkandroid.wings.net.constants.ApiRequestType;
import com.lwkandroid.wings.net.convert.ApiResponseConvert;
import com.lwkandroid.wings.net.error.ApiErrorTransformer;
import com.lwkandroid.wings.net.response.IApiStringResponse;
import com.socks.library.KLog;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by LWK
 * TODO Get请求
 */

public class ApiGetRequest extends ApiBaseRequest<ApiGetRequest> implements IApiStringResponse
{

    public ApiGetRequest(String url)
    {
        super(url, ApiRequestType.GET);
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
            KLog.w("RxHttp method GET must not have a request body：\n" + objectRequestBody.toString());
        } else if (okHttp3RequestBody != null)
        {
            KLog.w("RxHttp method GET must not have a request body：\n" + okHttp3RequestBody.toString());
        } else if (!TextUtils.isEmpty(jsonBody))
        {
            KLog.w("RXHttp method GET must not have a request body：\n" + jsonBody);
        }
        return service.get(getUrl(), headersMap, formDatasMap);
    }

    @Override
    public Observable<ApiResultCacheWrapper<String>> returnStringResponseWithCacheWraped()
    {
        return invokeRequest()
                .compose(ApiResponseConvert.responseToString())
                .compose(RxCache.transform(getFinalCacheOptions(), String.class))
                .compose(new ApiErrorTransformer<ApiResultCacheWrapper<String>>());
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
