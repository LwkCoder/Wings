package com.lwkandroid.wingsdemo.bean;

import com.lwkandroid.wings.net.bean.IApiResult;

import java.io.Serializable;

/**
 * Created by LWK
 * TODO 定义通用网络请求结果实体类
 */

public class CXHttpResult<T> implements Serializable, IApiResult<T>
{
    private static final long serialVersionUID = 926716737926969911L;
    private int status;
    private String msg;
    private T result;

    public CXHttpResult(int status, String message, T result)
    {
        this.status = status;
        this.msg = message;
        this.result = result;
    }

    public int getCode()
    {
        return status;
    }

    public void setCode(int status)
    {
        this.status = status;
    }

    public String getMessage()
    {
        return msg;
    }

    public void setMessage(String message)
    {
        this.msg = message;
    }

    public T getData()
    {
        return result;
    }

    public void setData(T result)
    {
        this.result = result;
    }

    @Override
    public String toString()
    {
        return "CXHttpResult{" +
                "status=" + status +
                ", message='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}
