package com.lwkandroid.wingsdemo.app;

import android.app.Application;
import android.os.Build;

import com.lwkandroid.wings.Wings;
import com.lwkandroid.wings.app.WingsInitOptions;
import com.lwkandroid.wings.log.KLog;
import com.lwkandroid.wings.net.bean.IApiDynamicFormData;
import com.lwkandroid.wings.net.bean.IApiDynamicHeader;
import com.lwkandroid.wings.utils.AppUtils;
import com.lwkandroid.wingsdemo.BuildConfig;
import com.lwkandroid.wingsdemo.net.ApiURL;
import com.lwkandroid.wingsdemo.net.TestDynamicHeadersInterceptor;
import com.lwkandroid.wingsdemo.net.TestDynamicParamsInterceptor;

/**
 * Application入口
 *
 * @author LWK
 */
public class DemoApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        ApiURL.HOST = BuildConfig.HOST;
        AppConfig.LIB_CONFIG1 = BuildConfig.LibConfig1;
        AppConfig.LIB_CONFIG2 = BuildConfig.LibConfig2;

        WingsInitOptions options = new WingsInitOptions(this);
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

        if (BuildConfig.DEBUG)
        {
            KLog.i(new StringBuilder()
                    .append("\n********************************************************\n")
                    .append("| AppName=").append(AppUtils.getAppName()).append("\n")
                    .append("| VersionName=").append(AppUtils.getAppVersionName()).append("\n")
                    .append("| VersionCode=").append(AppUtils.getAppVersionCode()).append("\n")
                    .append("| PackageName=").append(AppUtils.getPackageName()).append("\n")
                    .append("| SHA1=").append(AppUtils.getAppSignatureSHA1()).append("\n")
                    .append("| ApiURL.HOST=").append(ApiURL.HOST).append("\n")
                    .append("| DeviceManufacturer=").append(Build.MANUFACTURER).append("\n")
                    .append("| DeviceModel=").append(Build.MODEL).append("\n")
                    .append("| AndroidVersion=").append(Build.VERSION.RELEASE).append("\n")
                    .append("| AndroidSdk=").append(Build.VERSION.SDK_INT).append("\n")
                    .append("********************************************************"));
        }
    }


}
