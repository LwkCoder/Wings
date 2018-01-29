package com.lwkandroid.wings.net.utils;

import com.lwkandroid.wings.utils.StringUtils;
import com.socks.library.KLog;

import java.util.HashMap;

/**
 * Created by LWK
 * TODO 网络请求QueryMap、FieldMap参数辅助工具
 */

public class FormDataMap extends HashMap<String, String>
{
    private static final String TAG = "FormDataMap";
    private static final long serialVersionUID = 1124339485844644063L;

    /**
     * 添加文本参数
     */
    public FormDataMap addParam(String key, String value)
    {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value))
        {
            KLog.e(TAG, "FormDataMap's params can't be null ! Ignore the params: key=" + key + " value=" + value);
            return this;
        }
        put(key, value);
        return this;
    }

    /**
     * 添加short参数
     */
    public FormDataMap addParam(String key, short value)
    {
        put(key, String.valueOf(value));
        return this;
    }

    /**
     * 添加int参数
     */
    public FormDataMap addParam(String key, int value)
    {
        put(key, String.valueOf(value));
        return this;
    }

    /**
     * 添加long参数
     */
    public FormDataMap addParam(String key, long value)
    {
        put(key, String.valueOf(value));
        return this;
    }

    /**
     * 添加double参数
     */
    public FormDataMap addParam(String key, double value)
    {
        put(key, String.valueOf(value));
        return this;
    }

    /**
     * 添加float参数
     */
    public FormDataMap addParam(String key, float value)
    {
        put(key, String.valueOf(value));
        return this;
    }
}
