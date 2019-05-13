package com.lwkandroid.wings;

import android.app.Application;

import com.lwkandroid.wings.app.WingsActivityLifecycleLog;
import com.lwkandroid.wings.app.WingsInitOptions;
import com.lwkandroid.wings.log.KLog;
import com.lwkandroid.wings.net.RxHttp;
import com.lwkandroid.wings.net.constants.ApiConstants;
import com.lwkandroid.wings.net.interceptor.ApiLogInterceptor;
import com.lwkandroid.wings.utils.AppUtils;
import com.lwkandroid.wings.utils.CrashUtils;
import com.lwkandroid.wings.utils.Utils;

import androidx.core.content.FileProvider;

/**
 * Created by LWK
 * 入口类
 */

public final class Wings
{
    private Wings()
    {
    }

    /**
     * 初始化操作
     */
    public static void init(Application context)
    {
        WingsInitOptions options = new WingsInitOptions();
        options.setApplicationContext(context);
        init(options);
    }

    /**
     * 初始化操作
     */
    public static void init(WingsInitOptions options)
    {
        Utils.init(options.getApplicationContext());
        KLog.init(options.isDebugMode(), AppUtils.getAppName());
        CrashUtils.init(options.getCrashFilePath(), options.getCrashListener());
        RxHttp.init(options.getApiBaseUrl());
        options.getApplicationContext().registerActivityLifecycleCallbacks(new WingsActivityLifecycleLog());
        for (Application.ActivityLifecycleCallbacks callback : options.getLifecycleCallbacks())
        {
            options.getApplicationContext().registerActivityLifecycleCallbacks(callback);
        }

        if (options.isDebugMode())
        {
            DebugTools.init(options.getApplicationContext());
            RxHttp.getGlobalOptions().addInterceptor(ApiConstants.TAG_LOG_INTERCEPTOR, new ApiLogInterceptor());
        }
    }

    /**
     * 授权Uri的FileProvider
     */
    public static class WingsFileProvider extends FileProvider
    {
        @Override
        public boolean onCreate()
        {
            return super.onCreate();
        }

        public static String createAuthorities()
        {
            return AppUtils.getPackageName() + ".wings.provider";
        }
    }

}
