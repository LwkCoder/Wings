package com.lwkandroid.wingsdemo.app;

import com.lwkandroid.wings.app.WingsApplication;
import com.lwkandroid.wings.init.WingsInitOperator;
import com.lwkandroid.wings.log.KLog;
import com.lwkandroid.wingsdemo.BuildConfig;
import com.lwkandroid.wingsdemo.net.ApiURL;

/**
 * Created by LWK
 * TODO Application入口
 */

public class DemoApplication extends WingsApplication
{
    @Override
    protected void initBuildConfig()
    {
        AppConfig.WX_APP_ID = BuildConfig.WX_APP_ID;
        AppConfig.WX_APP_SECRET = BuildConfig.WX_APP_SECRET;
        ApiURL.HOST = BuildConfig.HOST;

        KLog.i("WX_APP_ID=" + AppConfig.WX_APP_ID + " WX_APP_SECRET=" + AppConfig.WX_APP_SECRET + " API_HOST=" + ApiURL.HOST);
    }

    @Override
    protected WingsInitOperator getWingsInitOptions()
    {
        return new AppInitOptions(this);
    }

    @Override
    protected void initExtraLibraries()
    {

    }
}
