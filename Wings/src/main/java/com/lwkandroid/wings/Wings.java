package com.lwkandroid.wings;

import android.app.Application;
import android.content.Context;

import com.lwkandroid.wings.app.WingsActivityLifecycleLog;
import com.lwkandroid.wings.app.WingsInitOptions;
import com.lwkandroid.wings.log.KLog;
import com.lwkandroid.wings.net.RxHttp;
import com.lwkandroid.wings.rx.UnknownErrorHandler;
import com.lwkandroid.wings.utils.AppUtils;
import com.lwkandroid.wings.utils.CrashUtils;

import androidx.core.content.FileProvider;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * 入口类
 *
 * @author LWK
 */

public final class Wings
{
    private static Application mApplication;

    private Wings()
    {
    }

    /**
     * 初始化操作
     */
    public static void init(Application context)
    {
        WingsInitOptions options = new WingsInitOptions(context);
        options.setApplicationContext(context);
        init(options);
    }

    /**
     * 初始化操作
     */
    public static void init(WingsInitOptions options)
    {
        mApplication = options.getApplicationContext();
        KLog.init(options.isDebugMode(), AppUtils.getAppName());
        DebugTools.init(options.getApplicationContext());
        CrashUtils.init(options.getCrashFilePath(), options.getCrashListener());
        RxHttp.init(options.getApiBaseUrl());
        RxJavaPlugins.setErrorHandler(new UnknownErrorHandler());
        for (Application.ActivityLifecycleCallbacks callback : options.getLifecycleCallbacks())
        {
            options.getApplicationContext().registerActivityLifecycleCallbacks(callback);
        }
        if (options.isDebugMode())
        {
            options.getApplicationContext().registerActivityLifecycleCallbacks(new WingsActivityLifecycleLog());
        }
    }

    public static Context getContext()
    {
        if (mApplication != null)
        {
            return mApplication.getApplicationContext();
        } else
        {
            throw new UnsupportedOperationException("You should invoke Wings.init(WingsInitOptions options) in Application !");
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
