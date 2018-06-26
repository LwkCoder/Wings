package com.lwkandroid.wings.net.interceptor;

import com.lwkandroid.wings.utils.json.JsonUtils;
import com.socks.library.KLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private static final String TAG = "ApiLogInterceptor";
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final String START = "————————————↓ OkHttp ↓————————————————————————————————————————";
    private static final String END = "————————————↑ OkHttp ↑————————————————————————————————————————";
    private static final String SEPARATOR = "| ";
    private static final String NEXT_LINE = "\n";

    @Override
    public Response intercept(Chain chain) throws IOException
    {
        StringBuffer buffer = new StringBuffer();
        Request request = chain.request();
        logRequest(request, chain.connection(), buffer);
        Response response;
        try
        {
            response = chain.proceed(request);
        } catch (Exception e)
        {
            buffer.append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append("--->HttpResponse : Fail to proceed response:")
                    .append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append(e.toString())
                    .append(NEXT_LINE)
                    .append(END);
            showLog(buffer);
            throw e;
        }

        return logResponse(response, buffer);
    }

    private void logRequest(Request r, Connection connection, StringBuffer buffer)
    {
        buffer.append(NEXT_LINE)
                .append(START);
        Request request = r.newBuilder().build();
        String method = request.method();
        String url = request.url().toString();
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        RequestBody body = request.body();
        boolean hasBody = body != null;
        MediaType mediaType = hasBody ? body.contentType() : null;

        try
        {
            buffer.append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append("---------->HttpRequest")
                    .append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append("Url=")
                    .append(url)
                    .append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append("Method=")
                    .append(method)
                    .append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append("Protocal=")
                    .append(protocol)
                    .append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append("Content-type=")
                    .append(hasBody ? mediaType : "null")
                    .append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append("Content-Length=")
                    .append(hasBody ? body.contentLength() : "null");

            Headers headers = request.headers();
            if (headers != null && headers.size() > 0)
                buffer.append(NEXT_LINE)
                        .append(SEPARATOR)
                        .append("Headers=");
            else
                buffer.append(NEXT_LINE)
                        .append(SEPARATOR)
                        .append("Headers=null");
            for (int i = 0, size = headers.size(); i < size; i++)
            {
                buffer.append("[" + headers.name(i) + "=" + headers.value(i) + "] ");
            }

            if (hasBody)
            {
                String bodyString = requestBodyToString(mediaType, body);
                if (isPlaintext(mediaType))
                {
                    if (JsonUtils.get().isJsonData(bodyString))
                    {
                        buffer.append(NEXT_LINE)
                                .append(SEPARATOR)
                                .append("RequestBody:");
                        appendFormatJson(bodyString, buffer);
                    } else
                    {
                        buffer.append(NEXT_LINE)
                                .append(SEPARATOR)
                                .append("RequestBody:")
                                .append(NEXT_LINE)
                                .append(SEPARATOR)
                                .append(bodyString);
                    }
                } else
                {
                    if (JsonUtils.get().isJsonData(bodyString))
                    {
                        buffer.append(NEXT_LINE)
                                .append(SEPARATOR)
                                .append("RequestBody: Binary json data:");
                        appendFormatJson(bodyString, buffer);
                    } else
                    {
                        buffer.append(NEXT_LINE)
                                .append(SEPARATOR)
                                .append("RequestBody: Maybe binary body. Ignored logging !");
                    }
                    buffer.append(NEXT_LINE)
                            .append(END);
                }
            } else
            {
                buffer.append(NEXT_LINE)
                        .append(SEPARATOR)
                        .append("RequestBody:null");
            }
        } catch (Exception e)
        {
            buffer.append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append("Exception occurred during logging for request:")
                    .append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append(e.toString());
        }
    }

    private Response logResponse(Response response, StringBuffer buffer)
    {
        Response clone = response.newBuilder().build();
        ResponseBody body = clone.body();
        boolean hasBody = body != null;
        MediaType mediaType = hasBody ? body.contentType() : null;

        try
        {
            buffer.append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append("---------->HttpResponse")
                    .append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append("Content-Type=")
                    .append((hasBody ? mediaType : "null"))
                    .append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append("Content-Length:")
                    .append((hasBody ? body.contentLength() : "null"));

            Headers headers = clone.headers();
            if (headers != null && headers.size() > 0)
                buffer.append(NEXT_LINE)
                        .append(SEPARATOR)
                        .append("Headers=");
            else
                buffer.append(NEXT_LINE)
                        .append(SEPARATOR)
                        .append("Headers=null");
            for (int i = 0, size = headers.size(); i < size; i++)
            {
                buffer.append("[" + headers.name(i) + "=" + headers.value(i) + "] ");
            }

            if (hasBody && HttpHeaders.hasBody(clone))
            {
                if (isPlaintext(mediaType))
                {
                    byte[] bytes = toByteArray(body.byteStream());
                    Charset charset = mediaType != null ? mediaType.charset(UTF8) : UTF8;
                    String bodyString = new String(bytes, charset);
                    if (JsonUtils.get().isJsonData(bodyString))
                    {
                        buffer.append(NEXT_LINE)
                                .append(SEPARATOR)
                                .append("ResponseBody:");
                        appendFormatJson(bodyString, buffer);
                    } else
                    {
                        buffer.append(NEXT_LINE)
                                .append(SEPARATOR)
                                .append("ResponseBody:")
                                .append(NEXT_LINE)
                                .append(SEPARATOR)
                                .append(bodyString);
                    }
                    buffer.append(NEXT_LINE)
                            .append(END);
                    showLog(buffer);
                    body = ResponseBody.create(body.contentType(), bytes);
                    return response.newBuilder().body(body).build();
                } else
                {
                    String binaryString = toByteString(body.byteStream());
                    if (JsonUtils.get().isJsonData(binaryString))
                    {
                        buffer.append(NEXT_LINE)
                                .append(SEPARATOR)
                                .append("ResponseBody: Binary json data:");
                        appendFormatJson(binaryString, buffer);
                    } else
                    {
                        buffer.append(NEXT_LINE)
                                .append(SEPARATOR)
                                .append("ResponseBody: Maybe binary body. Ignored logging !");
                    }
                    buffer.append(NEXT_LINE)
                            .append(END);
                    showLog(buffer);
                    body = ResponseBody.create(body.contentType(), binaryString);
                    return response.newBuilder().body(body).build();
                }
            } else
            {
                buffer.append(NEXT_LINE)
                        .append(SEPARATOR)
                        .append("ResponseBody:null")
                        .append(NEXT_LINE)
                        .append(END);
                showLog(buffer);
                return response;
            }

        } catch (Exception e)
        {
            buffer.append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append("ResponseBody:Exception occurred during logging for response:")
                    .append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append(e.toString())
                    .append(NEXT_LINE)
                    .append(END);
            showLog(buffer);
        }
        return response;
    }

    //判断是否为文本类型
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

    //请求体转为String
    private String requestBodyToString(MediaType mediaType, RequestBody body) throws IOException
    {
        Buffer buffer = new Buffer();
        body.writeTo(buffer);
        Charset charset = mediaType != null ? mediaType.charset(UTF8) : UTF8;
        return buffer.readString(charset);
    }

    //将InputStream转为字节数组
    private byte[] toByteArray(InputStream input)
    {
        ByteArrayOutputStream output = null;
        try
        {
            output = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[2048];
            while ((len = input.read(buffer)) != -1)
                output.write(buffer, 0, len);
            output.flush();
            return output.toByteArray();
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        } finally
        {
            try
            {
                if (output != null)
                    output.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    //将InputStream转为String，编码为UTF-8
    private String toByteString(InputStream input) throws IOException
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int len;
        byte[] buffer = new byte[2048];
        while ((len = input.read(buffer)) != -1)
            output.write(buffer, 0, len);
        output.flush();
        output.close();
        return output.toString(String.valueOf(UTF8));  // 依据需求可以选择要要的字符编码格式
    }

    //拼接格式化的Json数据
    private void appendFormatJson(String json, StringBuffer buffer)
    {
        String message;
        try
        {
            if (json.startsWith("{"))
            {
                JSONObject jsonObject = new JSONObject(json);
                message = jsonObject.toString(KLog.JSON_INDENT);
            } else if (json.startsWith("["))
            {
                JSONArray jsonArray = new JSONArray(json);
                message = jsonArray.toString(KLog.JSON_INDENT);
            } else
            {
                message = json;
            }
        } catch (JSONException e)
        {
            message = json;
        }

        String[] lines = message.split(KLog.LINE_SEPARATOR);
        for (String line : lines)
        {
            buffer.append(NEXT_LINE)
                    .append(SEPARATOR)
                    .append(line);
        }
    }

    private void showLog(StringBuffer buffer)
    {
        KLog.d(TAG, buffer.toString());
    }
}
