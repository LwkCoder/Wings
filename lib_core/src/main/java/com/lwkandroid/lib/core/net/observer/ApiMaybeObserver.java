package com.lwkandroid.lib.core.net.observer;

import com.lwkandroid.lib.core.net.bean.ApiException;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;

/**
 * @description: 网络请求MaybeObserver
 * @author: LWK
 * @date: 2020/6/22 14:14
 */
public abstract class ApiMaybeObserver<T> implements MaybeObserver<T>, IApiObserver<T>
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
    public void onError(Throwable e)
    {
        onError(ApiException.handleThrowable(e));
    }

    @Override
    public void onComplete()
    {

    }
}
