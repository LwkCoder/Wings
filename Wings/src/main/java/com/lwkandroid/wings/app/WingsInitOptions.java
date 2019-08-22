package com.lwkandroid.wings.app;

import android.app.Application;

import com.lwkandroid.wings.DebugTools;
import com.lwkandroid.wings.image.ImageLoader;
import com.lwkandroid.wings.image.bean.ImageGlobalOptions;
import com.lwkandroid.wings.net.RxHttp;
import com.lwkandroid.wings.net.bean.ApiGlobalOptions;
import com.lwkandroid.wings.utils.CrashUtils;

import java.util.LinkedList;

/**
 * Description:Wings初始化配置参数
 *
 * @author LWK
 * @date 2019/5/13
 */
public final class WingsInitOptions
{
    private boolean mDebugMode = DebugTools.DEBUG;
    private Application mApplicationContext;
    private String mApiBaseUrl;
    private String mCrashFilePath;
    private CrashUtils.OnCrashListener mCrashListener;
    private LinkedList<Application.ActivityLifecycleCallbacks> mLifecycleCallbacks = new LinkedList<>();

    public boolean isDebugMode()
    {
        return mDebugMode;
    }

    public void setDebugMode(boolean debugMode)
    {
        this.mDebugMode = debugMode;
    }

    public WingsInitOptions(Application application)
    {
        this.mApplicationContext = application;
    }

    public Application getApplicationContext()
    {
        return mApplicationContext;
    }

    public void setApplicationContext(Application application)
    {
        this.mApplicationContext = application;
    }

    public String getApiBaseUrl()
    {
        return mApiBaseUrl;
    }

    public void setApiBaseUrl(String apiBaseUrl)
    {
        this.mApiBaseUrl = apiBaseUrl;
    }

    public String getCrashFilePath()
    {
        return mCrashFilePath;
    }

    public void setCrashFilePath(String crashFilePath)
    {
        this.mCrashFilePath = crashFilePath;
    }

    public CrashUtils.OnCrashListener getCrashListener()
    {
        return mCrashListener;
    }

    public void setCrashListener(CrashUtils.OnCrashListener listener)
    {
        this.mCrashListener = listener;
    }

    public LinkedList<Application.ActivityLifecycleCallbacks> getLifecycleCallbacks()
    {
        return mLifecycleCallbacks;
    }

    public void addLifecycleCallback(Application.ActivityLifecycleCallbacks callback)
    {
        this.mLifecycleCallbacks.add(callback);
    }

    public ApiGlobalOptions getRxHttpGlobalOptions()
    {
        return RxHttp.getGlobalOptions();
    }

    public ImageGlobalOptions getImageLoaderGloablOptions()
    {
        return ImageLoader.getGlobalOptions();
    }
}
