package com.lwkandroid.lib.core.java.rx.life;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by LWK
 * TODO 执行生命周期Lifecycle自动绑定的方法
 * 2020/2/12
 */
public class RxLifeProvider implements LifecycleEventObserver
{
    private PublishSubject<Lifecycle.Event> mEventSubject = PublishSubject.create();
    private LifecycleOwner mLifecycleOwner;

    public RxLifeProvider(LifecycleOwner owner)
    {
        this.mLifecycleOwner = owner;
        mLifecycleOwner.getLifecycle().addObserver(this);
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event)
    {
        mEventSubject.onNext(event);
        if (mLifecycleOwner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED)
            release();
    }

    private void release()
    {
        mEventSubject.onComplete();
        mLifecycleOwner.getLifecycle().removeObserver(this);
    }

    public <T> RxLifeTransformer<T> bindUtilEvent(Lifecycle.Event event)
    {
        return new RxLifeTransformer<T>(getBindUtilObservable(event));
    }

    public <T> RxLifeTransformer<T> bindUtilOnDestory()
    {
        return new RxLifeTransformer<T>(getBindUtilObservable(Lifecycle.Event.ON_DESTROY));
    }

    private Observable<Lifecycle.Event> getBindUtilObservable(Lifecycle.Event lifeEvent)
    {
        return mEventSubject.filter(event -> event.equals(lifeEvent));
    }

}
