package com.lwkandroid.wings.mvp.base;

import com.lwkandroid.wings.rx.constant.RxLifeCycle;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by LWK
 * TODO MVP模版中Presenter接口
 */

public interface IMVPBasePresenter<V extends IMVPBaseView, M>
{
    void attachWithView(V view);

    M createModel();

    V getViewImpl();

    M getModelImpl();

    CompositeDisposable getCompositeDisposable();

    void addComposites(Disposable... disposable);

    Observable<Integer> withRxLifecycleForObservable(@RxLifeCycle.Event final int target);

    <T> ObservableTransformer<T, T> applyIo2MainWithLifeCycle();

    <T> ObservableTransformer<T, T> applyIo2MainWithLifeCycle(@RxLifeCycle.Event final int target);

    <T> ObservableTransformer<T, T> applyComputation2MainWithLifeCycle();

    <T> ObservableTransformer<T, T> applyComputation2MainWithLifeCycle(@RxLifeCycle.Event final int target);

    <T> ObservableTransformer<T, T> applyNewThread2MainWithLifeCycle();

    <T> ObservableTransformer<T, T> applyNewThread2MainWithLifeCycle(@RxLifeCycle.Event final int target);

    void onDestroyPresenter();
}
