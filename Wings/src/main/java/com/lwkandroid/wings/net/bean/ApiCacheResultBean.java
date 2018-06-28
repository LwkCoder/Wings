package com.lwkandroid.wings.net.bean;

import java.io.Serializable;

/**
 * Created by LWK
 * TODO
 */

public class ApiCacheResultBean<T> implements Serializable
{
    private boolean isFromCache;
    private T data;

    public ApiCacheResultBean()
    {
    }

    public ApiCacheResultBean(boolean isFromCache)
    {
        this.isFromCache = isFromCache;
    }

    public ApiCacheResultBean(boolean isFromCache, T data)
    {
        this.isFromCache = isFromCache;
        this.data = data;
    }

    public boolean isCache()
    {
        return isFromCache;
    }

    public void setCache(boolean cache)
    {
        isFromCache = cache;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    public T getData()
    {
        return data;
    }

    @Override
    public String toString()
    {
        return "ApiCacheResultBean{" +
                "isCache=" + isFromCache +
                ", data=" + data +
                '}';
    }
}
