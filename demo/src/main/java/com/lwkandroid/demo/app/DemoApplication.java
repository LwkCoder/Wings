package com.lwkandroid.demo.app;

import android.app.Application;

import com.lwkandroid.demo.BuildConfig;
import com.lwkandroid.demo.rxhttp.AppErrorConfig;
import com.lwkandroid.demo.rxhttp.AppErrorMessageParser;
import com.lwkandroid.demo.rxhttp.AppUrl;
import com.lwkandroid.lib.core.net.RxHttp;
import com.lwkandroid.lib.core.net.exception.RetryConfig;

/**
 * Description:
 *
 * @author LWK
 * @date 2020/4/7
 */
public class DemoApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        RxHttp.init(BuildConfig.DEBUG, AppUrl.BASE_URL)
                .setRetryConfig(new RetryConfig(3, 1000, AppErrorConfig.RETRY_CONDITION))
                .setApiErrorConsumer(AppErrorConfig.ERROR_CONSUMER)
                .setApiExceptionMsgParser(new AppErrorMessageParser());

        registerActivityLifecycleCallbacks(new AppLifecycleCallBack());
    }
}
