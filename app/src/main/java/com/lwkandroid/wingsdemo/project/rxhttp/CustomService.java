package com.lwkandroid.wingsdemo.project.rxhttp;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by LWK
 * TODO 自定义Retrofit的Service接口
 */

public interface CustomService
{
    @GET
    Observable<ResponseBody> customGet(@Url String url, @QueryMap Map<String, String> maps);
}
