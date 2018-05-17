package com.lwkandroid.wings.net;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by LWK
 * TODO Retrofit请求模版
 */

public interface ApiService
{
    @GET()
    Observable<ResponseBody> get(@Url String url, @HeaderMap Map<String, String> headerMap, @QueryMap Map<String, String> maps);

    @POST()
    @FormUrlEncoded
    Observable<ResponseBody> post(@Url String url, @HeaderMap Map<String, String> headerMap, @FieldMap Map<String, String> maps);

    @POST()
    Observable<ResponseBody> postObjectBody(@Url String url, @HeaderMap Map<String, String> headerMap, @Body Object object);

    @POST()
    Observable<ResponseBody> postOkHttp3Body(@Url String url, @HeaderMap Map<String, String> headerMap, @Body RequestBody body);

    @POST()
    Observable<ResponseBody> postJsonBody(@Url String url, @HeaderMap Map<String, String> headerMap, @Body RequestBody jsonBody);

    @DELETE()
    Observable<ResponseBody> delete(@Url String url, @HeaderMap Map<String, String> headerMap, @QueryMap Map<String, String> maps);

    @DELETE()
    Observable<ResponseBody> deleteObjectBody(@Url String url, @HeaderMap Map<String, String> headerMap, @Body Object object);

    @DELETE()
    Observable<ResponseBody> deleteOkHttp3Body(@Url String url, @HeaderMap Map<String, String> headerMap, @Body RequestBody body);

    @DELETE()
    Observable<ResponseBody> deleteJsonBody(@Url String url, @HeaderMap Map<String, String> headerMap, @Body RequestBody jsonBody);

    @PUT()
    Observable<ResponseBody> put(@Url String url, @HeaderMap Map<String, String> headerMap, @QueryMap Map<String, String> maps);

    @PUT()
    Observable<ResponseBody> putObjectBody(@Url String url, @HeaderMap Map<String, String> headerMap, @Body Object object);

    @PUT()
    Observable<ResponseBody> putOkHttp3Body(@Url String url, @HeaderMap Map<String, String> headerMap, @Body RequestBody body);

    @PUT()
    Observable<ResponseBody> putJsonBody(@Url String url, @HeaderMap Map<String, String> headerMap, @Body RequestBody jsonBody);

    @PATCH()
    Observable<ResponseBody> patch(@Url String url, @HeaderMap Map<String, String> headerMap, @QueryMap Map<String, String> maps);

    @Multipart
    @POST()
    Observable<ResponseBody> uploadFiles(@Url String url, @HeaderMap Map<String, String> headerMap, @Part() List<MultipartBody.Part> parts);

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl, @HeaderMap Map<String, String> headerMap, @QueryMap Map<String, String> maps);
}
