package com.lwkandroid.wings.mvp.base;

import com.lwkandroid.wings.rx.lifecycle.RxLifeCycleEvent;

import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by LWK
 *  MVP模版中Presenter接口
 */

public interface IMVPBasePresenter<V extends IMVPBaseView, M>
{
    void attachWithView(V view);

    M createModel();

    V getViewImpl();

    M getModelImpl();

    CompositeDisposable getCompositeDisposable();

    void addComposites(Disposable... disposable);

    <T> ObservableTransformer<T, T> applyIo2MainUntilViewDestroy();

    <T> ObservableTransformer<T, T> applyIo2MainUntilLifeCycle(@RxLifeCycleEvent final int target);

    <T> ObservableTransformer<T, T> applyComputation2MainUntilViewDestroy();

    <T> ObservableTransformer<T, T> applyComputation2MainUntilLifeCycle(@RxLifeCycleEvent final int target);

    <T> ObservableTransformer<T, T> applyNewThread2MainUntilViewDestroy();

    <T> ObservableTransformer<T, T> applyNewThread2MainUntilLifeCycle(@RxLifeCycleEvent final int target);

    void onDestroyPresenter();
}
