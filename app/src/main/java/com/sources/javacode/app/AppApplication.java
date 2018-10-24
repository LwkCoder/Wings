package com.sources.javacode.app;

import android.os.Build;

import com.lwkandroid.wings.DebugTools;
import com.lwkandroid.wings.app.WingsApplication;
import com.lwkandroid.wings.init.WingsInitOperator;
import com.lwkandroid.wings.log.KLog;
import com.lwkandroid.wings.utils.AppUtils;
import com.sources.javacode.BuildConfig;
import com.sources.javacode.net.ApiURL;

/**
 * Created by LWK
 * TODO Application入口
 */

public class AppApplication extends WingsApplication
{
    @Override
    protected void initBuildConfig()
    {
        ApiURL.HOST = BuildConfig.HOST;
        AppConfig.CHANNEL_NAME = BuildConfig.APP_CHANNEL;
    }

    @Override
    protected WingsInitOperator getWingsInitOptions()
    {
        return new AppInitOptions(this);
    }

    @Override
    protected void initExtraLibraries()
    {

    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        if (DebugTools.DEBUG)
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
                    .append("| DeviceManufacturer=").append(Build.MANUFACTURER).append("\n")
                    .append("| DeviceModel=").append(Build.MODEL).append("\n")
                    .append("| AndroidVersion=").append(Build.VERSION.RELEASE).append("\n")
                    .append("| AndroidSdk=").append(Build.VERSION.SDK_INT).append("\n")
                    .append("********************************************************"));
        }
    }
}
