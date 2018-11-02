package com.lwkandroid.wings.net.observer;

import com.lwkandroid.wings.net.bean.ApiException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by LWK
 * TODO 网络请求Observer基类（非必须）
 */

public abstract class ApiBaseObserver<T> implements Observer<T>
{
    @Override
    public void onSubscribe(Disposable d)
    {
    }

    @Override
    public void onNext(T t)
    {
        _OnNext(t);
    }

    @Override
    public void onError(Throwable e)
    {
        ApiException apiException = ApiException.handleThrowable(e);
        onApiException(apiException);
    }

    @Override
    public void onComplete()
    {

    }


    public abstract void _OnNext(T t);

    public abstract void onApiException(ApiException e);

}
