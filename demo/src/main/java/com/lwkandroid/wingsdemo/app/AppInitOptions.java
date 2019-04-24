package com.lwkandroid.wingsdemo.app;

import android.app.Application;

import com.lwkandroid.wings.app.WingsInitOperator;
import com.lwkandroid.wings.net.RxHttp;
import com.lwkandroid.wings.net.bean.IApiDynamicFormData;
import com.lwkandroid.wingsdemo.bean.WeatherHttpResult;
import com.lwkandroid.wingsdemo.net.ApiURL;

/**
 * Created by LWK
 * 定制化Wings初始化参数
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
                .addDynamicFormData("test_dynamic_data", new IApiDynamicFormData()
                {
                    @Override
                    public Object getFormData()
                    {
                        return String.valueOf(System.currentTimeMillis());
                    }
                });
    }
}
