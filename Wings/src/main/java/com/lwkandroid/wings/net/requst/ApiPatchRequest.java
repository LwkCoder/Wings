package com.lwkandroid.wings.net.requst;

import com.lwkandroid.wings.net.ApiService;
import com.lwkandroid.wings.net.constants.ApiRequestType;
import com.lwkandroid.wings.net.parser.ApiResponseConvert;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by LWK
 * TODO Patch请求
 */

public class ApiPatchRequest extends ApiBaseRequest<ApiPatchRequest> implements IApiStringResponse
{
    public ApiPatchRequest(String url)
    {
        super(url, ApiRequestType.PATCH);
    }

    @Override
    protected Observable<ResponseBody> buildResponse(Map<String, String> headersMap,
                                                     Map<String, String> formDatasMap,
                                                     Object objectRequestBody,
                                                     RequestBody okHttp3RequestBody,
                                                     String jsonRequestBody,
                                                     ApiService service)
    {
        return service.patch(getUrl(), headersMap, formDatasMap);
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
