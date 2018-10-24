package com.lwkandroid.wings.net.interceptor;


import com.lwkandroid.wings.log.KLog;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by LWK
 * TODO 网络请求Header拦截器
 */
public class ApiHeaderInterceptor implements Interceptor
{
    private static final String TAG = "ApiHeaderInterceptor";
    private Map<String, String> mHeaderMap;

    public ApiHeaderInterceptor(Map<String, String> headerMap)
    {
        this.mHeaderMap = headerMap;
    }

    @Override
    public Response intercept(Chain chain) throws IOException
    {
        Request.Builder builder = chain.request().newBuilder();
        if (mHeaderMap == null || mHeaderMap.isEmpty())
            return chain.proceed(builder.build());

        try
        {
            for (Map.Entry<String, String> entry : mHeaderMap.entrySet())
            {
                builder.addHeader(entry.getKey(), entry.getValue()).build();
            }
        } catch (Exception e)
        {
            KLog.e(TAG, "Exception occured when add Header:" + e.toString());
        }
        return chain.proceed(builder.build());
    }
}