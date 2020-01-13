package com.lwkandroid.lib.core.java.net.bean;

import com.lwkandroid.lib.core.java.annotation.NotProguard;
import com.lwkandroid.lib.core.java.net.RxHttp;

import java.io.Serializable;

/**
 * RestFul风格默认的请求结果
 *
 * @author LWK
 */
@NotProguard
public class ApiRestfulResult<T> implements IApiRestfulResult<T>, Serializable
{
    private static final long serialVersionUID = 4302225912527235188L;
    private int code;

    private String msg;

    private T data;

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    @Override
    public int getCode()
    {
        return code;
    }

    @Override
    public String getMessage()
    {
        return msg;
    }

    @Override
    public T getData()
    {
        return data;
    }

    @Override
    public boolean isResultOK()
    {
        return code == RxHttp.getGlobalOptions().getApiRestfulResultOkCode();
    }

    @Override
    public String toString()
    {
        return "ApiRestfulResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
