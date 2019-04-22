package com.lwkandroid.wings.net.error;

/**
 * Created by LWK
 *  定义ApiException错误描述文案的方法
 */

public interface IApiExceptionMsg
{
    String parserMessageByCode(int errorCode, String throwMessage);
}
