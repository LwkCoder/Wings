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
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        ApiGlobalOptions globalOptions = RxHttp.getGlobalOptions();
        /*设置超时时间*/
        builder.readTimeout(globalOptions.getReadTimeOut(), TimeUnit.MILLISECONDS);
        builder.writeTimeout(globalOptions.getWriteTimeOut(), TimeUnit.MILLISECONDS);
        builder.connectTimeout(globalOptions.getConnectTimeOut(), TimeUnit.MILLISECONDS);

        /*添加全局参数和Header*/
        Map<String, String> globalFormDatasMap = globalOptions.getFormDatasMap();
        Map<String, String> globalHeadersMap = globalOptions.getHeadersMap();
        if (globalFormDatasMap != null && globalFormDatasMap.size() > 0)
            builder.addInterceptor(new RetrofitFormDataInterceptor(globalFormDatasMap));
        if (globalHeadersMap != null && globalHeadersMap.size() > 0)
            builder.addInterceptor(new ApiHeaderInterceptor(globalHeadersMap));

        /*添加全局拦截器*/
        Map<String, Interceptor> interceptorMap = globalOptions.getInterceptorMap();
        Map<String, Interceptor> netInterceptorMap = globalOptions.getNetInterceptorMap();
        if (interceptorMap != null && interceptorMap.size() > 0)
        {
            for (Interceptor interceptor : interceptorMap.values())
            {
                builder.addInterceptor(interceptor);
            }
        }
        if (netInterceptorMap != null && netInterceptorMap.size() > 0)
        {
            for (Interceptor interceptor : netInterceptorMap.values())
            {
                builder.addNetworkInterceptor(interceptor);
            }
        }

        return create(globalOptions.getBaseUrl(), builder.build());
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
