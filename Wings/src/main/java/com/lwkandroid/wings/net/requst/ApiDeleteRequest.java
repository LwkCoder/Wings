package com.lwkandroid.wings.net.requst;

import com.lwkandroid.wings.net.ApiService;
import com.lwkandroid.wings.net.constants.ApiConstants;
import com.lwkandroid.wings.net.constants.ApiRequestType;
import com.lwkandroid.wings.net.parser.ApiResponseConvert;
import com.lwkandroid.wings.net.utils.RequestBodyUtils;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by LWK
 * TODO Delete请求
 */

public class ApiDeleteRequest extends ApiBaseRequest<ApiDeleteRequest> implements IApiStringResponse
{
    public ApiDeleteRequest(String url)
    {
        super(url, ApiRequestType.DELETE);
    }

    @Override
    protected Observable<ResponseBody> buildResponse(Map<String, String> headersMap,
                                                     Map<String, String> formDatasMap,
                                                     Object objectRequestBody,
                                                     RequestBody okHttp3RequestBody,
                                                     String jsonRequestBody,
                                                     ApiService service)
    {
        if (objectRequestBody != null)
        {
            return service.deleteObjectBody(getUrl(), headersMap, objectRequestBody);
        } else if (okHttp3RequestBody != null)
        {
            return service.deleteOkHttp3Body(getUrl(), headersMap, okHttp3RequestBody);
        } else if (jsonRequestBody != null)
        {
            RequestBody jsonBody = RequestBodyUtils.createJsonBody(jsonRequestBody);
            headersMap.put(ApiConstants.HEADER_KEY_CONTENT_TYPE, ApiConstants.HEADER_VALUE_JSON);
            headersMap.put(ApiConstants.HEADER_KEY_ACCEPT, ApiConstants.HEADER_VALUE_JSON);
            return service.deleteJsonBody(getUrl(), headersMap, jsonBody);
        } else
        {
            return service.delete(getUrl(), headersMap, formDatasMap);
        }
    }

    @Override
    public <T> Observable<T> parseAsObject(Class<T> tOfClass)
    {
        return invokeRequest()
                .compose(ApiResponseConvert.responseToString())
                .compose(getApiStringParser().parseDataAsObject(tOfClass));
    }

    @Override
    public <T> Observable<List<T>> parseAsList(Class<T> tOfClass)
    {
        return invokeRequest()
                .compose(ApiResponseConvert.responseToString())
                .compose(getApiStringParser().parseDataAsList(tOfClass));
    }
}
