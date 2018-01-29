package com.lwkandroid.wings.net.observer;

import com.lwkandroid.wings.net.bean.ApiException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by LWK
 * TODO 网络请求Observer基类
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
        if (e instanceof ApiException)
        {
            _OnError((ApiException) e);
        } else
        {
            _OnError(ApiException.handleThrowable(e));
        }
    }

    @Override
    public void onComplete()
    {

    }


    public abstract void _OnNext(T t);

    public abstract void _OnError(ApiException e);

}
