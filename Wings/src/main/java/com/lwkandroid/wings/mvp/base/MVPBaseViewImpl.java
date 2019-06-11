package com.lwkandroid.wings.mvp.base;

import com.lwkandroid.wings.log.KLog;
import com.lwkandroid.wings.rx.lifecycle.IRxLifeCyclePublisher;
import com.lwkandroid.wings.rx.lifecycle.RxLifeCyclePublisherImpl;
import com.lwkandroid.wings.utils.ReflectUtils;
import com.lwkandroid.wings.utils.ToastUtils;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.fragment.app.FragmentActivity;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by LWK
 * MVP模版中View层实现类
 * 【实现最基础的一些方法】
 *
 * @author LWK
 */
public class MVPBaseViewImpl<P extends MVPBasePresenter> implements IMVPBaseView
{
    private IRxLifeCyclePublisher mLifeCyclePublisherImpl = new RxLifeCyclePublisherImpl();
    private WeakReference<FragmentActivity> mActivityReference;

    MVPBaseViewImpl(FragmentActivity activity)
    {
        this.mActivityReference = new WeakReference<>(activity);
    }

    private P mPresenter;

    /**
     * 创建Presenter、关联View层对象
     *
     * @param view MVP View层对象
     */
    void createPresenterAndAttachView(IMVPBaseView view)
    {
        try
        {
            Class viewClass = view.getClass();
            Type superType = viewClass.getGenericSuperclass();
            if (superType instanceof ParameterizedType)
            {
                ParameterizedType pt = (ParameterizedType) superType;
                Type[] types = pt.getActualTypeArguments();
                if (types.length > 0)
                {
                    Class pClass = (Class) types[0];
                    mPresenter = ReflectUtils.reflect(pClass).newInstance().get();
                    mPresenter.attachWithView(view);
                    KLog.d("Create Presenter success: " + mPresenter.getClass().getSimpleName());
                }
            } else
            {
                KLog.w("Can not reflect INSTANCE of Presenter: can not get super class ParameterizedType.");
            }
        } catch (Exception e)
        {
            KLog.w("Can not reflect INSTANCE of Presenter:" + e.toString());
        }
    }

    @Override
    public FragmentActivity getFragmentActivity()
    {
        return mActivityReference.get();
    }

    @Override
    public P getPresenter()
    {
        return mPresenter;
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
        return mLifeCyclePublisherImpl.getLifeCycleSubject();
    }

    @Override
    public void publishLifeCycleEvent(Integer lifeCycleEvent)
    {
        mLifeCyclePublisherImpl.publishLifeCycleEvent(lifeCycleEvent);
    }
}
