package com.lwkandroid.lib.core.java.utils;

import android.content.Context;

import androidx.core.content.FileProvider;

/**
 * Description:FileProvider
 *
 * @author LWK
 * @date 2020/1/6
 */
public final class CustomFileProvider extends FileProvider
{
    public static Context mContext;

    @Override
    public boolean onCreate()
    {
        boolean b = super.onCreate();
        mContext = getContext();
        return b;
    }

    public static String createAuthorities()
    {
        return AppUtils.getPackageName() + ".wings.provider";
    }
}
