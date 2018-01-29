package com.lwkandroid.wings.net.requst;

import com.lwkandroid.wings.net.ApiService;
import com.lwkandroid.wings.net.RxHttp;
import com.lwkandroid.wings.net.constants.ApiRequestType;
import com.lwkandroid.wings.net.parser.ApiResponseConvert;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
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
    protected Observable<ResponseBody> buildResponse(Map<String, String> headersMap, Map<String, String> formDatasMap, ApiService service)
    {
        return service.delete(getUrl(), headersMap, formDatasMap);
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
