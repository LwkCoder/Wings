package com.lwkandroid.wings.mvp.base;

import com.lwkandroid.wings.rx.constant.RxLifecycle;
import com.lwkandroid.wings.rx.utils.RxSchedulers;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
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
    public Observable<Integer> withRxLifecycleForObservable()
    {
        return withRxLifecycleForObservable(RxLifecycle.ON_DESTROY);
    }

    @Override
    public Observable<Integer> withRxLifecycleForObservable(@RxLifecycle.Event final int target)
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

    @Override
    public Single<Integer> withRxLifecycleForSingle()
    {
        return withRxLifecycleForSingle(RxLifecycle.ON_DESTROY);
    }

    @Override
    public Single<Integer> withRxLifecycleForSingle(@RxLifecycle.Event final int target)
    {
        return getViewImpl().getLifecycleSingleSubject().filter(new Predicate<Integer>()
        {
            @Override
            public boolean test(Integer integer) throws Exception
            {
                return integer == target;
            }
        }).flatMapSingle(new Function<Integer, SingleSource<Integer>>()
        {
            @Override
            public SingleSource<Integer> apply(Integer integer) throws Exception
            {
                return Single.just(integer);
            }
        });
    }

    /**
     * 线程绑定IO到Main，并监听生命周期OnDestroy
     */
    @Override
    public <T> ObservableTransformer<T, T> applyIo2MainAsObservableWithLifeCycle()
    {
        return applyIo2MainAsObservableWithLifeCycle(RxLifecycle.ON_DESTROY);
    }

    /**
     * 线程绑定IO到Main，并监听指定生命周期
     */
    @Override
    public <T> ObservableTransformer<T, T> applyIo2MainAsObservableWithLifeCycle(@RxLifecycle.Event final int target)
    {
        return new ObservableTransformer<T, T>()
        {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream)
            {
                return upstream.compose(RxSchedulers.<T>applyIo2MainAsObservable())
                        .takeUntil(withRxLifecycleForObservable(target));
            }
        };
    }

    @Override
    public <T> SingleTransformer<T, T> applyIo2MainAsSingleWithLifeCycle()
    {
        return applyIo2MainAsSingleWithLifeCycle(RxLifecycle.ON_DESTROY);
    }

    @Override
    public <T> SingleTransformer<T, T> applyIo2MainAsSingleWithLifeCycle(@RxLifecycle.Event final int target)
    {
        return new SingleTransformer<T, T>()
        {
            @Override
            public SingleSource<T> apply(Single<T> upstream)
            {
                return upstream.compose(RxSchedulers.<T>applyIo2MainAsSingle())
                        .takeUntil(withRxLifecycleForSingle(target));
            }
        };
    }

    /**
     * 线程绑定Computation到Main，并监听生命周期OnDestroy
     */
    @Override
    public <T> ObservableTransformer<T, T> applyComputation2MainAsObservableWithLifeCycle()
    {
        return applyComputation2MainAsObservableWithLifeCycle(RxLifecycle.ON_DESTROY);
    }

    /**
     * 线程绑定Computation到Main，并监听指定生命周期
     */
    @Override
    public <T> ObservableTransformer<T, T> applyComputation2MainAsObservableWithLifeCycle(@RxLifecycle.Event final int target)
    {
        return new ObservableTransformer<T, T>()
        {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream)
            {
                return upstream.compose(RxSchedulers.<T>applyComputation2MainAsObservable())
                        .takeUntil(withRxLifecycleForObservable(target));
            }
        };
    }

    @Override
    public <T> SingleTransformer<T, T> applyComputation2MainAsSingleWithLifeCycle()
    {
        return applyComputation2MainAsSingleWithLifeCycle(RxLifecycle.ON_DESTROY);
    }

    @Override
    public <T> SingleTransformer<T, T> applyComputation2MainAsSingleWithLifeCycle(@RxLifecycle.Event final int target)
    {
        return new SingleTransformer<T, T>()
        {
            @Override
            public SingleSource<T> apply(Single<T> upstream)
            {
                return upstream.compose(RxSchedulers.<T>applyComputation2MainAsSingle())
                        .takeUntil(withRxLifecycleForSingle(target));
            }
        };
    }

    /**
     * 线程绑定NewThread到Main，并监听生命周期OnDestroy
     */
    @Override
    public <T> ObservableTransformer<T, T> applyNewThread2MainAsObservableWithLifeCycle()
    {
        return applyNewThread2MainAsObservableWithLifeCycle(RxLifecycle.ON_DESTROY);
    }

    /**
     * 线程绑定NewThread到Main，并监听指定生命周期
     */
    @Override
    public <T> ObservableTransformer<T, T> applyNewThread2MainAsObservableWithLifeCycle(@RxLifecycle.Event final int target)
    {
        return new ObservableTransformer<T, T>()
        {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream)
            {
                return upstream.compose(RxSchedulers.<T>applyNewThread2MainAsObservable())
                        .takeUntil(withRxLifecycleForObservable(target));
            }
        };
    }

    @Override
    public <T> SingleTransformer<T, T> applyNewThread2MainAsSingleWithLifeCycle()
    {
        return applyNewThread2MainAsSingleWithLifeCycle(RxLifecycle.ON_DESTROY);
    }

    @Override
    public <T> SingleTransformer<T, T> applyNewThread2MainAsSingleWithLifeCycle(@RxLifecycle.Event final int target)
    {
        return new SingleTransformer<T, T>()
        {
            @Override
            public SingleSource<T> apply(Single<T> upstream)
            {
                return upstream.compose(RxSchedulers.<T>applyNewThread2MainAsSingle())
                        .takeUntil(withRxLifecycleForSingle(target));
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
