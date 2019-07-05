package com.lwkandroid.wings.net.retry;

/**
 * 定义判断自动重试条件的接口
 *
 * @author LWK
 */

public interface IAutoRetry
{
    boolean judgeAutoRetry(Throwable throwable);
}
