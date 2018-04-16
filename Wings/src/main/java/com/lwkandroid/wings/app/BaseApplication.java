package com.lwkandroid.wings.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.lwkandroid.wings.DebugTools;
import com.lwkandroid.wings.net.RxHttp;
import com.lwkandroid.wings.net.constants.ApiConstants;
import com.lwkandroid.wings.net.interceptor.ApiLogInterceptor;
import com.lwkandroid.wings.net.interceptor.OkProgressInterceptor;
import com.lwkandroid.wings.utils.AppUtils;
import com.lwkandroid.wings.utils.CrashUtils;
import com.lwkandroid.wings.utils.StringUtils;
import com.lwkandroid.wings.utils.Utils;
import com.socks.library.KLog;

import java.util.Map;

import okhttp3.Interceptor;

/**
 * Created by LWK
 * TODO Application入口基类
 */
public abstract class BaseApplication extends Application implements Application.ActivityLifecycleCallbacks
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        initCommonUtils();
        initKLog();
        initCrashUtils();
        initDebugTools();
        initRxHttp();
        initExtraLibraries();
        //注册Activity生命周期监听
        registerActivityLifecycleCallbacks(this);
    }

    /**
     * 初始化公共工具类
     */
    protected void initCommonUtils()
    {
        Utils.init(this);
    }

    /**
     * 初始化日志
     */
    protected void initKLog()
    {
        KLog.init(DebugTools.DEBUG, AppUtils.getAppName());
    }

    /**
     * 初始化崩溃日志工具
     */
    protected void initCrashUtils()
    {
        CrashUtils.init();
    }

    /**
     * 子类实现该方法为网络请求加入全局拦截器
     */
    protected abstract Map<String, Interceptor> getCustomInterceptors();

    /**
     * 初始化Debug工具
     */
    protected void initDebugTools()
    {
        DebugTools.init(this);
    }

    /**
     * 初始化网络请求工具
     */
    protected void initRxHttp()
    {
        String baseUrl = getBaseUrl();
        if (StringUtils.isEmpty(baseUrl))
            throw new IllegalArgumentException("You have to set a non BaseUrl to RxHttp !!!");

        RxHttp.init(baseUrl)
                .addInterceptorsMap(getCustomInterceptors())
                .addInterceptor(ApiConstants.TAG_PROGRESS_INTERCEPTOR, new OkProgressInterceptor())
                .addInterceptor(ApiConstants.TAG_LOG_INTERCEPTOR, new ApiLogInterceptor());
    }

    /**
     * 子类实现
     * 返回全局网络请求域名
     */
    protected abstract String getBaseUrl();

    /**
     * 子类实现初始化第三方库的方法
     */
    protected abstract void initExtraLibraries();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState)
    {
        KLog.d("onActivityCreated: " + activity.getClass().getSimpleName());
        ActivityStack.getInstance().add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity)
    {
        KLog.d("onActivityStarted: " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityResumed(Activity activity)
    {
        KLog.d("onActivityResumed: " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityPaused(Activity activity)
    {
        KLog.d("onActivityPaused: " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityStopped(Activity activity)
    {
        KLog.d("onActivityStopped: " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState)
    {
        KLog.d("onActivitySaveInstanceState: " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityDestroyed(Activity activity)
    {
        KLog.d("onActivityDestroyed: " + activity.getClass().getSimpleName());
        ActivityStack.getInstance().remove(activity);
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
        ActivityStack.getInstance().finishAll();
        unregisterActivityLifecycleCallbacks(this);
    }
}
