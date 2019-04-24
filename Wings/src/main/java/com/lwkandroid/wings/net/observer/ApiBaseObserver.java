package com.lwkandroid.wings.net.observer;

import com.lwkandroid.wings.net.bean.ApiException;

import org.reactivestreams.Subscription;

import io.reactivex.CompletableObserver;
import io.reactivex.FlowableSubscriber;
import io.reactivex.MaybeObserver;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * Created by LWK
 * 网络请求Observer基类（非必须）
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
        ApiException apiException = ApiException.handleThrowable(e);
        subOnError(apiException);
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
