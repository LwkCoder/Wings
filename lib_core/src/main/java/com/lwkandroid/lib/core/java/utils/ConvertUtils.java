package com.lwkandroid.lib.core.java.utils;


import com.lwkandroid.lib.core.java.context.AppContext;

/**
 * 转换工具类
 * @author LWK
 */
public final class ConvertUtils
{
    private ConvertUtils()
    {
        throw new UnsupportedOperationException("Can't instantiate this class !");
    }

    /**
     * dp转px
     *
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(float dpValue)
    {
        final float scale = AppContext.get().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(float pxValue)
    {
        final float scale = AppContext.get().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param spValue sp值
     * @return px值
     */
    public static int sp2px(float spValue)
    {
        final float fontScale = AppContext.get().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转sp
     *
     * @param pxValue px值
     * @return sp值
     */
    public static int px2sp(float pxValue)
    {
        final float fontScale = AppContext.get().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


}
