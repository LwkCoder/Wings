package com.lwkandroid.wingsdemo.project.rxhttp;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * 自定义Retrofit的Service接口
 *
 * @author LWK
 */
public interface CustomService
{
    @GET
    Observable<ResponseBody> customGet(@Url String url, @QueryMap Map<String, Object> maps);

    @POST()
    @FormUrlEncoded
    Observable<ResponseBody> customPost(@Url String url, @FieldMap Map<String, Object> maps);
}
