package com.lwkandroid.wings.mvp.base;

import com.lwkandroid.wings.rx.constant.RxLifeCycle;
import com.lwkandroid.wings.rx.utils.RxSchedulers;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;

/**
 * Created by LWK
 * TODO MVP模版中Presenter基类
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

    @Override
    public Observable<Integer> withRxLifecycleForObservable(@RxLifeCycle.Event final int target)
    {
        return getViewImpl().getLifecyclePublishSubject().filter(new Predicate<Integer>()
        {
            @Override
            public boolean test(@NonNull Integer integer) throws Exception
            {
                return integer == target;
            }
        }).take(1);
    }

    /**
     * 线程绑定IO到Main，并监听生命周期OnDestroy
     */
    @Override
    public <T> ObservableTransformer<T, T> applyIo2MainWithLifeCycle()
    {
        return applyIo2MainWithLifeCycle(RxLifeCycle.ON_DESTROY);
    }

    /**
     * 线程绑定IO到Main，并监听指定生命周期
     */
    @Override
    public <T> ObservableTransformer<T, T> applyIo2MainWithLifeCycle(@RxLifeCycle.Event final int target)
    {
        return new ObservableTransformer<T, T>()
        {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream)
            {
                return upstream.compose(RxSchedulers.<T>applyIo2Main())
                        .takeUntil(withRxLifecycleForObservable(target));
            }
        };
    }

    /**
     * 线程绑定Computation到Main，并监听生命周期OnDestroy
     */
    @Override
    public <T> ObservableTransformer<T, T> applyComputation2MainWithLifeCycle()
    {
        return applyComputation2MainWithLifeCycle(RxLifeCycle.ON_DESTROY);
    }

    /**
     * 线程绑定Computation到Main，并监听指定生命周期
     */
    @Override
    public <T> ObservableTransformer<T, T> applyComputation2MainWithLifeCycle(@RxLifeCycle.Event final int target)
    {
        return new ObservableTransformer<T, T>()
        {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream)
            {
                return upstream.compose(RxSchedulers.<T>applyComputation2Main())
                        .takeUntil(withRxLifecycleForObservable(target));
            }
        };
    }

    /**
     * 线程绑定NewThread到Main，并监听生命周期OnDestroy
     */
    @Override
    public <T> ObservableTransformer<T, T> applyNewThread2MainWithLifeCycle()
    {
        return applyNewThread2MainWithLifeCycle(RxLifeCycle.ON_DESTROY);
    }

    /**
     * 线程绑定NewThread到Main，并监听指定生命周期
     */
    @Override
    public <T> ObservableTransformer<T, T> applyNewThread2MainWithLifeCycle(@RxLifeCycle.Event final int target)
    {
        return new ObservableTransformer<T, T>()
        {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream)
            {
                return upstream.compose(RxSchedulers.<T>applyNewThread2Main())
                        .takeUntil(withRxLifecycleForObservable(target));
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
