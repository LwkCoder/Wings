package com.lwkandroid.wings.utils;

import android.text.TextUtils;

import java.util.List;

/**
 * 获取不为空的数据
 */
public final class TypeUtil
{
    public static String getValue(String obj)
    {
        if (obj == null)
        {
            obj = "";
        }
        return obj;
    }

    public static String getValue(String obj, String defaultValue)
    {
        if (obj == null)
        {
            obj = defaultValue;
        }
        return obj;
    }

    public static int getValue(Integer obj)
    {
        if (obj == null)
        {
            obj = 0;
        }
        return obj;
    }

    public static int getValue(Integer obj, int defaultValue)
    {
        if (obj == null)
        {
            obj = defaultValue;
        }
        return obj;
    }

    public static double getValue(Double obj, double defaultValue)
    {
        if (obj == null)
        {
            obj = defaultValue;
        }
        return obj;
    }

    public static float getValue(Float obj, float defaultValue)
    {
        if (obj == null)
        {
            obj = defaultValue;
        }
        return obj;
    }

    public static boolean getValue(Boolean obj)
    {
        if (obj != null && obj)
        {
            return true;
        }
        return false;
    }

    public static boolean getValue(Boolean obj, boolean defaultValue)
    {
        if (obj == null)
        {
            return defaultValue;
        }
        return obj;
    }

    public static boolean isEmpty(Integer value)
    {
        if (value == null || value == 0)
        {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(String value)
    {
        if (value == null || "".equals(value))
        {
            return true;
        }
        return false;
    }

    public static long getValue(Long obj)
    {
        if (obj == null)
        {
            obj = 0L;
        }
        return obj;
    }

    public static double getValue(Double obj)
    {
        if (obj == null)
        {
            obj = 0D;
        }
        return obj;
    }

    /**
     * obj是否可以转为整型
     */
    public static boolean isNumber(String obj)
    {
        try
        {
            Integer.valueOf(obj);
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }

    /**
     * 判断List是否为空
     */
    public static boolean isEmpty(List lt)
    {
        if (lt != null && lt.size() > 0)
        {
            return false;
        }
        return true;
    }

    /**
     * 获取str对应的int值
     *
     * @param str
     * @return
     */
    public static int getIntValue(String str)
    {
        return getIntValue(str, 0);
    }

    public static int getIntValue(String str, int defaultValue)
    {
        if (TextUtils.isEmpty(str))
        {
            return defaultValue;
        }

        try
        {
            return Integer.valueOf(str);
        } catch (Exception e)
        {
            return defaultValue;
        }
    }

}
