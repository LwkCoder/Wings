package com.lwkandroid.wings.net.error;

import com.lwkandroid.wings.net.bean.ApiException;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by LWK
 *  ApiException异常转换方法
 */

public class ApiExceptionFunc<T> implements Function<Throwable, Observable<T>>
{
    @Override
    public Observable<T> apply(Throwable throwable) throws Exception
    {
        return Observable.error(ApiException.handleThrowable(throwable));
    }
}
