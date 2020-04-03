package com.lwkandroid.lib.core.net.exception;

import io.reactivex.Single;

/**
 * Description:
 *
 * @author 20180004
 * @date 2020/4/2
 */
public interface IRetryCondition
{
    Single<Boolean> onRetryCondition(Throwable throwable);
}
