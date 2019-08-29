package com.sources.javacode.app;

import android.app.Application;
import android.os.Build;

import com.lwkandroid.wings.Wings;
import com.lwkandroid.wings.app.WingsInitOptions;
import com.lwkandroid.wings.log.KLog;
import com.lwkandroid.wings.utils.AppUtils;
import com.sources.javacode.BuildConfig;
import com.sources.javacode.net.ApiURL;

/**
 * Application入口
 *
 * @author LWK
 */

public class AppApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        ApiURL.HOST = BuildConfig.HOST;
        AppConfig.CHANNEL_NAME = BuildConfig.APP_CHANNEL;
        AppConfig.LIB_CONFIG1 = BuildConfig.LibConfig1;
        AppConfig.LIB_CONFIG2 = BuildConfig.LibConfig2;

        WingsInitOptions options = new WingsInitOptions(this);
        options.setApplicationContext(this);
        options.setApiBaseUrl(ApiURL.HOST);
        Wings.init(options);

        if (BuildConfig.DEBUG)
        {
            KLog.i(new StringBuilder()
                    .append("\n********************************************************\n")
                    .append("| AppName=").append(AppUtils.getAppName()).append("\n")
                    .append("| VersionName=").append(AppUtils.getAppVersionName()).append("\n")
                    .append("| VersionCode=").append(AppUtils.getAppVersionCode()).append("\n")
                    .append("| ChannelName=").append(AppConfig.CHANNEL_NAME).append("\n")
                    .append("| PackageName=").append(AppUtils.getPackageName()).append("\n")
                    .append("| SHA1=").append(AppUtils.getAppSignatureSHA1()).append("\n")
                    .append("| ApiURL.HOST=").append(ApiURL.HOST).append("\n")
                    .append("| LibConfig1=").append(AppConfig.LIB_CONFIG1).append("\n")
                    .append("| LibConfig2=").append(AppConfig.LIB_CONFIG2).append("\n")
                    .append("| DeviceManufacturer=").append(Build.MANUFACTURER).append("\n")
                    .append("| DeviceModel=").append(Build.MODEL).append("\n")
                    .append("| AndroidVersion=").append(Build.VERSION.RELEASE).append("\n")
                    .append("| AndroidSdk=").append(Build.VERSION.SDK_INT).append("\n")
                    .append("********************************************************"));
        }
    }
}
