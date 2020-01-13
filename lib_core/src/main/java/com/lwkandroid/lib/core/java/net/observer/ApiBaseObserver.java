package com.lwkandroid.lib.core.java.net.observer;


import com.lwkandroid.lib.core.java.net.bean.ApiException;

import org.reactivestreams.Subscription;

import io.reactivex.CompletableObserver;
import io.reactivex.FlowableSubscriber;
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
        FlowableSubscriber<T>,
        MaybeObserver<T>,
        CompletableObserver
{
    @Override
    public void onSubscribe(Subscription s)
    {

    }

    @Override
    public void onSubscribe(Disposable d)
    {
    }

    @Override
    public void onSuccess(T t)
    {
        subOnNext(t);
    }

    @Override
    public void onNext(T t)
    {
        subOnNext(t);
    }

    @Override
    public void onError(Throwable e)
    {
        subOnError(ApiException.handleThrowable(e));
    }

    @Override
    public void onComplete()
    {

    }


    /**
     * 实现类实现的下一步操作
     *
     * @param t 数据源
     */
    public abstract void subOnNext(T t);

    /**
     * 实现类实现的错误处理操作
     *
     * @param e 错误类型对象
     */
    public abstract void subOnError(ApiException e);
}
