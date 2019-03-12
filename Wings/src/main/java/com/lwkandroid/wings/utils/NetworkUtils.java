package com.lwkandroid.wings.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络相关工具类【需要网络权限】
 */

public class NetworkUtils
{
    private NetworkUtils()
    {
        throw new UnsupportedOperationException("Can't instantiate this class !");
    }

    private static NetworkInfo getActiveNetworkInfo()
    {
        ConnectivityManager manager = (ConnectivityManager) Utils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo();
    }

    /**
     * 判断网络是否可用
     */
    public static boolean isNetworkConnected()
    {
        NetworkInfo info = getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    /**
     * 判断是否使用Wifi
     */
    public static boolean isUsingWifi()
    {
        NetworkInfo info = getActiveNetworkInfo();
        return info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 判断是否使用手机网络
     */
    public static boolean isUsingMoblieNet()
    {
        NetworkInfo info = getActiveNetworkInfo();
        return info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * 跳转到Wifi设置界面
     */
    public static void goToWifiSettingDetail()
    {
        Utils.getContext().startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
