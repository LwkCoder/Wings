package com.lwkandroid.wings.rx.mvp;

import com.lwkandroid.wings.utils.ToastUtils;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by LWK
 * TODO MVP模版中View层实现类
 * 【实现最基础的一些方法】
 */
class MVPBaseViewImpl implements IMVPBaseView
{
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
    public PublishSubject<Integer> getLifecycleSubject()
    {
        return LIFECYCLE_SUBJECT;
    }

}
