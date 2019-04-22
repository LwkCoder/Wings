package com.lwkandroid.wings;

import android.content.Context;
import android.os.StrictMode;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.lwkandroid.wings.image.glide.GlideOkClient;
import com.lwkandroid.wings.net.RxHttp;
import com.readystatesoftware.chuck.ChuckInterceptor;
import com.squareup.leakcanary.AndroidExcludedRefs;
import com.squareup.leakcanary.DisplayLeakService;
import com.squareup.leakcanary.ExcludedRefs;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by LWK
 *  Debug工具
 * 2017/5/22
 */

public class DebugTools
{
    public static final boolean DEBUG = true;

    public static void init(Context context)
    {
        //启动严格模式
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        //Debug环境下可以初始化debug工具
        //Facebook的Stetho
        Stetho.initialize(Stetho.newInitializerBuilder(context.getApplicationContext())
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(context.getApplicationContext()))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context.getApplicationContext()))
                .build());
        RxHttp.getGlobalOptions().addInterceptor("Chuck", new ChuckInterceptor(context.getApplicationContext()));
        RxHttp.getGlobalOptions().addNetInterceptor("Stetho", new StethoInterceptor());
        GlideOkClient.get().getBuilder().addNetworkInterceptor(new StethoInterceptor());
        //Square的LeakCanary
        if (LeakCanary.isInAnalyzerProcess(context.getApplicationContext()))
        {
            return;
        }
        ExcludedRefs excludedRefs = AndroidExcludedRefs.createAppDefaults()
                .instanceField("android.view.inputmethod.InputMethodManager", "sInstance").alwaysExclude()
                .instanceField("android.view.inputmethod.InputMethodManager", "mLastSrvView").alwaysExclude()
                .instanceField("com.android.internal.policy.PhoneWindow$DecorView", "mContext").alwaysExclude()
                .instanceField("android.support.v7.widget.SearchView$SearchAutoComplete", "mContext").alwaysExclude()
                .instanceField("android.view.ViewGroup$ViewLocationHolder", "mRoot").alwaysExclude()
                .instanceField("android.view.ViewGroup$ViewLocationHolder", "sPool").alwaysExclude()
                .build();
        LeakCanary
                .refWatcher(context)
                .listenerServiceClass(DisplayLeakService.class)
                .excludedRefs(excludedRefs)
                .buildAndInstall();
    }
}
