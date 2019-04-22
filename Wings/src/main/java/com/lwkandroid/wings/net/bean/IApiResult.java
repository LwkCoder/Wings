package com.lwkandroid.wings.net.bean;

/**
 * Created by LWK
 *  网络请求结果接口
 */

public interface IApiResult<T>
{
    int getCode();

    String getMessage();

    T getData();

    boolean isResultOK();
}
