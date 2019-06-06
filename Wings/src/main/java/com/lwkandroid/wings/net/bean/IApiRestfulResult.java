package com.lwkandroid.wings.net.bean;

/**
 * 网络请求结果接口
 *
 * @author LWK
 */
public interface IApiRestfulResult<T>
{
    /**
     * 获取请求结果唯一状态码
     */
    int getCode();

    /**
     * 获取请求结果信息
     */
    String getMessage();

    /**
     * 获取请求结果数据
     */
    T getData();

    /**
     * 快捷判断该次请求是否成功
     */
    boolean isResultOK();
}
