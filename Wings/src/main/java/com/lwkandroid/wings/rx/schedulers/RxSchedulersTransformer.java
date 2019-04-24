package com.lwkandroid.wings.rx.schedulers;

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
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;

/**
 * Description: 切换线程的Transformer
 *
 * @author LWK
 * @date 2019/4/24
 */
public class RxSchedulersTransformer<T> implements ObservableTransformer<T, T>,
        FlowableTransformer<T, T>,
        SingleTransformer<T, T>,
        MaybeTransformer<T, T>,
        CompletableTransformer
{
    private Scheduler mUpScheduler;
    private Scheduler mDownScheduler;

    public RxSchedulersTransformer(Scheduler up, Scheduler down)
    {
        this.mUpScheduler = up;
        this.mDownScheduler = down;
    }

    @Override
    public CompletableSource apply(Completable upstream)
    {
        return upstream.subscribeOn(mUpScheduler).observeOn(mDownScheduler);
    }

    @Override
    public Publisher<T> apply(Flowable<T> upstream)
    {
        return upstream.subscribeOn(mUpScheduler).observeOn(mDownScheduler);
    }

    @Override
    public MaybeSource<T> apply(Maybe<T> upstream)
    {
        return upstream.subscribeOn(mUpScheduler).observeOn(mDownScheduler);
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream)
    {
        return upstream.subscribeOn(mUpScheduler).observeOn(mDownScheduler);
    }

    @Override
    public SingleSource<T> apply(Single<T> upstream)
    {
        return upstream.subscribeOn(mUpScheduler).observeOn(mDownScheduler);
    }
}
