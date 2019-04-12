package com.lwkandroid.wings.mvp.base;

import com.lwkandroid.wings.utils.ToastUtils;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.SingleSubject;

/**
 * Created by LWK
 * TODO MVP模版中View层实现类
 * 【实现最基础的一些方法】
 */
public class MVPBaseViewImpl implements IMVPBaseView
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
    public PublishSubject<Integer> getLifecyclePublishSubject()
    {
        return LIFECYCLE_PUBLISH_SUBJECT;
    }

    @Override
    public SingleSubject<Integer> getLifecycleSingleSubject()
    {
        return LIFECYCLE_SINGLE_SUBJECT;
    }
}
