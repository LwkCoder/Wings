package com.lwkandroid.wings.net.requst;

import com.lwkandroid.wings.net.ApiService;
import com.lwkandroid.wings.net.RxHttp;
import com.lwkandroid.wings.net.bean.ApiCacheOptions;
import com.lwkandroid.wings.net.bean.ApiRequestOptions;
import com.lwkandroid.wings.net.constants.ApiRequestType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
        setSubUrl(url);
        setRequestType(type);
    }

    /**
     * 执行请求的过程
     */
    public Observable<ResponseBody> invokeRequest()
    {
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        okBuilder.readTimeout(getReadTimeOut(), TimeUnit.MILLISECONDS);
        okBuilder.writeTimeout(getWriteTimeOut(), TimeUnit.MILLISECONDS);
        okBuilder.connectTimeout(getConnectTimeOut(), TimeUnit.MILLISECONDS);

        /*设置HostnameVerifier*/
        if (getHostnameVerifier() != null)
        {
            okBuilder.hostnameVerifier(getHostnameVerifier());
        }

        /*设置Https证书*/
        if (getHttpsSSLParams() != null)
        {
            okBuilder.sslSocketFactory(getHttpsSSLParams().sSLSocketFactory, getHttpsSSLParams().trustManager);
        }

        //设置拦截器
        Map<String, Interceptor> allInterceptorMap = mergeParams(
                RxHttp.getGlobalOptions().getInterceptorMap(),
                getInterceptorMap(), getIgnoreInterceptors(),
                isIgnoreAllGlobalInterceptors());
        if (allInterceptorMap.size() > 0)
        {
            for (Interceptor interceptor : allInterceptorMap.values())
            {
                okBuilder.addInterceptor(interceptor);
            }
        }

        //设置网络拦截器
        Map<String, Interceptor> allNetInterceptorMap = mergeParams(
                RxHttp.getGlobalOptions().getNetInterceptorMap(),
                getNetInterceptorMap(), getIgnoreNetInterceptors(),
                isIgnoreAllGlobalNetInterceptors());
        if (allNetInterceptorMap.size() > 0)
        {
            for (Interceptor interceptor : allNetInterceptorMap.values())
            {
                okBuilder.addNetworkInterceptor(interceptor);
            }
        }

        //获取Headers
        Map<String, String> allHeadersMap = mergeParams(
                RxHttp.getGlobalOptions().getHeadersMap(),
                getHeadersMap(), getIgnoreHeaders(),
                isIgnoreAllGlobalHeaders());

        //获取表单参数
        Map<String, Object> allFormDataMap = mergeParams(
                RxHttp.getGlobalOptions().getFormDataMap(),
                getFormDataMap(), getIgnoreFormDataSet(),
                isIgnoreAllGlobalFormData());

        //添加Cookie管理类
        RxHttp.getGlobalOptions().getCookieManager().add(getCookieList());
        okBuilder.cookieJar(RxHttp.getGlobalOptions().getCookieManager());

        //创建Retrofit对象
        Retrofit retrofit = RxHttp.RETROFIT().create(getBaseUrl(), okBuilder.build());
        ApiService apiService = retrofit.create(ApiService.class);

        //执行请求
        return buildResponse(allHeadersMap, allFormDataMap, getObjectRequestBody(),
                getOkHttp3RequestBody(), getJsonRequestBody(), apiService);
    }

    /*合并全局参数和自定义参数*/
    private <P> Map<String, P> mergeParams(Map<String, P> globalParams,
                                           Map<String, P> customParams,
                                           Set<String> ignoreParams,
                                           boolean ignoreGlobal)
    {
        Map<String, P> resultMap = new HashMap<>();
        //添加全局参数
        if (!ignoreGlobal && globalParams != null)
        {
            resultMap.putAll(globalParams);
        }
        //添加自定义参数
        if (customParams != null)
        {
            resultMap.putAll(customParams);
        }
        //去除忽略的参数
        if (ignoreParams != null)
        {
            for (String param : ignoreParams)
            {
                resultMap.remove(param);
            }
        }
        return resultMap;
    }

    /**
     * 获取最终缓存参数
     */
    protected ApiCacheOptions getFinalCacheOptions()
    {
        ApiCacheOptions.Builder cacheBuilder = new ApiCacheOptions.Builder();
        cacheBuilder.appVersion(RxHttp.getGlobalOptions().getCacheVersion());
        cacheBuilder.cachePath(RxHttp.getGlobalOptions().getCachePath());
        cacheBuilder.diskMaxSize(RxHttp.getGlobalOptions().getCacheDiskMaxSize());
        cacheBuilder.cacheOpeartor(RxHttp.getGlobalOptions().getCacheOperator());
        cacheBuilder.cacheMode(getCacheMode());
        cacheBuilder.cacheTime(getCacheTime());
        cacheBuilder.cacheKey(getCacheKey());

        return cacheBuilder.build();
    }

    /**
     * 子类实现该方法执行网络请求过程
     *
     * @param headersMap         所有Header的Map
     * @param formDataMap        所有QueryParams的Map
     * @param objectRequestBody  Object对象的请求体
     * @param okHttp3RequestBody OK3RequestBody对象的请求体
     * @param jsonBody           Json格式String对象的请求体
     * @param service            Retrofit请求模版Service对象
     * @return Observable
     */
    protected abstract Observable<ResponseBody> buildResponse(Map<String, String> headersMap,
                                                              Map<String, Object> formDataMap,
                                                              Object objectRequestBody,
                                                              RequestBody okHttp3RequestBody,
                                                              String jsonBody,
                                                              ApiService service);
}
