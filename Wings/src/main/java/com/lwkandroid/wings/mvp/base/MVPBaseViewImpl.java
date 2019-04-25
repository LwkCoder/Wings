package com.lwkandroid.wings.mvp.base;

import android.app.Activity;

import com.lwkandroid.wings.log.KLog;
import com.lwkandroid.wings.rx.lifecycle.RxLifeCyclePublisherImpl;
import com.lwkandroid.wings.utils.ReflectUtils;
import com.lwkandroid.wings.utils.ToastUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by LWK
 * MVP模版中View层实现类
 * 【实现最基础的一些方法】
 */
public class MVPBaseViewImpl implements IMVPBaseView
{
    private RxLifeCyclePublisherImpl mLifeCycleImpl = new RxLifeCyclePublisherImpl();
    private Activity mActivity;

    void attachToActivity(Activity activity)
    {
        this.mActivity = activity;
    }

    <P> P createPresenter(Class viewClass)
    {
        try
        {
            Type superType = viewClass.getGenericSuperclass();
            if (superType instanceof ParameterizedType)
            {
                ParameterizedType pt = (ParameterizedType) superType;
                Type[] types = pt.getActualTypeArguments();
                if (types.length > 0)
                {
                    Class pClass = (Class) types[0];
                    P presenter = ReflectUtils.reflect(pClass).newInstance().get();
                    KLog.d("Create Presenter success: " + presenter.getClass().getSimpleName());
                    return presenter;
                    //NOTICE ：不能通过下面的方式反射
                    //否则会报错：java.lang.SecurityException: Can not make a java.lang.Class constructor accessible
                    //                    return ReflectUtils.reflect(types[0]).newInstance().get();
                }
            } else
            {
                KLog.w("Can not reflect INSTANCE of Presenter: can not get super class ParameterizedType.");
            }
        } catch (Exception e)
        {
            KLog.w("Can not reflect INSTANCE of Presenter:" + e.toString());
        }
        return (P) new DefaultMVPPresenter();
    }

    @Override
    public Activity getAttachedActivity()
    {
        return mActivity;
    }

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
