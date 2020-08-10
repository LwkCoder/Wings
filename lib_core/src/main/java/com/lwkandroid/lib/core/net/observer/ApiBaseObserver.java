package com.lwkandroid.lib.core.net.observer;


import com.lwkandroid.lib.core.net.bean.ApiException;

import io.reactivex.MaybeObserver;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * 网络请求Observer基类（非必须）
 *
 * @author LWK
 */
public abstract class ApiBaseObserver<T> implements Observer<T>,
        SingleObserver<T>,
        MaybeObserver<T>,
        IApiActionObserver<T>
{
    @Override
    public void onSubscribe(Disposable d)
    {
    }

    @Override
    public void onSuccess(T t)
    {
        onAccept(t);
    }

    @Override
    public void onNext(T t)
    {
        onAccept(t);
    }

    @Override
    public void onError(Throwable e)
    {
        onError(ApiException.handleThrowable(e));
    }

    @Override
    public void onComplete()
    {

    }
}
