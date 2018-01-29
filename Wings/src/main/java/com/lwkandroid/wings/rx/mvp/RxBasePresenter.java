package com.lwkandroid.wings.rx.mvp;

import com.lwkandroid.wings.mvp.BasePresenter;
import com.lwkandroid.wings.rx.constant.RxLifecycle;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;

/**
 * RX MVP模版中Presenter基类
 */

public abstract class RxBasePresenter<V extends IRxBaseView, M> extends BasePresenter<V, M>
{
    protected CompositeDisposable mCompositeDisposable;

    @Override
    public void attachToView(V v)
    {
        super.attachToView(v);
        mCompositeDisposable = new CompositeDisposable();
    }

    //统一管理所有Composites
    protected void addComposites(Disposable... disposable)
    {
        mCompositeDisposable.addAll(disposable);
    }

    /**
     * 提供生命周期自动解绑，用于配合takeUntil操作符
     */
    protected Observable<Integer> withRxLifecycle()
    {
        return withRxLifecycle(RxLifecycle.ON_DESTROY);
    }

    /**
     * 提供生命周期自动解绑，用于配合takeUntil操作符
     *
     * @param target 某个生命周期事件(RxLifecycle)
     */
    protected Observable<Integer> withRxLifecycle(@RxLifecycle.Event final int target)
    {
        return mViewImpl.getLifecycleSubject().filter(new Predicate<Integer>()
        {
            @Override
            public boolean test(@NonNull Integer integer) throws Exception
            {
                return integer == target;
            }
        }).take(1);
    }

    @Override
    public void onDestory()
    {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed())
            mCompositeDisposable.dispose();
        mCompositeDisposable = null;
        super.onDestory();
    }
}
