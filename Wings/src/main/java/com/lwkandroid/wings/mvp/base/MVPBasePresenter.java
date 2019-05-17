package com.lwkandroid.wings.mvp.base;

import com.lwkandroid.wings.rx.lifecycle.IRxLifeCycleOperator;
import com.lwkandroid.wings.rx.lifecycle.RxLifeCycleEvent;
import com.lwkandroid.wings.rx.lifecycle.RxLifeCycleOperatorImpl;

import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by LWK
 * MVP模版中Presenter基类
 *
 * @author LWK
 */
public abstract class MVPBasePresenter<V extends IMVPBaseView, M> implements IMVPBasePresenter<V, M>
{
    private V mView;
    private M mModel;
    private IRxLifeCycleOperator<V> mRxLifeCycleOperatorImpl;

    public MVPBasePresenter()
    {
        mRxLifeCycleOperatorImpl = new RxLifeCycleOperatorImpl<>();
    }

    @Override
    public void attachWithView(V view)
    {
        this.mView = view;
        this.mModel = createModel();
        attachWithPublisher(view);
    }

    @Override
    public void attachWithPublisher(V publisher)
    {
        mRxLifeCycleOperatorImpl.attachWithPublisher(publisher);
    }

    @Override
    public V getPublisher()
    {
        return mRxLifeCycleOperatorImpl.getPublisher();
    }

    /**
     * 创建Model层对象
     */
    @Override
    public abstract M createModel();

    @Override
    public V getViewImpl()
    {
        return mView;
    }

    @Override
    public M getModelImpl()
    {
        return mModel;
    }

    @Override
    public CompositeDisposable getCompositeDisposable()
    {
        return mRxLifeCycleOperatorImpl.getCompositeDisposable();
    }

    @Override
    public void addComposites(Disposable... disposable)
    {
        mRxLifeCycleOperatorImpl.addComposites(disposable);
    }

    @Override
    public void clearComposites()
    {
        mRxLifeCycleOperatorImpl.clearComposites();
    }

    /**
     * 线程绑定IO到Main，并监听生命周期OnDestroy
     */
    @Override
    public <T> ObservableTransformer<T, T> applyIo2MainUntilViewDestroy()
    {
        return mRxLifeCycleOperatorImpl.applyIo2MainUntilViewDestroy();
    }

    /**
     * 线程绑定IO到Main，并监听指定生命周期
     */
    @Override
    public <T> ObservableTransformer<T, T> applyIo2MainUntilLifeCycle(@RxLifeCycleEvent final int target)
    {
        return mRxLifeCycleOperatorImpl.applyIo2MainUntilLifeCycle(target);
    }

    /**
     * 线程绑定Computation到Main，并监听生命周期OnDestroy
     */
    @Override
    public <T> ObservableTransformer<T, T> applyComputation2MainUntilViewDestroy()
    {
        return mRxLifeCycleOperatorImpl.applyComputation2MainUntilViewDestroy();
    }

    /**
     * 线程绑定Computation到Main，并监听指定生命周期
     */
    @Override
    public <T> ObservableTransformer<T, T> applyComputation2MainUntilLifeCycle(@RxLifeCycleEvent final int target)
    {
        return mRxLifeCycleOperatorImpl.applyComputation2MainUntilLifeCycle(target);
    }

    /**
     * 线程绑定NewThread到Main，并监听生命周期OnDestroy
     */
    @Override
    public <T> ObservableTransformer<T, T> applyNewThread2MainUntilViewDestroy()
    {
        return mRxLifeCycleOperatorImpl.applyNewThread2MainUntilViewDestroy();
    }

    /**
     * 线程绑定NewThread到Main，并监听指定生命周期
     */
    @Override
    public <T> ObservableTransformer<T, T> applyNewThread2MainUntilLifeCycle(@RxLifeCycleEvent final int target)
    {
        return mRxLifeCycleOperatorImpl.applyNewThread2MainUntilLifeCycle(target);
    }

    @Override
    public void onDestroyPresenter()
    {
        mRxLifeCycleOperatorImpl.clearComposites();
        if (mView != null)
        {
            mView = null;
        }
        if (mModel != null)
        {
            mModel = null;
        }
    }
}
