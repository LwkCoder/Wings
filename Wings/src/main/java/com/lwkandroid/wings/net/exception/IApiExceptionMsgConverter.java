package com.lwkandroid.wings.net.exception;

/**
 * Created by LWK
 *  定义ApiException错误描述文案的方法
 */

public interface IApiExceptionMsgConverter
{
    String parserMessageByCode(int errorCode, String throwMessage);
}
