package com.lwkandroid.wings.mvp.base;

import com.lwkandroid.wings.rx.constant.RxLifecycle;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;
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

    Observable<Integer> withRxLifecycleForObservable();

    Single<Integer> withRxLifecycleForSingle();

    Observable<Integer> withRxLifecycleForObservable(@RxLifecycle.Event final int target);

    Single<Integer> withRxLifecycleForSingle(@RxLifecycle.Event final int target);

    <T> ObservableTransformer<T, T> applyIo2MainAsObservableWithLifeCycle();

    <T> ObservableTransformer<T, T> applyIo2MainAsObservableWithLifeCycle(@RxLifecycle.Event final int target);

    <T> SingleTransformer<T, T> applyIo2MainAsSingleWithLifeCycle();

    <T> SingleTransformer<T, T> applyIo2MainAsSingleWithLifeCycle(@RxLifecycle.Event final int target);

    <T> ObservableTransformer<T, T> applyComputation2MainAsObservableWithLifeCycle();

    <T> ObservableTransformer<T, T> applyComputation2MainAsObservableWithLifeCycle(@RxLifecycle.Event final int target);

    <T> SingleTransformer<T, T> applyComputation2MainAsSingleWithLifeCycle();

    <T> SingleTransformer<T, T> applyComputation2MainAsSingleWithLifeCycle(@RxLifecycle.Event final int target);

    <T> ObservableTransformer<T, T> applyNewThread2MainAsObservableWithLifeCycle();

    <T> ObservableTransformer<T, T> applyNewThread2MainAsObservableWithLifeCycle(@RxLifecycle.Event final int target);

    <T> SingleTransformer<T, T> applyNewThread2MainAsSingleWithLifeCycle();

    <T> SingleTransformer<T, T> applyNewThread2MainAsSingleWithLifeCycle(@RxLifecycle.Event final int target);

    void onDestroyPresenter();
}
