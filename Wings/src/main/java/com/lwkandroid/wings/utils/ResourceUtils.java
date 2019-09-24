package com.lwkandroid.wings.utils;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import com.lwkandroid.wings.Wings;
import com.lwkandroid.wings.log.KLog;

import java.io.IOException;
import java.io.InputStream;

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
 * 资源工具类
 */

public final class ResourceUtils
{
    public static boolean getBoolean(@BoolRes int resId)
    {
        return Wings.getContext().getResources().getBoolean(resId);
    }

    public static Drawable getDrawable(@DrawableRes int resId)
    {
        return ContextCompat.getDrawable(Wings.getContext(), resId);
    }

    public static String getString(@StringRes int resId)
    {
        return Wings.getContext().getResources().getString(resId);
    }

    public static String getString(@StringRes int resId, Object... formatArgs)
    {
        return Wings.getContext().getResources().getString(resId, formatArgs);
    }

    public static String[] getStringArray(@ArrayRes int resId)
    {
        return Wings.getContext().getResources().getStringArray(resId);
    }

    public static int getColor(@ColorRes int resId)
    {
        return ContextCompat.getColor(Wings.getContext(), resId);
    }

    public static ColorStateList getColorStateList(@ColorRes int resId)
    {
        return ContextCompat.getColorStateList(Wings.getContext(), resId);
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
        return Wings.getContext().getResources().getDimension(resId);
    }

    public static int getDimenPixelOffset(@DimenRes int resId)
    {
        return Wings.getContext().getResources().getDimensionPixelOffset(resId);
    }

    public static int getDimenPixelSize(@DimenRes int resId)
    {
        return Wings.getContext().getResources().getDimensionPixelSize(resId);
    }

    public static int getInteger(@IntegerRes int resId)
    {
        return Wings.getContext().getResources().getInteger(resId);
    }

    public static int[] getIntegerArray(@ArrayRes int resId)
    {
        return Wings.getContext().getResources().getIntArray(resId);
    }

    public static Typeface getTypeface(@FontRes int resId)
    {
        return ResourcesCompat.getFont(Wings.getContext(), resId);
    }

    public static float getAttrFloatValue(int attrRes)
    {
        TypedValue typedValue = new TypedValue();
        Wings.getContext().getTheme().resolveAttribute(attrRes, typedValue, true);
        return typedValue.getFloat();
    }

    public static int getAttrColor(int attrRes)
    {
        TypedValue typedValue = new TypedValue();
        Wings.getContext().getTheme().resolveAttribute(attrRes, typedValue, true);
        return typedValue.data;
    }

    public static ColorStateList getAttrColorStateList(int attrRes)
    {
        TypedValue typedValue = new TypedValue();
        Wings.getContext().getTheme().resolveAttribute(attrRes, typedValue, true);
        return ContextCompat.getColorStateList(Wings.getContext(), typedValue.resourceId);
    }

    public static Drawable getAttrDrawable(int attrRes)
    {
        int[] attrs = new int[]{attrRes};
        TypedArray ta = Wings.getContext().obtainStyledAttributes(attrs);
        Drawable drawable = getAttrDrawable(ta, 0);
        ta.recycle();
        return drawable;
    }

    public static Drawable getAttrDrawable(TypedArray typedArray, int index)
    {
        TypedValue value = typedArray.peekValue(index);
        if (value != null && value.type != TypedValue.TYPE_ATTRIBUTE && value.resourceId != 0)
        {
            return AppCompatResources.getDrawable(Wings.getContext(), value.resourceId);
        }
        return null;
    }

    public static int getAttrDimen(int attrRes)
    {
        TypedValue typedValue = new TypedValue();
        Wings.getContext().getTheme().resolveAttribute(attrRes, typedValue, true);
        return TypedValue.complexToDimensionPixelSize(typedValue.data, Wings.getContext().getResources().getDisplayMetrics());
    }

    /**
     * 获取Raw文件夹下资源
     *
     * @param resId R.raw.xxx形式的资源id
     * @return 文件输入流
     */
    public static InputStream getRaw(int resId)
    {
        return Wings.getContext().getResources().openRawResource(resId);
    }

    public static InputStream getAsset(String fileName)
    {
        try
        {
            return Wings.getContext().getAssets().open(fileName);
        } catch (IOException e)
        {
            KLog.e("Fail to open assets file:" + e.toString());
        }
        return null;
    }
}
