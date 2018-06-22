package com.lwkandroid.wings.net.utils;

import com.lwkandroid.wings.net.RxHttp;
import com.lwkandroid.wings.net.bean.ApiGlobalOptions;
import com.lwkandroid.wings.net.interceptor.ApiHeaderInterceptor;
import com.lwkandroid.wings.net.interceptor.RetrofitFormDataInterceptor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LWK
 * TODO Retrofit辅助工具
 */

public class RetrofitUtils
{
    /**
     * 根据RxHttp全局配置创建Retrofit对象
     * 【全局参数和Header通过添加拦截器实现】
     *
     * @return Retrofit对象
     */
    public static Retrofit createWithGlobalOptions()
    {
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        ApiGlobalOptions globalOptions = RxHttp.getGlobalOptions();
        /*设置超时时间*/
        okBuilder.readTimeout(globalOptions.getReadTimeOut(), TimeUnit.MILLISECONDS);
        okBuilder.writeTimeout(globalOptions.getWriteTimeOut(), TimeUnit.MILLISECONDS);
        okBuilder.connectTimeout(globalOptions.getConnectTimeOut(), TimeUnit.MILLISECONDS);

         /*设置HostnameVerifier*/
        if (globalOptions.getHostnameVerifier() != null)
            okBuilder.hostnameVerifier(globalOptions.getHostnameVerifier());

        /*设置Https证书*/
        if (globalOptions.getSslParams() != null)
            okBuilder.sslSocketFactory(globalOptions.getSslParams().sSLSocketFactory,
                    globalOptions.getSslParams().trustManager);

        /*添加全局参数和Header*/
        Map<String, String> globalFormDatasMap = globalOptions.getFormDatasMap();
        Map<String, String> globalHeadersMap = globalOptions.getHeadersMap();
        if (globalFormDatasMap != null && globalFormDatasMap.size() > 0)
            okBuilder.addInterceptor(new RetrofitFormDataInterceptor(globalFormDatasMap));
        if (globalHeadersMap != null && globalHeadersMap.size() > 0)
            okBuilder.addInterceptor(new ApiHeaderInterceptor(globalHeadersMap));

        /*添加全局拦截器*/
        Map<String, Interceptor> interceptorMap = globalOptions.getInterceptorMap();
        Map<String, Interceptor> netInterceptorMap = globalOptions.getNetInterceptorMap();
        if (interceptorMap != null && interceptorMap.size() > 0)
        {
            for (Interceptor interceptor : interceptorMap.values())
            {
                okBuilder.addInterceptor(interceptor);
            }
        }
        if (netInterceptorMap != null && netInterceptorMap.size() > 0)
        {
            for (Interceptor interceptor : netInterceptorMap.values())
            {
                okBuilder.addNetworkInterceptor(interceptor);
            }
        }

        //添加Cookie管理类
        okBuilder.cookieJar(RxHttp.getGlobalOptions().getCookieManager());

        return create(globalOptions.getBaseUrl(), okBuilder.build());
    }

    /**
     * 创建Retrofit对象
     *
     * @param baseUrl  请求域名
     * @param okClient OkHttpClient对象
     * @return Retrofit对象
     */
    public static Retrofit create(String baseUrl, OkHttpClient okClient)
    {
        Retrofit.Builder retroBuilder = new Retrofit.Builder();
        retroBuilder
                .baseUrl(baseUrl)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        return retroBuilder.build();
    }

}
