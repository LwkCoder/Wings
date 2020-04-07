package com.lwkandroid.demo.rxhttp;

/**
 * Description:定义APP网络请求的错误
 *
 * @author LWK
 * @date 2020/4/7
 */
public final class AppErrorCode
{
    private AppErrorCode()
    {
    }

    /**
     * 动态token过期
     */
    public static final int ACCESS_TOKEN_EXPIRE = 201;
    /**
     * 用户未登录
     */
    public static final int USER_NOT_LOGIN = 202;
}
