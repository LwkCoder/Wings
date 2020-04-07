package com.lwkandroid.demo.rxhttp;

import com.lwkandroid.lib.core.net.exception.ApiExceptionMsgParserImpl;

/**
 * Description:
 *
 * @author 20180004
 * @date 2020/4/7
 */
public class AppErrorMessageParser extends ApiExceptionMsgParserImpl
{
    @Override
    public String parseDisplayMessage(int errCode, String throwMessage)
    {
        switch (errCode)
        {
            case AppErrorCode.USER_NOT_LOGIN:
                return "用户未登录";
            case AppErrorCode.ACCESS_TOKEN_EXPIRE:
                return "AccessToken过期";
            default:
                return super.parseDisplayMessage(errCode, throwMessage);
        }
    }
}
