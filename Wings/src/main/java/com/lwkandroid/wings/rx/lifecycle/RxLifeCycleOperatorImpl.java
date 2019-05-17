package com.lwkandroid.wings.rx.lifecycle;

import com.lwkandroid.wings.rx.schedulers.RxSchedulers;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Description:RX生命周期处理接口实现类
 *
 * @author LWK
 * @date 2019/5/16
 */
public class RxLifeCycleOperatorImpl<P extends IRxLifeCyclePublisher> implements IRxLifeCycleOperator<P>
{
    private P mPublisher;
    private CompositeDisposable mCompositeDisposable;

    public RxLifeCycleOperatorImpl()
    {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachWithPublisher(P publisher)
    {
        mPublisher = publisher;
    }

    @Override
    public P getPublisher()
    {
        return mPublisher;
    }

    @Override
    public CompositeDisposable getCompositeDisposable()
    {
        return mCompositeDisposable;
    }

    @Override
    public void addComposites(Disposable... disposable)
    {
        mCompositeDisposable.addAll(disposable);
    }

    @Override
    public void clearComposites()
    {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed())
        {
            mCompositeDisposable.dispose();
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
    }

    @Override
    public <T> ObservableTransformer<T, T> applyNewThread2MainUntilLifeCycle(final int target)
    {
        return new ObservableTransformer<T, T>()
        {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream)
            {
                return upstream
                        .compose(RxSchedulers.<T>applyNewThread2Main())
                        .compose(RxLifeCycle.<T>bindUntilEvent(getPublisher().getLifeCycleSubject(), target));
            }
        };
    }

    @Override
    public <T> ObservableTransformer<T, T> applyNewThread2MainUntilViewDestroy()
    {
        return applyNewThread2MainUntilLifeCycle(RxLifeCycleConstants.ON_DESTROY);
    }

    @Override
    public <T> ObservableTransformer<T, T> applyComputation2MainUntilLifeCycle(final int target)
    {
        return new ObservableTransformer<T, T>()
        {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream)
            {
                return upstream
                        .compose(RxSchedulers.<T>applyComputation2Main())
                        .compose(RxLifeCycle.<T>bindUntilEvent(getPublisher().getLifeCycleSubject(), target));
            }
        };
    }

    @Override
    public <T> ObservableTransformer<T, T> applyComputation2MainUntilViewDestroy()
    {
        return applyComputation2MainUntilLifeCycle(RxLifeCycleConstants.ON_DESTROY);
    }

    @Override
    public <T> ObservableTransformer<T, T> applyIo2MainUntilLifeCycle(final int target)
    {
        return new ObservableTransformer<T, T>()
        {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream)
            {
                return upstream
                        .compose(RxSchedulers.<T>applyIo2Main())
                        .compose(RxLifeCycle.<T>bindUntilEvent(getPublisher().getLifeCycleSubject(), target));
            }
        };
    }

    @Override
    public <T> ObservableTransformer<T, T> applyIo2MainUntilViewDestroy()
    {
        return applyIo2MainUntilLifeCycle(RxLifeCycleConstants.ON_DESTROY);
    }
}
