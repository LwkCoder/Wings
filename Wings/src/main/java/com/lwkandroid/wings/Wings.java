package com.lwkandroid.wings;

import android.content.Context;

import com.lwkandroid.wings.app.ActivityStack;
import com.lwkandroid.wings.init.WingsInitOpeartor;

/**
 * Created by LWK
 * TODO 入口类
 */

public final class Wings
{
    private Wings()
    {
    }

    /**
     * 初始化操作
     *
     * @param appContext
     */
    public static void init(Context appContext)
    {
        WingsInitOpeartor options = new WingsInitOpeartor(appContext)
        {
            @Override
            protected String getApiBaseUrl()
            {
                return null;
            }
        };
        init(options);
    }

    /**
     * 初始化操作
     *
     * @param options 定制化参数
     */
    public static void init(WingsInitOpeartor options)
    {
        options.init();
    }

    /**
     * Application的onTerminate()调用
     */
    public static void onDestory()
    {
        ActivityStack.get().finishAll();
    }
}
