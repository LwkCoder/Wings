package com.lwkandroid.wings.utils.json;

/**
 * Created by LWK
 * TODO Json解析工具类入口
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
