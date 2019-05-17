package com.lwkandroid.wings.rx.lifecycle;

import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Description:RX生命周期处理接口
 *
 * @author LWK
 * @date 2019/5/16
 */
public interface IRxLifeCycleOperator<P extends IRxLifeCyclePublisher>
{
    void attachWithPublisher(P publisher);

    P getPublisher();

    CompositeDisposable getCompositeDisposable();

    void addComposites(Disposable... disposable);

    void clearComposites();

    <T> ObservableTransformer<T, T> applyIo2MainUntilViewDestroy();

    <T> ObservableTransformer<T, T> applyIo2MainUntilLifeCycle(@RxLifeCycleEvent final int target);

    <T> ObservableTransformer<T, T> applyComputation2MainUntilViewDestroy();

    <T> ObservableTransformer<T, T> applyComputation2MainUntilLifeCycle(@RxLifeCycleEvent final int target);

    <T> ObservableTransformer<T, T> applyNewThread2MainUntilViewDestroy();

    <T> ObservableTransformer<T, T> applyNewThread2MainUntilLifeCycle(@RxLifeCycleEvent final int target);

}
