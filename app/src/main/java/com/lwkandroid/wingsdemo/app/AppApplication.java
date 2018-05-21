package com.lwkandroid.wingsdemo.app;

import android.app.Application;

import com.lwkandroid.wings.app.BaseApplication;
import com.lwkandroid.wings.net.RxHttp;
import com.lwkandroid.wingsdemo.BuildConfig;
import com.lwkandroid.wingsdemo.bean.WeatherHttpResult;
import com.lwkandroid.wingsdemo.net.ApiURL;
import com.lwkandroid.wingsdemo.net.TestSignInterceptor;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;

/**
 * Created by LWK
 * TODO 项目Application入口
 */

public class AppApplication extends BaseApplication
{
    private static AppApplication INSTANCE;

    @Override
    public void onCreate()
    {
        super.onCreate();
        INSTANCE = this;
    }

    @Override
    protected void initBuildConfig()
    {
        AppConfig.WX_APP_ID = BuildConfig.WX_APP_ID;
        AppConfig.WX_APP_SECRET = BuildConfig.WX_APP_SECRET;
        ApiURL.HOST = BuildConfig.HOST;

        KLog.i("WX_APP_ID=" + AppConfig.WX_APP_ID + " WX_APP_SECRET=" + AppConfig.WX_APP_SECRET + " API_HOST=" + ApiURL.HOST);
    }

    @Override
    protected Map<String, Interceptor> getCustomInterceptors()
    {
        Map<String, Interceptor> map = new HashMap<>();
        map.put("sign", new TestSignInterceptor());
        return map;
    }

    @Override
    protected void initRxHttp()
    {
        super.initRxHttp();
        RxHttp.getGlobalOptions()
                .setApiResultType(WeatherHttpResult.class)
                .setApiResultOkCode(0);
    }


    @Override
    protected String getBaseUrl()
    {
        return ApiURL.HOST;
    }

    @Override
    protected void initExtraLibraries()
    {

    }

    public static Application getInstance()
    {
        return INSTANCE;
    }
}
