package com.lwkandroid.wings.utils.json;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by LWK
 *  Json解析接口
 */

public interface IJsonStrategy
{
    boolean isJsonData(String data);

    <T> T parseJsonObject(String json, Class<T> classOfT);

    <T> T parseJsonObject(String json, Type typeOfT);

    <T> List<T> parseJsonArray(String json, Class<T> classOfT);

    <T> List<T> parseJsonArray(String json, Type typeOfT);

    String toJson(Object o);
}
