package com.lwkandroid.lib.core.context;

import android.content.Context;

/**
 * Description:全局Context提供者
 *
 * @author LWK
 * @date 2020/1/6
 */
public final class AppContext
{
    private AppContext() throws IllegalAccessException
    {
        throw new IllegalAccessException("Can't instantiate this class !");
    }

    public static Context get()
    {
        return ContextProvider.mContext;
    }
}
