package com.lwkandroid.wings.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Toast工具类
 */
public final class ToastUtils
{
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    private static Toast mToast;

    private ToastUtils()
    {
        throw new UnsupportedOperationException("Can't instantiate this class !");
    }

    /**
     * 显示时长较长的Toast
     */
    public static void showLong(@StringRes int resId)
    {
        show(Utils.getContext().getResources().getString(resId), Toast.LENGTH_LONG);
    }

    /**
     * 显示时长较长的Toast
     */
    public static void showLong(CharSequence s)
    {
        show(s, Toast.LENGTH_LONG);
    }

    /**
     * 显示时长较短的Toast
     */
    public static void showShort(@StringRes int resId)
    {
        show(Utils.getContext().getResources().getString(resId), Toast.LENGTH_SHORT);
    }

    /**
     * 显示时长较短的Toast
     */
    public static void showShort(CharSequence s)
    {
        show(s, Toast.LENGTH_SHORT);
    }

    //执行Toast的方法
    private static void show(final CharSequence s, final int duration)
    {
        mHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                if (mToast != null)
                {
                    mToast.cancel();
                    mToast = null;
                }

                mToast = Toast.makeText(Utils.getContext(), s, duration);
                mToast.show();
            }
        });
    }

}
