package com.lwkandroid.lib.common.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Description:Activity栈管理操作类
 *
 * @author LWK
 * @date 2020/2/24
 */
public class ActivityStackCallBack implements Application.ActivityLifecycleCallbacks
{
    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState)
    {
        ActivityStack.get().add(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity)
    {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity)
    {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity)
    {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity)
    {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState)
    {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity)
    {
        ActivityStack.get().remove(activity);
    }
}
