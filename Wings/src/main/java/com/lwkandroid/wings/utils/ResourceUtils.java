package com.lwkandroid.wings.utils;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.BoolRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.FontRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;

/**
 * Created by LWK
 * TODO 资源工具类
 */

public final class ResourceUtils
{
    public static boolean getBoolean(@BoolRes int resId)
    {
        return Utils.getContext().getResources().getBoolean(resId);
    }

    public static Drawable getDrawable(@DrawableRes int resId)
    {
        return ContextCompat.getDrawable(Utils.getContext(), resId);
    }

    public static String getString(@StringRes int resId)
    {
        return Utils.getContext().getResources().getString(resId);
    }

    public static String getString(@StringRes int resId, Object... formatArgs)
    {
        return Utils.getContext().getResources().getString(resId, formatArgs);
    }

    public static String[] getStringArray(@ArrayRes int resId)
    {
        return Utils.getContext().getResources().getStringArray(resId);
    }

    public static int getColor(@ColorRes int resId)
    {
        return ContextCompat.getColor(Utils.getContext(), resId);
    }

    public static ColorStateList getColorStateList(@ColorRes int resId)
    {
        return ContextCompat.getColorStateList(Utils.getContext(), resId);
    }

    /**
     * getDimension和getDimensionPixelOffset的功能类似，
     * getDimension返回float,getDimenPixelOffset返回int，
     * 这两者返回的结果取决于resId的单位，如果是dp/sp则需要乘以density，px不用。
     * <p>
     * getDimensionPixelSize则不管resId的单位是dp/sp/px,都会乘以denstiy。
     */
    public static float getDimen(@DimenRes int resId)
    {
        return Utils.getContext().getResources().getDimension(resId);
    }

    public static int getDimenPixelOffset(@DimenRes int resId)
    {
        return Utils.getContext().getResources().getDimensionPixelOffset(resId);
    }

    public static int getDimenPixelSize(@DimenRes int resId)
    {
        return Utils.getContext().getResources().getDimensionPixelSize(resId);
    }

    public static int getInteger(@IntegerRes int resId)
    {
        return Utils.getContext().getResources().getInteger(resId);
    }

    public static int[] getIntegerArray(@ArrayRes int resId)
    {
        return Utils.getContext().getResources().getIntArray(resId);
    }

    public static Typeface getTypeface(@FontRes int resId)
    {
        return ResourcesCompat.getFont(Utils.getContext(), resId);
    }
}
