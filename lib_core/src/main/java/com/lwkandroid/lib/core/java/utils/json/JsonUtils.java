package com.lwkandroid.lib.core.java.utils.json;

/**
 * Json解析工具类入口
 *
 * @author LWK
 */

public final class JsonUtils
{
    private JsonUtils()
    {
    }

    static
    {
        IMPL = new GsonStrategy();
    }

    private static final IJsonStrategy IMPL;

    public static IJsonStrategy get()
    {
        return IMPL;
    }
}
