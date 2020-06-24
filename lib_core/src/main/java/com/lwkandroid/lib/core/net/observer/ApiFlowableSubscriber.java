package com.lwkandroid.lib.core.net.observer;


import com.lwkandroid.lib.core.net.bean.ApiException;

import org.reactivestreams.Subscription;

import io.reactivex.FlowableSubscriber;

/**
 * @description: 网络请求FLowableSubscriber
 * @author: LWK
 * @date: 2020/6/23 9:59
 */
public abstract class ApiFlowableSubscriber<T> implements FlowableSubscriber<T>, IApiObserver<T>
{
    @Override
    public void onSubscribe(Subscription s)
    {

    }

    @Override
    public void onNext(T t)
    {
        onAccept(t);
    }

    @Override
    public void onError(Throwable t)
    {
        onError(ApiException.handleThrowable(t));
    }
}
