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
        ApiException apiException = ApiException.handleThrowable(e);
        //可以在这里根据错误码设置对应的提示信息
        setDisplayMessage(apiException);
        _OnError(apiException);
    }

    @Override
    public void onComplete()
    {

    }


    public abstract void _OnNext(T t);

    public abstract void _OnError(ApiException e);

    /**
     * 子类可重写该方法根据具体错误设置提示信息
     */
    protected void setDisplayMessage(ApiException e)
    {
    }

}
