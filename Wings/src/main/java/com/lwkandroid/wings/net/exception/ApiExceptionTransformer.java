package com.lwkandroid.wings.net.exception;

import com.lwkandroid.wings.net.bean.ApiException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * Created by LWK
 * 异常转换为ApiException的Transformer
 */
public final class ApiExceptionTransformer<T> implements ObservableTransformer<T, T>
{
    @Override
    public ObservableSource<T> apply(Observable<T> upstream)
    {
        return upstream.onErrorResumeNext(new Function<Throwable, ObservableSource<T>>()
        {
            @Override
            public ObservableSource<T> apply(Throwable throwable) throws Exception
            {
                return Observable.error(ApiException.handleThrowable(throwable));
            }
        });
    }
}
