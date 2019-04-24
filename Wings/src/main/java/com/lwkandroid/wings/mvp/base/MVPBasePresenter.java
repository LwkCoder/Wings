package com.lwkandroid.wings.mvp.base;

import com.lwkandroid.wings.rx.lifecycle.RxLifeCycle;
import com.lwkandroid.wings.rx.lifecycle.RxLifeCycleConstants;
import com.lwkandroid.wings.rx.lifecycle.RxLifeCycleEvent;
import com.lwkandroid.wings.rx.schedulers.RxSchedulers;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by LWK
 * MVP模版中Presenter基类
 */
public abstract class MVPBasePresenter<V extends IMVPBaseView, M> implements IMVPBasePresenter<V, M>
{
    private V mView;
    private M mModel;
    private CompositeDisposable mCompositeDisposable;

    public MVPBasePresenter()
    {

    }

    @Override
    public void attachWithView(V view)
    {
        this.mView = view;
        this.mModel = createModel();
        mCompositeDisposable = new CompositeDisposable();
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
        return mCompositeDisposable;
    }

    @Override
    public void addComposites(Disposable... disposable)
    {
        if (mCompositeDisposable != null)
        {
            mCompositeDisposable.addAll(disposable);
        }
    }

    /**
     * 线程绑定IO到Main，并监听生命周期OnDestroy
     */
    @Override
    public <T> ObservableTransformer<T, T> applyIo2MainUntilLifeCycle()
    {
        return applyIo2MainUntilLifeCycle(RxLifeCycleConstants.ON_DESTROY);
    }

    /**
     * 线程绑定IO到Main，并监听指定生命周期
     */
    @Override
    public <T> ObservableTransformer<T, T> applyIo2MainUntilLifeCycle(@RxLifeCycleEvent final int target)
    {
        return new ObservableTransformer<T, T>()
        {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream)
            {
                return upstream
                        .compose(RxSchedulers.<T>applyIo2Main())
                        .compose(RxLifeCycle.<T>bindUntilEvent(getViewImpl().getLifeCycleSubject(), target));
            }
        };
    }

    /**
     * 线程绑定Computation到Main，并监听生命周期OnDestroy
     */
    @Override
    public <T> ObservableTransformer<T, T> applyComputation2MainUntilLifeCycle()
    {
        return applyComputation2MainUntilLifeCycle(RxLifeCycleConstants.ON_DESTROY);
    }

    /**
     * 线程绑定Computation到Main，并监听指定生命周期
     */
    @Override
    public <T> ObservableTransformer<T, T> applyComputation2MainUntilLifeCycle(@RxLifeCycleEvent final int target)
    {
        return new ObservableTransformer<T, T>()
        {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream)
            {
                return upstream
                        .compose(RxSchedulers.<T>applyComputation2Main())
                        .compose(RxLifeCycle.<T>bindUntilEvent(getViewImpl().getLifeCycleSubject(), target));
            }
        };
    }

    /**
     * 线程绑定NewThread到Main，并监听生命周期OnDestroy
     */
    @Override
    public <T> ObservableTransformer<T, T> applyNewThread2MainUntilLifeCycle()
    {
        return applyNewThread2MainUntilLifeCycle(RxLifeCycleConstants.ON_DESTROY);
    }

    /**
     * 线程绑定NewThread到Main，并监听指定生命周期
     */
    @Override
    public <T> ObservableTransformer<T, T> applyNewThread2MainUntilLifeCycle(@RxLifeCycleEvent final int target)
    {
        return new ObservableTransformer<T, T>()
        {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream)
            {
                return upstream
                        .compose(RxSchedulers.<T>applyNewThread2Main())
                        .compose(RxLifeCycle.<T>bindUntilEvent(getViewImpl().getLifeCycleSubject(), target));
            }
        };
    }

    @Override
    public void onDestroyPresenter()
    {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed())
        {
            mCompositeDisposable.dispose();
            mCompositeDisposable = null;
        }
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
