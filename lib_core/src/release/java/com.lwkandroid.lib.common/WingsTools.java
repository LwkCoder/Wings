package com.lwkandroid.lib.common;

import android.app.ActivityThread;
import android.content.Context;

import com.chuckerteam.chucker.api.ChuckerInterceptor;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.lwkandroid.lib.core.helper.ActivityLifecycleHelper;
import com.lwkandroid.lib.core.imageloader.glide.GlideOkClient;
import com.lwkandroid.lib.core.net.RxHttp;
import com.squareup.leakcanary.AndroidExcludedRefs;
import com.squareup.leakcanary.DisplayLeakService;
import com.squareup.leakcanary.ExcludedRefs;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Description:工具初始化入口
 *
 * @author LWK
 * @date 2020/3/6
 */
public final class WingsTools
{
    private WingsTools()
    {
    }

    public static void initTools(Context context)
    {
        //崩溃日志统计
        CrashUtils.init();
        //Activity栈管理
        ActivityThread.currentApplication().registerActivityLifecycleCallbacks(ActivityLifecycleHelper.get());
    }
}
