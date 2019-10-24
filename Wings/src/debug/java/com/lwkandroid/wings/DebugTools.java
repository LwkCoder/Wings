package com.lwkandroid.wings;

import android.app.Activity;
import android.app.Application;
import android.os.Build;

import com.chuckerteam.chucker.api.ChuckerInterceptor;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.lwkandroid.wings.image.glide.GlideOkClient;
import com.lwkandroid.wings.log.KLog;
import com.lwkandroid.wings.net.RxHttp;
import com.squareup.leakcanary.AndroidExcludedRefs;
import com.squareup.leakcanary.DisplayLeakService;
import com.squareup.leakcanary.ExcludedRefs;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.squareup.leakcanary.internal.ActivityLifecycleCallbacksAdapter;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * Debug工具
 *
 * @author LWK
 */
public class DebugTools
{
    public static final boolean DEBUG = true;
    private static RefWatcher mRefWatcher;

    public static void init(Application context)
    {
        //启动严格模式
        //        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
        //        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        //Debug环境下可以初始化debug工具
        //Facebook的Stetho
        Stetho.initialize(Stetho.newInitializerBuilder(context.getApplicationContext())
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(context.getApplicationContext()))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context.getApplicationContext()))
                .build());
        RxHttp.getGlobalOptions().addInterceptor("Chucker", new ChuckerInterceptor(context.getApplicationContext()));
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
                .staticField("android.view.ViewGroup$ViewLocationHolder", "sPool").alwaysExclude()
                .build();
        mRefWatcher = LeakCanary
                .refWatcher(context)
                .listenerServiceClass(DisplayLeakService.class)
                .excludedRefs(excludedRefs)
                .buildAndInstall();
        context.registerActivityLifecycleCallbacks(LEAK_CANARY_ACT_CALLBACK);
    }

    /**
     * 针对Android O上ViewLocationHolder内存泄漏的解决方案
     */
    private static final Application.ActivityLifecycleCallbacks LEAK_CANARY_ACT_CALLBACK = new ActivityLifecycleCallbacksAdapter()
    {
        @Override
        public void onActivityDestroyed(Activity activity)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                try
                {
                    Class<?> holder = Class.forName("android.view.ViewGroup$ViewLocationHolder");

                    Field sPoolField = holder.getDeclaredField("sPool");
                    sPoolField.setAccessible(true);
                    Object sPool = sPoolField.get(null);
                    Class<?> simplePoolClass = sPool.getClass().getSuperclass();
                    Field poolArrayField = simplePoolClass.getDeclaredField("mPool");
                    poolArrayField.setAccessible(true);
                    Object[] poolArray = (Object[]) poolArrayField.get(sPool);
                    Field poolSizeField = simplePoolClass.getDeclaredField("mPoolSize");
                    poolSizeField.setAccessible(true);
                    int poolSize = (int) poolSizeField.get(sPool);

                    // sanity check, we really care about poolSize
                    int poolArrayLength = Array.getLength(poolArray);
                    KLog.d(activity.getClass().getSimpleName() + "->poolArrayLength = " + poolArrayLength);
                    for (int i = 0; i < poolArrayLength; i++)
                    {
                        Object viewLocationHolder = poolArray[i];
                        if (viewLocationHolder == null)
                        {
                            KLog.d(activity.getClass().getSimpleName() + "->poolArray[" + i + "] == null");
                            continue;
                        }

                        Field mRootField = viewLocationHolder.getClass().getDeclaredField("mRoot");
                        mRootField.setAccessible(true);

                        Object mRoot = mRootField.get(viewLocationHolder);
                        if (mRoot == null)
                        {
                            KLog.d(activity.getClass().getSimpleName() + "->poolArray[" + i + "].mRoot == null");
                        } else
                        {
                            KLog.d(activity.getClass().getSimpleName() + "->Found leak!  poolArray[" + i + "].mRoot != null");
                        }
                    }
                    KLog.d(activity.getClass().getSimpleName() + "->poolSize = " + poolSize);
                    for (int i = 0; i < poolSize; i++)
                    {
                        Object viewLocationHolder = poolArray[i];
                        Field mRootField = viewLocationHolder.getClass().getDeclaredField("mRoot");
                        mRootField.setAccessible(true);
                        mRootField.set(viewLocationHolder, null);
                    }
                } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e)
                {
                    e.printStackTrace();
                }
            }
            mRefWatcher.watch(activity);
        }
    };
}
