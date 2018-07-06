package com.lwkandroid.wings.net.retry;

/**
 * Created by LWK
 * TODO 定义判断自动重试条件的接口
 */

public interface IAutoRetry
{
    boolean judgeAutoRetry(Throwable throwable);
}
