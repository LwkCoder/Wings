package com.lwkandroid.app.app;

import android.app.Application;

import com.lwkandroid.app.net.ApiURL;
import com.lwkandroid.wings.init.WingsInitOpeartor;

/**
 * Created by LWK
 * TODO 定制化Wings初始化参数
 */

public class AppInitOptions extends WingsInitOpeartor
{
    public AppInitOptions(Application context)
    {
        super(context);
    }

    @Override
    protected String getApiBaseUrl()
    {
        return ApiURL.HOST;
    }
}
