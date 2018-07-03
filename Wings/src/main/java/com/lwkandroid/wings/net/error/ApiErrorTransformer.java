package com.lwkandroid.wings.net.error;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

/**
 * Created by LWK
 * TODO 异常转换为ApiException的Transformer
 */

public class ApiErrorTransformer<T> implements ObservableTransformer<T, T>
{
    @Override
    public ObservableSource<T> apply(Observable<T> upstream)
    {
        return upstream.onErrorResumeNext(new ApiExceptionFunc<T>());
    }
}
