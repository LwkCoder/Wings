package com.lwkandroid.wingsdemo.app;

import android.app.Application;

import com.lwkandroid.wings.app.WingsInitOperator;
import com.lwkandroid.wings.net.RxHttp;
import com.lwkandroid.wingsdemo.bean.WeatherHttpResult;
import com.lwkandroid.wingsdemo.net.ApiURL;
import com.lwkandroid.wingsdemo.net.TestSignInterceptor;

/**
 * Created by LWK
 * TODO 定制化Wings初始化参数
 */

public class AppInitOptions extends WingsInitOperator
{
    public AppInitOptions(Application appContext)
    {
        super(appContext);
    }

    @Override
    protected String getApiBaseUrl()
    {
        return ApiURL.HOST;
    }

    @Override
    protected void initRxHttp()
    {
        super.initRxHttp();
        RxHttp.getGlobalOptions()
                .setApiResultType(WeatherHttpResult.class)
                .setApiResultOkCode(0)
                .addNetInterceptor("TestParams", new TestSignInterceptor());
    }
}
