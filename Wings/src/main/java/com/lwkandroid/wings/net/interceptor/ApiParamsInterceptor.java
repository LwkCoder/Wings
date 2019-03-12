package com.lwkandroid.wings.net.interceptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by LWK
 * TODO OkHttp参数拦截器，用于统一对参数进行处理
 */

public abstract class ApiParamsInterceptor implements Interceptor
{
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private HttpUrl mHttpUrl;
    private final String GET = "GET";
    private final String POST = "POST";

    @Override
    public Response intercept(Chain chain) throws IOException
    {
        Request request = chain.request();
        if (GET.equals(request.method()))
        {
            this.mHttpUrl = HttpUrl.parse(parseUrl(request.url().url().toString()));
            request = addGetParamsSign(request);
        } else if (POST.equals(request.method()))
        {
            this.mHttpUrl = request.url();
            request = addPostParamsSign(request);
        }
        return chain.proceed(request);
    }

    //解析get请求的原始url
    private String parseUrl(String url)
    {
        if (!"".equals(url) && url.contains("?"))
        {
            url = url.substring(0, url.indexOf('?'));
        }
        return url;
    }

    //get 对参数进行动态修改
    private Request addGetParamsSign(Request request) throws UnsupportedEncodingException
    {
        HttpUrl httpUrl = request.url();
        HttpUrl.Builder newBuilder = httpUrl.newBuilder();

        //获取原有的参数
        Set<String> nameSet = httpUrl.queryParameterNames();
        ArrayList<String> nameList = new ArrayList<>();
        nameList.addAll(nameSet);
        TreeMap<String, String> oldparams = new TreeMap<>();
        for (int i = 0; i < nameList.size(); i++)
        {
            String value = httpUrl.queryParameterValues(nameList.get(i)) != null
                    && httpUrl.queryParameterValues(nameList.get(i)).size() > 0 ?
                    httpUrl.queryParameterValues(nameList.get(i)).get(0) : "";
            oldparams.put(nameList.get(i), value);
        }
        String nameKeys = Collections.singletonList(nameList).toString();
        //组装新的参数
        TreeMap<String, String> newParams = dynamic(oldparams);
        for (Map.Entry<String, String> entry : newParams.entrySet())
        {
            String urlValue = URLEncoder.encode(entry.getValue(), UTF8.name());
            if (!nameKeys.contains(entry.getKey()))
            {
                newBuilder.addQueryParameter(entry.getKey(), urlValue);
            }
        }

        httpUrl = newBuilder.build();
        request = request.newBuilder().url(httpUrl).build();
        return request;
    }

    //post 对参数进行动态修改
    private Request addPostParamsSign(Request request) throws UnsupportedEncodingException
    {
        if (request.body() instanceof FormBody)
        {
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            FormBody formBody = (FormBody) request.body();

            //原有的参数
            TreeMap<String, String> oldparams = new TreeMap<>();
            for (int i = 0; i < formBody.size(); i++)
            {
                oldparams.put(formBody.encodedName(i), formBody.encodedValue(i));
            }

            //拼装新的参数
            TreeMap<String, String> newParams = dynamic(oldparams);
            for (Map.Entry<String, String> entry : newParams.entrySet())
            {
                String value = URLDecoder.decode(entry.getValue(), UTF8.name());
                bodyBuilder.addEncoded(entry.getKey(), value);
            }
            formBody = bodyBuilder.build();
            request = request.newBuilder().post(formBody).build();
        } else if (request.body() instanceof MultipartBody)
        {
            MultipartBody multipartBody = (MultipartBody) request.body();
            MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            List<MultipartBody.Part> oldparts = multipartBody.parts();

            //拼装新的参数
            List<MultipartBody.Part> newparts = new ArrayList<>();
            newparts.addAll(oldparts);
            TreeMap<String, String> oldparams = new TreeMap<>();
            TreeMap<String, String> newParams = dynamic(oldparams);
            for (Map.Entry<String, String> stringStringEntry : newParams.entrySet())
            {
                MultipartBody.Part part = MultipartBody.Part.createFormData(stringStringEntry.getKey(), stringStringEntry.getValue());
                newparts.add(part);
            }
            for (MultipartBody.Part part : newparts)
            {
                bodyBuilder.addPart(part);
            }
            multipartBody = bodyBuilder.build();
            request = request.newBuilder().post(multipartBody).build();
        }
        return request;
    }

    /**
     * 子类实现该方法对参数进行动态修改
     */
    public abstract TreeMap<String, String> dynamic(TreeMap<String, String> oldParams);
}
