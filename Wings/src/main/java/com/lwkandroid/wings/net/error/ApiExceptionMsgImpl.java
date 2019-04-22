package com.lwkandroid.wings.net.error;

import com.lwkandroid.wings.net.constants.ApiExceptionCode;

/**
 * Created by LWK
 *  设置ApiException错误描述的方法
 */

public class ApiExceptionMsgImpl implements IApiExceptionMsg
{
    @Override
    public String parserMessageByCode(int errorCode, String throwMessage)
    {
        if (errorCode == ApiExceptionCode.PARSE_ERROR)
        {
            return "数据解析错误";
        } else if (errorCode == ApiExceptionCode.CAST_ERROR)
        {
            return "类型转换错误";
        } else if (errorCode == ApiExceptionCode.CONNECT_ERROR)
        {
            return "连接失败";
        } else if (errorCode == ApiExceptionCode.SSL_ERROR)
        {
            return "证书验证失败";
        } else if (errorCode == ApiExceptionCode.TIMEOUT_ERROR)
        {
            return "连接超时";
        } else if (errorCode == ApiExceptionCode.UNKNOWNHOST_ERROR)
        {
            return "无法解析该域名";
        } else if (errorCode == ApiExceptionCode.NULLPOINTER_EXCEPTION)
        {
            return "空指针异常";
        } else if (errorCode == ApiExceptionCode.IO_EXCEPTION)
        {
            return "IO流异常";
        } else if (errorCode == ApiExceptionCode.RESPONSE_EMPTY)
        {
            return "服务器无响应";
        } else if (errorCode == ApiExceptionCode.CACHE_EMPTY)
        {
            return "本地无缓存";
        } else
        {
            return getCustomMessage(errorCode, throwMessage);
        }
    }

    /**
     * 子类可继承该类完成补全
     *
     * @param errCode
     * @param throwMessage
     * @return
     */
    public String getCustomMessage(int errCode, String throwMessage)
    {
        return "未知错误";
    }
}
