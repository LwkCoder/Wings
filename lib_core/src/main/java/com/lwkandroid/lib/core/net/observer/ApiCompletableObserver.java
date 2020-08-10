package com.lwkandroid.lib.core.net.observer;

import com.lwkandroid.lib.core.net.bean.ApiException;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;

/**
 * @description: CompletableObserver
 *
 * @author: LWK
 */
public abstract class ApiCompletableObserver implements CompletableObserver
{
    @Override
    public void onSubscribe(Disposable d)
    {
    }

    @Override
    public void onComplete()
    {
        subOnComplete();
    }

    @Override
    public void onError(Throwable e)
    {
        subOnError(ApiException.handleThrowable(e));
    }

    /**
     * 实现类实现的下一步操作
     */
    public abstract void subOnComplete();

    /**
     * 实现类实现的错误处理操作
     *
     * @param e 错误类型对象
     */
    public abstract void subOnError(ApiException e);
}
