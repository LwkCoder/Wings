package com.lwkandroid.wings.net.requst;

import android.text.TextUtils;

import com.lwkandroid.wings.net.ApiService;
import com.lwkandroid.wings.net.constants.ApiRequestType;
import com.lwkandroid.wings.net.convert.ApiResponseConvert;
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
