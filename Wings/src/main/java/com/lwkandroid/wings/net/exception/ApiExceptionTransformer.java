package com.lwkandroid.wings.net.exception;

import com.lwkandroid.wings.net.bean.ApiException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * 异常转换为ApiException的Transformer
 *
 * @author LWK
 */
public final class ApiExceptionTransformer<T> implements ObservableTransformer<T, T>
{
    @Override
    public ObservableSource<T> apply(Observable<T> upstream)
    {
        return upstream.onErrorResumeNext((Function<Throwable, ObservableSource<T>>) throwable -> Observable.error(ApiException.handleThrowable(throwable)));
    }
}
