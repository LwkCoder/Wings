package com.lwkandroid.wings.net;

import android.content.Context;

import com.lwkandroid.wings.net.bean.ApiGlobalOptions;
import com.lwkandroid.wings.net.bean.ApiResult;
import com.lwkandroid.wings.net.cookie.CookieManager;
import com.lwkandroid.wings.net.requst.ApiDeleteRequest;
import com.lwkandroid.wings.net.requst.ApiDownloadRequest;
import com.lwkandroid.wings.net.requst.ApiGetRequest;
import com.lwkandroid.wings.net.requst.ApiPatchRequest;
import com.lwkandroid.wings.net.requst.ApiPostRequest;
import com.lwkandroid.wings.net.requst.ApiPutRequest;
import com.lwkandroid.wings.net.requst.ApiUploadRequest;
import com.lwkandroid.wings.net.utils.RetrofitUtils;
import com.lwkandroid.wings.utils.Utils;
import com.socks.library.KLog;

import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * Created by LWK
 * TODO 向外提供功能的入口
 */

public class RxHttp
{
    static
    {
        mGlobalOptions = new ApiGlobalOptions();
        mRetrofitUtils = new RetrofitUtils();
    }

    private RxHttp()
    {
    }

    private static Context mContext;
    private static final ApiGlobalOptions mGlobalOptions;
    private static final RetrofitUtils mRetrofitUtils;

    /**
     * 初始化公共配置
     *
     * @param context Context
     * @param baseUrl 网络请求域名，用来配置Retrofit，结尾必须是“/”
     * @return 公共配置对象
     */
    public static ApiGlobalOptions init(Context context, String baseUrl)
    {
        mContext = context.getApplicationContext();
        mGlobalOptions.setBaseUrl(baseUrl);
        mGlobalOptions.setApiResultType(ApiResult.class);
        mGlobalOptions.setCookieManager(new CookieManager());
        initRxJava();
        return mGlobalOptions;
    }

    //初始化RxJava配置：捕获异常
    private static void initRxJava()
    {
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>()
        {
            @Override
            public void accept(Throwable throwable) throws Exception
            {
                KLog.e("RxJavaPlugins.ErrorHandler :" + throwable.toString());
            }
        });
    }

    public static Context getContext()
    {
        if (mContext == null)
            return Utils.getContext();
        return mContext;
    }

    /**
     * 获取公共配置
     */
    public static ApiGlobalOptions getGlobalOptions()
    {
        return mGlobalOptions;
    }

    /**
     * 发起Get请求
     */
    public static ApiGetRequest GET(String url)
    {
        return new ApiGetRequest(url);
    }

    /**
     * 发起Post请求
     */
    public static ApiPostRequest POST(String url)
    {
        return new ApiPostRequest(url);
    }

    /**
     * 发起Put请求
     */
    public static ApiPutRequest PUT(String url)
    {
        return new ApiPutRequest(url);
    }

    /**
     * 发起Delete请求
     */
    public static ApiDeleteRequest DELETE(String url)
    {
        return new ApiDeleteRequest(url);
    }

    /**
     * 发起Patch请求
     */
    public static ApiPatchRequest PATCH(String url)
    {
        return new ApiPatchRequest(url);
    }

    /**
     * 发起Download请求
     */
    public static ApiDownloadRequest DOWNLOAD(String url)
    {
        return new ApiDownloadRequest(url);
    }

    /**
     * 发起Upload请求
     */
    public static ApiUploadRequest UPLOAD(String url)
    {
        return new ApiUploadRequest(url);
    }

    /**
     * 获取RetrofitUtils对象，便于自定义接口
     */
    public static RetrofitUtils RETROFIT()
    {
        return mRetrofitUtils;
    }
}
