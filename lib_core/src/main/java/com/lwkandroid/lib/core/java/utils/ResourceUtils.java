package com.lwkandroid.lib.core.java.utils;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import com.lwkandroid.lib.core.java.context.AppContext;
import com.lwkandroid.lib.core.java.log.KLog;

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
        return AppContext.get().getResources().getBoolean(resId);
    }

    public static String getString(@StringRes int resId)
    {
        return AppContext.get().getResources().getString(resId);
    }

    public static String getString(@StringRes int resId, Object... formatArgs)
    {
        return AppContext.get().getResources().getString(resId, formatArgs);
    }

    public static String[] getStringArray(@ArrayRes int resId)
    {
        return AppContext.get().getResources().getStringArray(resId);
    }

    public static int getColor(@ColorRes int resId)
    {
        return ContextCompat.getColor(AppContext.get(), resId);
    }

    public static ColorStateList getColorStateList(@ColorRes int resId)
    {
        return ContextCompat.getColorStateList(AppContext.get(), resId);
    }

    public static Drawable getDrawable(@DrawableRes int resId)
    {
        return ContextCompat.getDrawable(AppContext.get(), resId);
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
        return AppContext.get().getResources().getDimension(resId);
    }

    public static int getDimenPixelOffset(@DimenRes int resId)
    {
        return AppContext.get().getResources().getDimensionPixelOffset(resId);
    }

    public static int getDimenPixelSize(@DimenRes int resId)
    {
        return AppContext.get().getResources().getDimensionPixelSize(resId);
    }

    public static int getInteger(@IntegerRes int resId)
    {
        return AppContext.get().getResources().getInteger(resId);
    }

    public static int[] getIntegerArray(@ArrayRes int resId)
    {
        return AppContext.get().getResources().getIntArray(resId);
    }

    public static Typeface getTypeface(@FontRes int resId)
    {
        return ResourcesCompat.getFont(AppContext.get(), resId);
    }

    public static float getAttrFloatValue(int attrRes)
    {
        TypedValue typedValue = new TypedValue();
        AppContext.get().getTheme().resolveAttribute(attrRes, typedValue, true);
        return typedValue.getFloat();
    }

    public static int getAttrColor(int attrRes)
    {
        TypedValue typedValue = new TypedValue();
        AppContext.get().getTheme().resolveAttribute(attrRes, typedValue, true);
        return typedValue.data;
    }

    public static ColorStateList getAttrColorStateList(int attrRes)
    {
        TypedValue typedValue = new TypedValue();
        AppContext.get().getTheme().resolveAttribute(attrRes, typedValue, true);
        return ContextCompat.getColorStateList(AppContext.get(), typedValue.resourceId);
    }

    public static Drawable getAttrDrawable(int attrRes)
    {
        int[] attrs = new int[]{attrRes};
        TypedArray ta = AppContext.get().obtainStyledAttributes(attrs);
        Drawable drawable = getAttrDrawable(ta, 0);
        ta.recycle();
        return drawable;
    }

    public static Drawable getAttrDrawable(TypedArray typedArray, int index)
    {
        TypedValue value = typedArray.peekValue(index);
        if (value != null && value.type != TypedValue.TYPE_ATTRIBUTE && value.resourceId != 0)
        {
            return AppCompatResources.getDrawable(AppContext.get(), value.resourceId);
        }
        return null;
    }

    public static int getAttrDimen(int attrRes)
    {
        TypedValue typedValue = new TypedValue();
        AppContext.get().getTheme().resolveAttribute(attrRes, typedValue, true);
        return TypedValue.complexToDimensionPixelSize(typedValue.data, AppContext.get().getResources().getDisplayMetrics());
    }

    /**
     * 获取Raw文件夹下资源
     *
     * @param resId R.raw.xxx形式的资源id
     * @return 文件输入流
     */
    public static InputStream getRaw(int resId)
    {
        return AppContext.get().getResources().openRawResource(resId);
    }

    public static InputStream getAsset(String fileName)
    {
        try
        {
            return AppContext.get().getAssets().open(fileName);
        } catch (IOException e)
        {
            KLog.e("Fail to open assets file:" + e.toString());
        }
        return null;
    }
}
