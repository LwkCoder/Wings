package com.lwkandroid.wings.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.lwkandroid.wings.log.KLog;

/**
 * Description:Activity生命周期日志
 *
 * @author LWK
 * @date 2019/5/13
 */
public class WingsActivityLifecycleLog implements Application.ActivityLifecycleCallbacks
{
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
}
