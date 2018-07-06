package com.lwkandroid.wings.net.requst;

import com.lwkandroid.wings.net.ApiService;
import com.lwkandroid.wings.net.RxHttp;
import com.lwkandroid.wings.net.bean.ApiCacheOptions;
import com.lwkandroid.wings.net.bean.ApiRequestOptions;
import com.lwkandroid.wings.net.constants.ApiRequestType;
import com.lwkandroid.wings.utils.StringUtils;

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
        setUrl(url);
        setRequestType(type);
    }

    /**
     * 执行请求的过程
     */
    public Observable<ResponseBody> invokeRequest()
    {
        String baseUrl = getFinalBaseUrl();

        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        okBuilder.readTimeout(getFinalReadTimeOut(), TimeUnit.MILLISECONDS);
        okBuilder.writeTimeout(getFinalWriteTimeOut(), TimeUnit.MILLISECONDS);
        okBuilder.connectTimeout(getFinalConnectTimeOut(), TimeUnit.MILLISECONDS);

        /*设置HostnameVerifier*/
        if (getHostnameVerifier() != null)
            okBuilder.hostnameVerifier(getHostnameVerifier());

        /*设置Https证书*/
        if (getSslParams() != null)
            okBuilder.sslSocketFactory(getSslParams().sSLSocketFactory, getSslParams().trustManager);

        //设置拦截器
        Map<String, Interceptor> allInterceptorMap = mergeParams(
                RxHttp.getGlobalOptions().getInterceptorMap(),
                getInterceptorMap(), getRemoveInterceptors(),
                isRemoveAllGlobalInterceptors());
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
                getNetInterceptorMap(), getRemoveNetInterceptors(),
                isRemoveAllGlobalNetInterceptors());
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
                getHeadersMap(), getRemoveHeaders(),
                isRemoveAllGlobalHeaders());

        //获取表单参数
        Map<String, String> allFormDatasMap = mergeParams(
                RxHttp.getGlobalOptions().getFormDatasMap(),
                getFormDatasMap(), getRemoveFormDatas(),
                isRemoveAllGlobalFormDatas());

        //添加Cookie管理类
        RxHttp.getGlobalOptions().getCookieManager().add(getCookieList());
        okBuilder.cookieJar(RxHttp.getGlobalOptions().getCookieManager());

        //创建Retrofit对象
        Retrofit retrofit = RxHttp.RETROFIT().create(baseUrl, okBuilder.build());
        ApiService apiService = retrofit.create(ApiService.class);

        //执行请求
        return buildResponse(allHeadersMap, allFormDatasMap, getObjectRequestBody(),
                getOkHttp3RequestBody(), getJsonRequestBody(), apiService);
    }

    /**
     * 获取最终请求的BaseUrl
     */
    protected String getFinalBaseUrl()
    {
        return StringUtils.isNotEmpty(getBaseUrl()) ? getBaseUrl() : RxHttp.getGlobalOptions().getBaseUrl();
    }

    /**
     * 获取最终请求的读取超时时间
     */
    protected long getFinalReadTimeOut()
    {
        return getReadTimeOut() != -1 ? getReadTimeOut() : RxHttp.getGlobalOptions().getReadTimeOut();
    }

    /**
     * 获取最终请求的写入超时时间
     */
    protected long getFinalWriteTimeOut()
    {
        return getWriteTimeOut() != -1 ? getWriteTimeOut() : RxHttp.getGlobalOptions().getWriteTimeOut();
    }

    /**
     * 获取最终请求的连接超时时间
     */
    protected long getFinalConnectTimeOut()
    {
        return getConnectTimeOut() != -1 ? getConnectTimeOut() : RxHttp.getGlobalOptions().getConnectTimeOut();
    }

    /*合并全局参数和自定义参数*/
    protected <P> Map<String, P> mergeParams(Map<String, P> globalParams,
                                             Map<String, P> customParams,
                                             Set<String> ignoreParams,
                                             boolean ignoreGlobal)
    {
        Map<String, P> resultMap = new HashMap<>();
        //添加全局参数
        if (!ignoreGlobal && globalParams != null)
            resultMap.putAll(globalParams);
        //添加自定义参数
        if (customParams != null)
            resultMap.putAll(customParams);
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
        cacheBuilder.cacheOpeartor(RxHttp.getGlobalOptions().getCacheOpeartor());
        cacheBuilder.cacheMode(getCacheMode());
        cacheBuilder.cacheTime(getCacheTime());
        cacheBuilder.cacheKey(getCacheKey());

        return cacheBuilder.build();
    }

    /**
     * 子类实现该方法执行网络请求过程
     */
    protected abstract Observable<ResponseBody> buildResponse(Map<String, String> headersMap,
                                                              Map<String, String> formDatasMap,
                                                              Object objectRequestBody,
                                                              RequestBody okHttp3RequestBody,
                                                              String jsonBody,
                                                              ApiService service);
}
