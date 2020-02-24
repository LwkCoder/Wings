package com.lwkandroid.lib.core.rx.life;

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
 * Created by LWK
 * TODO 配合RxJava使用的生命周期自动绑定Transformer
 * 2020/2/12
 */
public final class RxLifeTransformer<T> implements ObservableTransformer<T, T>,
        FlowableTransformer<T, T>,
        MaybeTransformer<T, T>,
        SingleTransformer<T, T>,
        CompletableTransformer
{
    private final Observable<?> mObservable;

    public RxLifeTransformer(Observable<?> observable)
    {
        this.mObservable = observable;
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream)
    {
        return upstream.takeUntil(mObservable);
    }

    @Override
    public CompletableSource apply(Completable upstream)
    {
        return Completable.ambArray(upstream,
                mObservable.flatMapCompletable((Function<Object, Completable>) o -> Completable.error(new CancellationException())));
    }

    @Override
    public Publisher<T> apply(Flowable<T> upstream)
    {
        return upstream.takeUntil(mObservable.toFlowable(BackpressureStrategy.LATEST));
    }

    @Override
    public MaybeSource<T> apply(Maybe<T> upstream)
    {
        return upstream.takeUntil(mObservable.firstElement());
    }

    @Override
    public SingleSource<T> apply(Single<T> upstream)
    {
        return upstream.takeUntil(mObservable.firstOrError());
    }
}
