package com.lwkandroid.wings.net.error;


import com.lwkandroid.wings.log.KLog;

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
