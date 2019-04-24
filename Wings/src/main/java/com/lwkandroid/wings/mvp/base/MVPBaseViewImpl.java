package com.lwkandroid.wings.mvp.base;

import com.lwkandroid.wings.rx.lifecycle.RxLifeCyclePublisherImpl;
import com.lwkandroid.wings.utils.ToastUtils;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by LWK
 * MVP模版中View层实现类
 * 【实现最基础的一些方法】
 */
public class MVPBaseViewImpl implements IMVPBaseView
{
    private RxLifeCyclePublisherImpl mLifeCycleImpl = new RxLifeCyclePublisherImpl();

    @Override
    public void showShortToast(int resId)
    {
        ToastUtils.showShort(resId);
    }

    @Override
    public void showShortToast(CharSequence message)
    {
        ToastUtils.showShort(message);
    }

    @Override
    public void showLongToast(int resId)
    {
        ToastUtils.showLong(resId);
    }

    @Override
    public void showLongToast(CharSequence message)
    {
        ToastUtils.showLong(message);
    }

    @Override
    public PublishSubject<Integer> getLifeCycleSubject()
    {
        return mLifeCycleImpl.getLifeCycleSubject();
    }

    @Override
    public void publishLifeCycleEvent(Integer lifeCycleEvent)
    {
        mLifeCycleImpl.publishLifeCycleEvent(lifeCycleEvent);
    }
}
