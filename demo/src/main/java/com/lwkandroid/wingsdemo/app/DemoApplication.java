package com.lwkandroid.wingsdemo.app;

import android.app.Application;
import android.content.Context;

import com.lwkandroid.wings.Wings;
import com.lwkandroid.wings.app.WingsInitOptions;
import com.lwkandroid.wings.net.bean.IApiDynamicFormData;
import com.lwkandroid.wings.net.bean.IApiDynamicHeader;
import com.lwkandroid.wingsdemo.BuildConfig;
import com.lwkandroid.wingsdemo.net.ApiURL;
import com.lwkandroid.wingsdemo.net.TestDynamicHeadersInterceptor;
import com.lwkandroid.wingsdemo.net.TestDynamicParamsInterceptor;

import androidx.multidex.MultiDex;

/**
 * Application入口
 *
 * @author LWK
 */
public class DemoApplication extends Application
{
    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        // 将MultiDex注入到项目中
        MultiDex.install(this);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        AppConfig.WX_APP_ID = BuildConfig.WX_APP_ID;
        AppConfig.WX_APP_SECRET = BuildConfig.WX_APP_SECRET;
        ApiURL.HOST = BuildConfig.HOST;

        WingsInitOptions options = new WingsInitOptions();
        options.setApplicationContext(this);
        options.setApiBaseUrl(ApiURL.HOST);
        options.getRxHttpGlobalOptions().addInterceptor("ParamsInterceptor", new TestDynamicParamsInterceptor());
        options.getRxHttpGlobalOptions().addInterceptor("HeadersInterceptor", new TestDynamicHeadersInterceptor());
        options.getRxHttpGlobalOptions().addDynamicFormData("CallBackDynamicParamKey", new IApiDynamicFormData()
        {
            @Override
            public Object getFormData()
            {
                return "CallBackDynamicParamValue";
            }
        });
        options.getRxHttpGlobalOptions().addDynamicHeader("CallBackDynamicHeaderTag", new IApiDynamicHeader()
        {
            @Override
            public String getHeader()
            {
                return "CallBackDynamicHeaderValue";
            }
        });
        Wings.init(options);
    }


}
