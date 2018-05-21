package com.lwkandroid.wingsdemo.bean;

import com.lwkandroid.wings.annotation.NotProguard;
import com.lwkandroid.wings.net.bean.IApiResult;

/**
 * Created by LWK
 * TODO 聚合数据天气预报网络请求返回基类
 */
@NotProguard
public class WeatherHttpResult<T> implements IApiResult<T>
{

    private int status_code;
    private String message;
    private T data;

    public int getStatus_code()
    {
        return status_code;
    }

    public void setStatus_code(int status_code)
    {
        this.status_code = status_code;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void setData(T data)    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "WeatherHttpResult{" +
                "status_code=" + status_code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    @Override
    public int getCode()
    {
        return status_code;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    @Override
    public T getData()
    {
        return data;
    }
}
