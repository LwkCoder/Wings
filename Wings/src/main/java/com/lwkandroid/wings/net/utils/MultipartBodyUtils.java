package com.lwkandroid.wings.net.utils;

import okhttp3.Headers;
import okhttp3.RequestBody;

/**
 * Created by LWK
 * TODO 网络请求MultipartBody参数辅助工具
 */

public class MultipartBodyUtils
{
    private MultipartBodyUtils()
    {
    }

    /**
     * 创建单独的Multipart.Body.Part对象
     */
    public static okhttp3.MultipartBody.Part createPart(RequestBody requestBody)
    {
        return okhttp3.MultipartBody.Part.create(requestBody);
    }

    /**
     * 创建单独的Multipart.Body.Part对象
     */
    public static okhttp3.MultipartBody.Part createPart(Headers headers, RequestBody requestBody)
    {
        return okhttp3.MultipartBody.Part.create(headers, requestBody);
    }

    /**
     * 创建单独的Multipart.Body.Part对象
     */
    public static okhttp3.MultipartBody.Part createFormDataPart(String key, String value)
    {
        return okhttp3.MultipartBody.Part.createFormData(key, value);
    }

    /**
     * 创建单独的Multipart.Body.Part对象
     */
    public static okhttp3.MultipartBody.Part createFormDataPart(String key, String fileName, RequestBody requestBody)
    {
        return okhttp3.MultipartBody.Part.createFormData(key, fileName, requestBody);
    }
}
