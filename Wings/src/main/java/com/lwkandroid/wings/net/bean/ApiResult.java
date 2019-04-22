package com.lwkandroid.wings.net.bean;

import com.lwkandroid.wings.annotation.NotProguard;
import com.lwkandroid.wings.net.RxHttp;

import java.io.Serializable;

/**
 * Created by LWK
 *  默认的请求结果
 */
@NotProguard
public class ApiResult<T> implements IApiResult<T>, Serializable
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
        return code == RxHttp.getGlobalOptions().getApiResultOkCode();
    }

    @Override
    public String toString()
    {
        return "ApiResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
