package com.lwkandroid.wings.utils;

import android.content.Context;

/**
 * Created by LWK
 * TODO 工具类公共方法区
 * 2017/5/9
 */

public final class Utils
{
    private static Context mContext;

    private Utils()
    {
        throw new UnsupportedOperationException("Can't instantiate this class !");
    }

    public static void init(Context context)
    {
        mContext = context.getApplicationContext();
    }

    public static Context getContext()
    {
        if (mContext != null)
        {
            return mContext;
        } else
        {
            throw new UnsupportedOperationException("You should invoke Utils.wrap(Context context) in Application !");
        }
    }
}
