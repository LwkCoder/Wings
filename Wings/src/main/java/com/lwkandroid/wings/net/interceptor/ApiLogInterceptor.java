package com.lwkandroid.wings.net.interceptor;

import com.lwkandroid.wings.utils.json.JsonUtils;
import com.socks.library.KLog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;

/**
 * Created by LWK
 * TODO OkHttp日志请求拦截器
 */

public class ApiLogInterceptor implements Interceptor
{
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException
    {
        Request request = chain.request();
        logRequest(request, chain.connection());
        Response response;
        try
        {
            response = chain.proceed(request);
        } catch (Exception e)
        {
            KLog.d("--->HttpResponse : Fail to proceed response");
            KLog.d("----------------------------↑ OkHttp ↑----------------------------");
            throw e;
        }

        return logResponse(response);
    }

    private void logRequest(Request r, Connection connection)
    {
        KLog.d("----------------------------↓ OkHttp ↓----------------------------");
        Request request = r.newBuilder().build();
        String method = request.method();
        String url = request.url().toString();
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        RequestBody body = request.body();
        boolean hasBody = body != null;
        MediaType mediaType = hasBody ? body.contentType() : null;

        try
        {
            KLog.d("--->HttpRequest");
            KLog.d("| Url=" + url + " Method：" + method + " " + " Protocol：" + protocol);
            KLog.d("| Content-Type：" + (hasBody ? mediaType : "null")
                    + " Content-Length：" + (hasBody ? body.contentLength() : "null"));
            StringBuffer headersLog = new StringBuffer("| Headers：");
            Headers headers = request.headers();
            for (int i = 0, size = headers.size(); i < size; i++)
            {
                headersLog.append("[name=" + headers.name(i) + " value=" + headers.value(i) + "] ");
            }
            KLog.d(headersLog.toString());
            if (hasBody)
            {
                if (isPlaintext(mediaType))
                {
                    String requestBody = bodyToString(mediaType, body);
                    if (JsonUtils.get().isJsonData(requestBody))
                    {
                        KLog.d("| RequestBody：\n");
                        KLog.json(requestBody);
                    } else
                    {
                        KLog.d("| RequestBody：" + requestBody);
                    }
                } else
                    KLog.d("| RequestBody：maybe binary body, Ignored logging !");
            } else
            {
                KLog.d("| RequestBody：null");
            }

        } catch (Exception e)
        {
            KLog.d("Exception occurred during logging for request:" + e.toString());
        }
    }

    private static boolean isPlaintext(MediaType mediaType)
    {
        if (mediaType == null)
            return false;
        if (mediaType.type() != null && mediaType.type().equals("text"))
            return true;

        String subtype = mediaType.subtype();
        if (subtype != null)
        {
            subtype = subtype.toLowerCase();
            if (subtype.contains("x-www-form-urlencoded") || subtype.contains("json")
                    || subtype.contains("xml") || subtype.contains("html"))
                return true;
        }
        return false;
    }

    private String bodyToString(MediaType mediaType, RequestBody body) throws IOException
    {
        Buffer buffer = new Buffer();
        body.writeTo(buffer);
        Charset charset = mediaType != null ? mediaType.charset(UTF8) : UTF8;
        return buffer.readString(charset);
    }

    private Response logResponse(Response response)
    {
        Response clone = response.newBuilder().build();
        ResponseBody responseBody = clone.body();
        boolean hasBody = responseBody != null;
        MediaType mediaType = hasBody ? responseBody.contentType() : null;

        try
        {
            KLog.d("--->HttpResponse");
            StringBuffer headersLog = new StringBuffer("| Headers：");
            Headers headers = clone.headers();
            for (int i = 0, size = headers.size(); i < size; i++)
            {
                headersLog.append("[name=" + headers.name(i) + " value=" + headers.value(i) + "] ");
            }
            KLog.d(headersLog.toString());
            if (hasBody && HttpHeaders.hasBody(clone))
            {
                if (isPlaintext(mediaType))
                {
                    byte[] bytes = toByteArray(responseBody.byteStream());
                    Charset charset = mediaType != null ? mediaType.charset(UTF8) : UTF8;
                    String bodyString = new String(bytes, charset);
                    if (JsonUtils.get().isJsonData(bodyString))
                    {
                        KLog.d("| ResponseBody：\n");
                        KLog.json(bodyString);
                    } else
                    {
                        KLog.d("| ResponseBody：" + bodyString);
                    }
                    KLog.d("----------------------------↑ OkHttp ↑----------------------------");
                    responseBody = ResponseBody.create(responseBody.contentType(), bytes);
                    return response.newBuilder().body(responseBody).build();
                } else
                {
                    KLog.d("| ResponseBody：maybe binary body, Ignored logging !");
                }
            } else
            {
                KLog.d("| ResponseBody：null");
                KLog.d("----------------------------↑ OkHttp ↑----------------------------");
                return response;
            }

        } catch (Exception e)
        {
            KLog.d("Exception occurred during logging for response:" + e.toString());
        }
        KLog.d("----------------------------↑ OkHttp ↑----------------------------");
        return response;
    }

    private static byte[] toByteArray(InputStream input) throws IOException
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int len;
        byte[] buffer = new byte[2048];
        while ((len = input.read(buffer)) != -1)
            output.write(buffer, 0, len);
        output.close();
        return output.toByteArray();
    }

}
