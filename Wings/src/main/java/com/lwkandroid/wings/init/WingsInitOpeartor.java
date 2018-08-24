package com.lwkandroid.wings.init;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.lwkandroid.wings.DebugTools;
import com.lwkandroid.wings.app.ActivityStack;
import com.lwkandroid.wings.net.RxHttp;
import com.lwkandroid.wings.net.constants.ApiConstants;
import com.lwkandroid.wings.net.interceptor.ApiLogInterceptor;
import com.lwkandroid.wings.utils.AppUtils;
import com.lwkandroid.wings.utils.CrashUtils;
import com.lwkandroid.wings.utils.Utils;
import com.socks.library.KLog;

/**
 * Created by LWK
 * TODO Wings初始化配置操作
 */

public abstract class WingsInitOpeartor implements Application.ActivityLifecycleCallbacks
{
    private Application context;

    public WingsInitOpeartor(Application context)
    {
        this.context = context;
    }

    public Application getContext()
    {
        return context;
    }

    public void init()
    {
        initCommonUtils();
        initKLog();
        initCrashUtils();
        initRxHttp();
        //Debug环境下额外操作
        if (DebugTools.DEBUG)
        {
            DebugTools.init(getContext());
            RxHttp.getGlobalOptions().addInterceptor(ApiConstants.TAG_LOG_INTERCEPTOR, new ApiLogInterceptor());
            if (getContext() != null)
                getContext().registerActivityLifecycleCallbacks(this);
        }
    }

    /*初始化公共工具*/
    protected void initCommonUtils()
    {
        Utils.init(getContext());
    }

    /*初始化日志KLog*/
    protected void initKLog()
    {
        KLog.init(DebugTools.DEBUG, AppUtils.getAppName());
    }

    /*初始化崩溃工具*/
    protected void initCrashUtils()
    {
        CrashUtils.init();
    }

    /*初始化RxHttp*/
    protected void initRxHttp()
    {
        RxHttp.init(getContext(), getApiBaseUrl());
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState)
    {
        KLog.d("onActivityCreated: " + activity.getClass().getSimpleName());
        ActivityStack.get().add(activity);
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
        ActivityStack.get().remove(activity);
    }

    /**
     * 设置网络请求BaseUrl
     */
    protected abstract String getApiBaseUrl();
}
