package com.lwkandroid.lib.core.net.observer;

import com.lwkandroid.lib.core.net.bean.ApiException;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * @description: 网络请求SingleObserver
 * @author: LWK
 * @date: 2020/6/23 9:56
 */
public abstract class ApiSingleObserver<T> implements SingleObserver<T>, IApiObserver<T>
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
}
