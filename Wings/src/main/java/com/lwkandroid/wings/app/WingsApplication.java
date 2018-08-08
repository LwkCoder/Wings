package com.lwkandroid.wings.app;

import android.app.Application;

import com.lwkandroid.wings.Wings;
import com.lwkandroid.wings.init.WingsInitOpeartor;

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
        WingsInitOpeartor options = getWingsInitOptions();
        if (options == null)
        {
            options = new WingsInitOpeartor(this)
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
        Wings.onDestory();
        super.onTerminate();
    }

    /**
     * 初始化获取BuildConfig里配置的参数
     */
    protected abstract void initBuildConfig();

    /**
     * 获取Wings的初始化参数
     */
    protected abstract WingsInitOpeartor getWingsInitOptions();

    /**
     * 子类实现初始化第三方库的方法
     */
    protected abstract void initExtraLibraries();

}
