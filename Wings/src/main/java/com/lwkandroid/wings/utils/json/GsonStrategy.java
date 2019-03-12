package com.lwkandroid.wings.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LWK
 * TODO Gson实现的网络请求结果解析工具类
 */
public final class GsonStrategy implements IJsonStrategy
{
    private static final Gson GSON;

    static
    {
        GSON = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();
    }

    @Override
    public boolean isJsonData(String data)
    {
        try
        {
            new JsonParser().parse(data);
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public <T> T parseJsonObject(String json, Class<T> classOfT)
    {
        return GSON.fromJson(json, classOfT);
    }

    @Override
    public <T> T parseJsonObject(String json, Type typeOfT)
    {
        return GSON.fromJson(json, typeOfT);
    }

    @Override
    public <T> List<T> parseJsonArray(String json, Class<T> classOfT)
    {
        List<T> lst = new ArrayList<>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array)
        {
            lst.add(GSON.fromJson(elem, classOfT));
        }
        return lst;
    }

    @Override
    public <T> List<T> parseJsonArray(String json, Type typeOfT)
    {
        List<T> lst = new ArrayList<>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array)
        {
            lst.add(GSON.<T>fromJson(elem, typeOfT));
        }
        return lst;
    }

    @Override
    public String toJson(Object o)
    {
        return GSON.toJson(o);
    }

}