package com.lwkandroid.wings.app;

import android.app.Application;

import com.lwkandroid.wings.Wings;

/**
 * Created by LWK
 * TODO Wings定制化Application
 */

public abstract class WingsApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        initBuildConfig();
        initWings();
        initExtraLibraries();
    }

    /*初始化Wings*/
    protected void initWings()
    {
        WingsInitOperator options = getWingsInitOptions();
        if (options == null)
        {
            options = new WingsInitOperator(this)
            {
                @Override
                protected String getApiBaseUrl()
                {
                    return null;
                }
            };
        }
        Wings.init(options);
    }

    @Override
    public void onTerminate()
    {
        Wings.onDestroy();
        super.onTerminate();
    }

    /**
     * 初始化获取BuildConfig里配置的参数
     */
    protected abstract void initBuildConfig();

    /**
     * 获取Wings的初始化参数
     */
    protected abstract WingsInitOperator getWingsInitOptions();

    /**
     * 子类实现初始化第三方库的方法
     */
    protected abstract void initExtraLibraries();

}
