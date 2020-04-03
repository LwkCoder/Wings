package com.lwkandroid.lib.core.net.exception;

import com.lwkandroid.lib.core.net.RxHttp;
import com.lwkandroid.lib.core.net.bean.ApiException;

import io.reactivex.functions.Consumer;

/**
 * Description:统一处理错误的消费者
 *
 * @author LWK
 * @date 2020/4/3
 */
public abstract class ApiErrorConsumer implements Consumer<Throwable>
{
    /**
     * 空实现
     */
    public static ApiErrorConsumer empty()
    {
        return new ApiErrorConsumer()
        {
            @Override
            public void onAcceptApiException(ApiException e)
            {

            }
        };
    }

    @Override
    public void accept(Throwable throwable) throws Exception
    {
        ApiException e = ApiException.handleThrowable(throwable);
        String displayMessage = RxHttp.getGlobalOptions().getApiExceptionMsgParser().parserMessageByCode(e.getCode(), e.getThrowMessage());
        e.setDisplayMessage(displayMessage);
        onAcceptApiException(e);
    }

    public abstract void onAcceptApiException(ApiException e);
}
