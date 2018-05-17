package com.lwkandroid.wings.net.requst;

import com.lwkandroid.wings.net.ApiService;
import com.lwkandroid.wings.net.RxHttp;
import com.lwkandroid.wings.net.constants.ApiConstants;
import com.lwkandroid.wings.net.constants.ApiRequestType;
import com.lwkandroid.wings.net.parser.ApiResponseConvert;

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
                                                     String jsonRequestBody,
                                                     ApiService service)
    {
        if (objectRequestBody != null)
        {
            return service.putObjectBody(getUrl(), headersMap, objectRequestBody);
        } else if (okHttp3RequestBody != null)
        {
            return service.putOkHttp3Body(getUrl(), headersMap, okHttp3RequestBody);
        } else if (jsonRequestBody != null)
        {
            RequestBody jsonBody = RequestBody.create(okhttp3.MediaType.parse(ApiConstants.OKHPPT3_MEDIA_TYPE_JSON), jsonRequestBody);
            headersMap.put(ApiConstants.HEADER_KEY_CONTENT_TYPE, ApiConstants.HEADER_VALUE_JSON);
            headersMap.put(ApiConstants.HEADER_KEY_ACCEPT, ApiConstants.HEADER_VALUE_JSON);
            return service.putJsonBody(getUrl(), headersMap, jsonBody);
        } else
        {
            return service.put(getUrl(), headersMap, formDatasMap);
        }
    }

    @Override
    public <T> Observable<T> parseAsObject(Class<T> tOfClass)
    {
        return invokeRequest()
                .compose(ApiResponseConvert.responseToString())
                .compose(RxHttp.getGlobalOptions().getApiStringParser().parseDataAsObject(tOfClass));
    }

    @Override
    public <T> Observable<List<T>> parseAsList(Class<T> tOfClass)
    {
        return invokeRequest()
                .compose(ApiResponseConvert.responseToString())
                .compose(RxHttp.getGlobalOptions().getApiStringParser().parseDataAsList(tOfClass));
    }
}
