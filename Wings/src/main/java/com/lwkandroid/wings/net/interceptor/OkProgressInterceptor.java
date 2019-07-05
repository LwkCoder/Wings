package com.lwkandroid.wings.net.interceptor;

import com.lwkandroid.wings.net.manager.OkProgressManger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * OkHttp 上传/下载过程拦截器
 *
 * @author LWK
 */

public final class OkProgressInterceptor implements Interceptor
{
    @Override
    public Response intercept(Chain chain) throws IOException
    {
        return OkProgressManger.get().wrapResponseBody(
                chain.proceed(OkProgressManger.get().wrapRequestBody(chain.request())));
    }
}
