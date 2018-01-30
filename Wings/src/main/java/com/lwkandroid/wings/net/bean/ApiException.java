package com.lwkandroid.wings.net.bean;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.lwkandroid.wings.net.constants.ApiExceptionCode;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.io.NotSerializableException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * Created by LWK
 * TODO 自定义异常：网络请求错误
 */

public class ApiException extends Exception
{
    private static final long serialVersionUID = 4966919777463155346L;
    private int code;
    private String throwMessage;
    private String displayMessage;

    public ApiException(int code, String throwMessage)
    {
        super(throwMessage);
        this.code = code;
        this.throwMessage = throwMessage;
        this.displayMessage = createDisplayMessage(code);
    }

    public ApiException(int code, String throwMessage, String displayMessage)
    {
        super(throwMessage);
        this.code = code;
        this.throwMessage = throwMessage;
        this.displayMessage = displayMessage;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getThrowMessage()
    {
        return throwMessage;
    }

    public void setThrowMessage(String throwMessage)
    {
        this.throwMessage = throwMessage;
    }

    public String getDisplayMessage()
    {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage)
    {
        this.displayMessage = displayMessage;
    }

    @Override
    public String toString()
    {
        return "ApiException{" +
                "code=" + code +
                ", throwMessage='" + throwMessage + '\'' +
                ", displayMessage='" + displayMessage + '\'' +
                '}';
    }

    /**
     * 统一处理异常
     */
    public static ApiException handleThrowable(Throwable e)
    {
        if (e instanceof ApiException)
        {
            return (ApiException) e;
        } else if (e instanceof HttpException)
        {
            HttpException httpException = (HttpException) e;
            httpException.printStackTrace();
            return new ApiException(httpException.code(), httpException.getMessage());
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof JsonSyntaxException
                || e instanceof JsonSerializer
                || e instanceof NotSerializableException
                || e instanceof ParseException)
        {
            e.printStackTrace();
            return new ApiException(ApiExceptionCode.PARSE_ERROR, e.getMessage());
        } else if (e instanceof ClassCastException)
        {
            e.printStackTrace();
            return new ApiException(ApiExceptionCode.CAST_ERROR, e.getMessage());
        } else if (e instanceof ConnectException)
        {
            e.printStackTrace();
            return new ApiException(ApiExceptionCode.NETWORD_ERROR, e.getMessage());
        } else if (e instanceof javax.net.ssl.SSLHandshakeException)
        {
            e.printStackTrace();
            return new ApiException(ApiExceptionCode.SSL_ERROR, e.getMessage());
        } else if (e instanceof ConnectTimeoutException)
        {
            e.printStackTrace();
            return new ApiException(ApiExceptionCode.TIMEOUT_ERROR, e.getMessage());
        } else if (e instanceof java.net.SocketTimeoutException)
        {
            e.printStackTrace();
            return new ApiException(ApiExceptionCode.TIMEOUT_ERROR, e.getMessage());
        } else if (e instanceof UnknownHostException)
        {
            e.printStackTrace();
            return new ApiException(ApiExceptionCode.UNKNOWNHOST_ERROR, e.getMessage());
        } else if (e instanceof NullPointerException)
        {
            e.printStackTrace();
            return new ApiException(ApiExceptionCode.NULLPOINTER_EXCEPTION, e.getMessage());
        } else
        {
            e.printStackTrace();
            return new ApiException(ApiExceptionCode.UNKNOWN, e.getMessage());
        }
    }

    /**
     * 定义描述话语
     */
    private static String createDisplayMessage(int code)
    {
        switch (code)
        {
            case ApiExceptionCode.PARSE_ERROR:
                return "数据解析错误";
            case ApiExceptionCode.CAST_ERROR:
                return "类型转换错误";
            case ApiExceptionCode.NETWORD_ERROR:
                return "连接失败";
            case ApiExceptionCode.SSL_ERROR:
                return "证书验证失败";
            case ApiExceptionCode.TIMEOUT_ERROR:
                return "连接超时";
            case ApiExceptionCode.UNKNOWNHOST_ERROR:
                return "无法解析该域名";
            case ApiExceptionCode.NULLPOINTER_EXCEPTION:
                return "空指针异常";
            case ApiExceptionCode.IO_EXCEPTION:
                return "IO流异常";
            default:
                return "未知错误";
        }
    }
}
