package com.lwkandroid.wings.net.requst;

import android.text.TextUtils;

import com.lwkandroid.wings.net.ApiService;
import com.lwkandroid.wings.net.constants.ApiConstants;
import com.lwkandroid.wings.net.constants.ApiRequestType;
import com.lwkandroid.wings.net.convert.ApiResponseConvert;
import com.lwkandroid.wings.net.response.IApiStringResponse;
import com.lwkandroid.wings.net.utils.RequestBodyUtils;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by LWK
 * TODO Put请求
 */

public class ApiPutRequest extends ApiBaseRequest<ApiPutRequest> implements IApiStringResponse
{
    public ApiPutRequest(String url)
    {
        super(url, ApiRequestType.PUT);
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
            return service.put(getUrl(), headersMap, objectRequestBody);
        } else if (okHttp3RequestBody != null)
        {
            return service.put(getUrl(), headersMap, okHttp3RequestBody);
        } else if (!TextUtils.isEmpty(jsonBody))
        {
            RequestBody jsonRequestBody = RequestBodyUtils.createJsonBody(jsonBody);
            headersMap.put(ApiConstants.HEADER_KEY_CONTENT_TYPE, ApiConstants.HEADER_VALUE_JSON);
            headersMap.put(ApiConstants.HEADER_KEY_ACCEPT, ApiConstants.HEADER_VALUE_JSON);
            return service.put(getUrl(), headersMap, jsonRequestBody);
        } else
        {
            return service.put(getUrl(), headersMap, formDatasMap);
        }
    }

    @Override
    public Observable<String> returnStringResponse()
    {
        return invokeRequest()
                .compose(ApiResponseConvert.responseToString());
    }

    @Override
    public <T> Observable<T> parseAsObject(Class<T> tOfClass)
    {
        return returnStringResponse()
                .compose(getApiStringParser().parseAsObject(tOfClass));
    }

    @Override
    public <T> Observable<List<T>> parseAsList(Class<T> tOfClass)
    {
        return returnStringResponse()
                .compose(getApiStringParser().parseAsList(tOfClass));
    }
}
