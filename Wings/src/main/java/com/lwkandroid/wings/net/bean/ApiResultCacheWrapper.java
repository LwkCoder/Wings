package com.lwkandroid.wings.net.bean;

import java.io.Serializable;

/**
 * Created by LWK
 *  请求结果的缓存封装
 */

public class ApiResultCacheWrapper<T> implements Serializable
{
    private static final long serialVersionUID = -5458415801107285006L;
    private boolean isFromCache;
    private T data;

    public ApiResultCacheWrapper()
    {
    }

    public ApiResultCacheWrapper(boolean isFromCache)
    {
        this.isFromCache = isFromCache;
    }

    public ApiResultCacheWrapper(boolean isFromCache, T data)
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
        return "ApiResultCacheWrapper{" +
                "isCache=" + isFromCache +
                ", data=" + data +
                '}';
    }
}
