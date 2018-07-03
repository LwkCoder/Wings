package com.lwkandroid.wings.net.error;

import com.socks.library.KLog;

import io.reactivex.functions.Consumer;

/**
 * Created by LWK
 * TODO 未知异常捕获类
 */

public class UnknownErrorHandler implements Consumer<Throwable>
{
    @Override
    public void accept(Throwable throwable) throws Exception
    {
        KLog.e("RxHttp UnknownErrorHandler :" + throwable.toString());
    }
}
