package com.lwkandroid.wings.utils;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import androidx.annotation.ArrayRes;
import androidx.annotation.BoolRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.annotation.IntegerRes;
import androidx.annotation.StringRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

/**
 * Created by LWK
 *  资源工具类
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

    public static float getAttrFloatValue(int attrRes)
    {
        TypedValue typedValue = new TypedValue();
        Utils.getContext().getTheme().resolveAttribute(attrRes, typedValue, true);
        return typedValue.getFloat();
    }

    public static int getAttrColor(int attrRes)
    {
        TypedValue typedValue = new TypedValue();
        Utils.getContext().getTheme().resolveAttribute(attrRes, typedValue, true);
        return typedValue.data;
    }

    public static ColorStateList getAttrColorStateList(int attrRes)
    {
        TypedValue typedValue = new TypedValue();
        Utils.getContext().getTheme().resolveAttribute(attrRes, typedValue, true);
        return ContextCompat.getColorStateList(Utils.getContext(), typedValue.resourceId);
    }

    public static Drawable getAttrDrawable(int attrRes)
    {
        int[] attrs = new int[]{attrRes};
        TypedArray ta = Utils.getContext().obtainStyledAttributes(attrs);
        Drawable drawable = getAttrDrawable(ta, 0);
        ta.recycle();
        return drawable;
    }

    public static Drawable getAttrDrawable(TypedArray typedArray, int index)
    {
        TypedValue value = typedArray.peekValue(index);
        if (value != null && value.type != TypedValue.TYPE_ATTRIBUTE && value.resourceId != 0)
        {
            return AppCompatResources.getDrawable(Utils.getContext(), value.resourceId);
        }
        return null;
    }

    public static int getAttrDimen(int attrRes)
    {
        TypedValue typedValue = new TypedValue();
        Utils.getContext().getTheme().resolveAttribute(attrRes, typedValue, true);
        return TypedValue.complexToDimensionPixelSize(typedValue.data, Utils.getContext().getResources().getDisplayMetrics());
    }
}
