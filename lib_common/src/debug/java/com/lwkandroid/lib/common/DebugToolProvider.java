package com.lwkandroid.lib.common;

import android.app.ActivityThread;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.chuckerteam.chucker.api.ChuckerInterceptor;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.lwkandroid.lib.core.imageloader.glide.GlideOkClient;
import com.lwkandroid.lib.core.net.RxHttp;
import com.lwkandroid.lib.core.utils.CrashUtils;
import com.squareup.leakcanary.AndroidExcludedRefs;
import com.squareup.leakcanary.DisplayLeakService;
import com.squareup.leakcanary.ExcludedRefs;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Description:Debug模式下辅助工具
 *
 * @author LWK
 * @date 2020/2/15
 */
public class DebugToolProvider extends ContentProvider
{
    private static RefWatcher mRefWatcher;

    @Override
    public boolean onCreate()
    {
        //添加崩溃日志记录
        CrashUtils.init();
        //启动严格模式
        //        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
        //        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        //初始化Chucker
        RxHttp.getGlobalOptions().addInterceptor("Chucker", new ChuckerInterceptor(getContext().getApplicationContext()));
        //初始化Stetho
        RxHttp.getGlobalOptions().addNetInterceptor("Stetho", new StethoInterceptor());
        Stetho.initialize(Stetho.newInitializerBuilder(getContext().getApplicationContext())
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(getContext().getApplicationContext()))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(getContext().getApplicationContext()))
                .build());
        GlideOkClient.get().getBuilder().addNetworkInterceptor(new StethoInterceptor());
        //初始化LeakCanary
        if (LeakCanary.isInAnalyzerProcess(getContext().getApplicationContext()))
        {
            return false;
        }
        ExcludedRefs excludedRefs = AndroidExcludedRefs.createAppDefaults()
                .instanceField("android.view.inputmethod.InputMethodManager", "sInstance").alwaysExclude()
                .instanceField("android.view.inputmethod.InputMethodManager", "mLastSrvView").alwaysExclude()
                .instanceField("com.android.internal.policy.PhoneWindow$DecorView", "mContext").alwaysExclude()
                .instanceField("android.support.v7.widget.SearchView$SearchAutoComplete", "mContext").alwaysExclude()
                .instanceField("android.view.ViewGroup$ViewLocationHolder", "mRoot").alwaysExclude()
                .instanceField("android.view.ViewGroup$ViewLocationHolder", "sPool").alwaysExclude()
                .staticField("android.view.ViewGroup$ViewLocationHolder", "sPool").alwaysExclude()
                .build();
        mRefWatcher = LeakCanary
                .refWatcher(getContext())
                .listenerServiceClass(DisplayLeakService.class)
                .excludedRefs(excludedRefs)
                .buildAndInstall();
        ActivityThread.currentApplication().registerActivityLifecycleCallbacks(new LeakCanaryCallBack(mRefWatcher));
        //添加Activity生命周期日志记录
        ActivityThread.currentApplication().registerActivityLifecycleCallbacks(new ActivityLogCallBack());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder)
    {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri)
    {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values)
    {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs)
    {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs)
    {
        return 0;
    }
}
