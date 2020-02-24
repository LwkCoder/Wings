package com.lwkandroid.lib.common;

import android.app.Activity;
import android.os.Build;

import com.lwkandroid.lib.core.log.KLog;
import com.squareup.leakcanary.RefWatcher;
import com.squareup.leakcanary.internal.ActivityLifecycleCallbacksAdapter;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * Description:解决LeakCanary上对ViewLocationHolder的泄漏报告
 *
 * @author LWK
 * @date 2020/2/15
 */
public class LeakCanaryCallBack extends ActivityLifecycleCallbacksAdapter
{
    private RefWatcher mRefWatcher;

    public LeakCanaryCallBack(RefWatcher refWatcher)
    {
        this.mRefWatcher = refWatcher;
    }

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
}