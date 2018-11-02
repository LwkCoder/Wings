package com.lwkandroid.wings;

import android.app.Application;

import com.lwkandroid.wings.app.ActivityStack;
import com.lwkandroid.wings.app.WingsInitOperator;
import com.lwkandroid.wings.utils.AppUtils;

import androidx.core.content.FileProvider;

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
     */
    public static void init(Application context)
    {
        WingsInitOperator options = new WingsInitOperator(context)
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
    public static void init(WingsInitOperator options)
    {
        options.init();
    }

    /**
     * Application的onTerminate()调用
     */
    public static void onDestroy()
    {
        ActivityStack.get().finishAll();
    }

    /**
     * 授权Uri的FileProvider
     */
    public static class WingsFileProvider extends FileProvider
    {
        @Override
        public boolean onCreate()
        {
            return super.onCreate();
        }

        public static String createAuthorities()
        {
            return new StringBuilder()
                    .append(AppUtils.getPackageName())
                    .append(".wings.provider")
                    .toString();
        }
    }

}
