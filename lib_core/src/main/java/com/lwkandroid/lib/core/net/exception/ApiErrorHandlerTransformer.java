package com.lwkandroid.lib.core.net.exception;

import org.reactivestreams.Publisher;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;

/**
 * Description:全局错误处理操作
 *
 * @author LWK
 * @date 2020/4/2
 */
public class ApiErrorHandlerTransformer<T> implements ObservableTransformer<T, T>, FlowableTransformer<T, T>, SingleTransformer<T, T>,
        MaybeTransformer<T, T>, CompletableTransformer
{
    private RetryConfig mRetryConfigProvider;
    private ApiErrorConsumer mErrorConsumer;

    public ApiErrorHandlerTransformer(RetryConfig retryConfigProvider, ApiErrorConsumer errorConsumer)
    {
        this.mRetryConfigProvider = retryConfigProvider;
        this.mErrorConsumer = errorConsumer;
    }

    @Override
    public CompletableSource apply(Completable upstream)
    {
        return upstream.onErrorResumeNext(new ApiExceptionConvertFunc())
                .retryWhen(new ApiRetryFlowableExecutor(mRetryConfigProvider))
                .doOnError(mErrorConsumer);
    }

    @Override
    public Publisher<T> apply(Flowable<T> upstream)
    {
        return upstream.onErrorResumeNext(new ApiExceptionConvertFunc())
                .retryWhen(new ApiRetryFlowableExecutor(mRetryConfigProvider))
                .doOnError(mErrorConsumer);
    }

    @Override
    public MaybeSource<T> apply(Maybe<T> upstream)
    {
        return upstream.onErrorResumeNext(new ApiExceptionConvertFunc())
                .retryWhen(new ApiRetryFlowableExecutor(mRetryConfigProvider))
                .doOnError(mErrorConsumer);
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream)
    {
        return upstream.onErrorResumeNext(new ApiExceptionConvertFunc())
                .retryWhen(new ApiRetryObservableExecutor(mRetryConfigProvider))
                .doOnError(mErrorConsumer);
    }

    @Override
    public SingleSource<T> apply(Single<T> upstream)
    {
        return upstream.onErrorResumeNext(new ApiExceptionConvertFunc())
                .retryWhen(new ApiRetryFlowableExecutor(mRetryConfigProvider))
                .doOnError(mErrorConsumer);
    }
}
