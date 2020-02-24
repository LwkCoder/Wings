package com.lwkandroid.lib.core.net.retry;


import com.lwkandroid.lib.core.net.bean.ApiException;
import com.lwkandroid.lib.core.net.constants.ApiExceptionCode;

import org.apache.http.conn.ConnectTimeoutException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * 默认判断自动重试的条件
 *
 * @author LWK
 */
public class AutoRetryJudgeImpl implements IAutoRetry
{
    @Override
    public boolean judgeAutoRetry(Throwable throwable)
    {
        ApiException apiException = ApiException.handleThrowable(throwable);
        int errCode = apiException.getCode();

        return errCode == ApiExceptionCode.CONNECT_ERROR
                || errCode == ApiExceptionCode.TIMEOUT_ERROR
                || throwable instanceof ConnectException
                || throwable instanceof ConnectTimeoutException
                || throwable instanceof SocketTimeoutException;
    }
}
