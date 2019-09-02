package com.lwkandroid.wings.rx.lifecycle;

import org.reactivestreams.Publisher;

import java.util.concurrent.CancellationException;

import io.reactivex.BackpressureStrategy;
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
import io.reactivex.functions.Function;

/**
 * Description:RxLifeCycle的Transformer
 *
 * @author LWK
 * @date 2019/4/24
 */
class RxLifecycleTransformer<T> implements ObservableTransformer<T, T>,
        FlowableTransformer<T, T>,
        SingleTransformer<T, T>,
        MaybeTransformer<T, T>,
        CompletableTransformer
{
    final Observable<?> mObservable;

    RxLifecycleTransformer(Observable<?> observable)
    {
        this.mObservable = observable;
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream)
    {
        return upstream.takeUntil(mObservable);
    }

    @Override
    public Publisher<T> apply(Flowable<T> upstream)
    {
        return upstream.takeUntil(mObservable.toFlowable(BackpressureStrategy.LATEST));
    }

    @Override
    public SingleSource<T> apply(Single<T> upstream)
    {
        return upstream.takeUntil(mObservable.firstOrError());
    }

    @Override
    public MaybeSource<T> apply(Maybe<T> upstream)
    {
        return upstream.takeUntil(mObservable.firstElement());
    }

    @Override
    public CompletableSource apply(Completable upstream)
    {
        return Completable.ambArray(upstream,
                mObservable.flatMapCompletable((Function<Object, Completable>) o -> Completable.error(new CancellationException())));
    }

}
