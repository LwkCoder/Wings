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
    private static WingsInitOptions mOptions;

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
        if (options == null)
        {
            throw new IllegalArgumentException("You must init Wings by NotNull WingsInitOption Object.");
        }
        mOptions = options;
        mApplication = mOptions.getApplicationContext();
        KLog.init(mOptions.isDebugMode(), AppUtils.getAppName());
        DebugTools.init(mOptions.getApplicationContext());
        CrashUtils.init(mOptions.getCrashFilePath(), mOptions.getCrashListener());
        RxHttp.init(mOptions.isDebugMode(), mOptions.getApiBaseUrl());
        RxJavaPlugins.setErrorHandler(new UnknownErrorHandler());
        for (Application.ActivityLifecycleCallbacks callback : mOptions.getLifecycleCallbacks())
        {
            mOptions.getApplicationContext().registerActivityLifecycleCallbacks(callback);
        }
        if (mOptions.isDebugMode())
        {
            mOptions.getApplicationContext().registerActivityLifecycleCallbacks(new WingsActivityLifecycleLog());
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

    public static WingsInitOptions getOptions()
    {
        return mOptions;
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
