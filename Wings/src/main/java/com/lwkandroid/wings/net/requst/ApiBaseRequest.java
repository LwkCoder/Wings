package com.lwkandroid.wings.net.requst;

import com.lwkandroid.wings.net.ApiService;
import com.lwkandroid.wings.net.RxHttp;
import com.lwkandroid.wings.net.bean.ApiRequestOptions;
import com.lwkandroid.wings.net.constants.ApiRequestType;
import com.lwkandroid.wings.net.utils.RetrofitUtils;
import com.lwkandroid.wings.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * Created by LWK
 * TODO 请求构造体基类
 */

public abstract class ApiBaseRequest<T extends ApiRequestOptions> extends ApiRequestOptions<T>
{
    public ApiBaseRequest(String url, @ApiRequestType.Type int type)
    {
        setUrl(url);
        setRequestType(type);
    }

    /**
     * 执行请求的过程
     */
    protected Observable<ResponseBody> invokeRequest()
    {
        String baseUrl = StringUtils.isNotEmpty(getBaseUrl()) ? getBaseUrl() : RxHttp.getGlobalOptions().getBaseUrl();
        long readTimeOut = getReadTimeOut() != -1 ? getReadTimeOut() : RxHttp.getGlobalOptions().getReadTimeOut();
        long writeTimeOut = getWriteTimeOut() != -1 ? getWriteTimeOut() : RxHttp.getGlobalOptions().getWriteTimeOut();
        long connectTimeOut = getConnectTimeOut() != -1 ? getConnectTimeOut() : RxHttp.getGlobalOptions().getConnectTimeOut();

        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        okBuilder.readTimeout(readTimeOut, TimeUnit.MILLISECONDS);
        okBuilder.writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS);
        okBuilder.connectTimeout(connectTimeOut, TimeUnit.MILLISECONDS);

        //设置拦截器
        Map<String, Interceptor> allInterceptorMap =
                getUsefulInterceptors(getInterceptorMap(),
                        RxHttp.getGlobalOptions().getInterceptorMap(),
                        isRemoveAllGlobalInterceptors(),
                        getRemoveInterceptorList());
        if (allInterceptorMap.size() > 0)
        {
            for (Interceptor interceptor : allInterceptorMap.values())
            {
                okBuilder.addInterceptor(interceptor);
            }
        }

        //设置网络拦截器
        Map<String, Interceptor> allNetInterceptorMap =
                getUsefulInterceptors(getNetInterceptorMap(),
                        RxHttp.getGlobalOptions().getNetInterceptorMap(),
                        isRemoveAllGlobalNetInterceptors(),
                        getRemoveNetInterceptorList());
        if (allNetInterceptorMap.size() > 0)
        {
            for (Interceptor interceptor : allNetInterceptorMap.values())
            {
                okBuilder.addNetworkInterceptor(interceptor);
            }
        }

        //获取Headers
        Map<String, String> allHeadersMap = getUsefulParams(getHeadersMap(),
                RxHttp.getGlobalOptions().getHeadersMap(),
                isRemoveAllGlobalHeaders(),
                getRemoveHeaderList());

        //获取表单参数
        Map<String, String> allFormDatasMap = getUsefulParams(getFormDatasMap(),
                RxHttp.getGlobalOptions().getFormDatasMap(),
                isRemoveAllGlobalFormDatas(),
                getRemoveFormDatasList());

        //创建Retrofit对象
        Retrofit retrofit = RetrofitUtils.create(baseUrl, okBuilder.build());
        ApiService apiService = retrofit.create(ApiService.class);

        //执行请求
        return buildResponse(allHeadersMap, allFormDatasMap, getObjectRequestBody(),
                getOkHttp3RequestBody(), getJsonRequestBody(), apiService);
    }

    //计算该请求下包含的拦截器
    protected Map<String, Interceptor> getUsefulInterceptors(Map<String, Interceptor> map,
                                                             Map<String, Interceptor> globalMaps,
                                                             boolean removeGlobal,
                                                             List<String> exceptList)
    {
        Map<String, Interceptor> resultMap = new HashMap<>();
        if (!removeGlobal && globalMaps != null && globalMaps.size() > 0)
            resultMap.putAll(globalMaps);
        if (map != null && map.size() > 0)
            resultMap.putAll(map);
        if (exceptList != null && exceptList.size() > 0 && resultMap.size() > 0)
        {
            for (String tag : exceptList)
            {
                resultMap.remove(tag);
            }
        }
        return resultMap;
    }

    //计算该请求下包含的参数
    protected Map<String, String> getUsefulParams(Map<String, String> map,
                                                  Map<String, String> globalMaps,
                                                  boolean removeGlobal,
                                                  List<String> exceptList)
    {
        Map<String, String> resultMap = new HashMap<>();
        if (!removeGlobal && globalMaps != null && globalMaps.size() > 0)
            resultMap.putAll(globalMaps);
        if (map != null && map.size() > 0)
            resultMap.putAll(map);
        if (exceptList != null && exceptList.size() > 0 && resultMap.size() > 0)
        {
            for (String key : exceptList)
            {
                resultMap.remove(key);
            }
        }
        return resultMap;
    }

    /**
     * 子类实现该方法执行网络请求过程
     */
    protected abstract Observable<ResponseBody> buildResponse(Map<String, String> headersMap,
                                                              Map<String, String> formDatasMap,
                                                              Object objectRequestBody,
                                                              RequestBody okHttp3RequestBody,
                                                              String jsonRequestBody,
                                                              ApiService service);
}
