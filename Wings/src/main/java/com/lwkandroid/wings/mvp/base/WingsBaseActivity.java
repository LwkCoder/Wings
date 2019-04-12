package com.lwkandroid.wings.mvp.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lwkandroid.wings.log.KLog;
import com.lwkandroid.wings.rx.constant.RxLifecycle;
import com.lwkandroid.wings.utils.ReflectUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.SingleSubject;

/**
 * Created by LWK
 * TODO Activity基类
 */

public abstract class WingsBaseActivity<P extends MVPBasePresenter> extends AppCompatActivity implements
        IContentView, IMVPBaseView, ContentViewImpl.onClickListenerDispatcher, View.OnClickListener
{
    private final String TAG = getClass().getSimpleName();
    private P mPresenter;
    private MVPBaseViewImpl mMVPViewImpl = new MVPBaseViewImpl();
    private ContentViewImpl mContentViewImpl = new ContentViewImpl(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        setBarColor();
        super.onCreate(savedInstanceState);
        getLifecyclePublishSubject().onNext(RxLifecycle.ON_CREATE);
        getLifecycleSingleSubject().onSuccess(RxLifecycle.ON_CREATE);
        getIntentData(getIntent(), false);
        setContentView(mContentViewImpl.inflateContentView(this, getContentViewId()));
        mPresenter = createPresenter();
        if (getPresenter() != null)
        {
            getPresenter().attachWithView(this);
        }
        initUI(getContentView());
        initData(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        getIntentData(intent, true);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        getLifecyclePublishSubject().onNext(RxLifecycle.ON_START);
        getLifecycleSingleSubject().onSuccess(RxLifecycle.ON_START);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        getLifecyclePublishSubject().onNext(RxLifecycle.ON_RESUME);
        getLifecycleSingleSubject().onSuccess(RxLifecycle.ON_RESUME);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        getLifecyclePublishSubject().onNext(RxLifecycle.ON_PAUSE);
        getLifecycleSingleSubject().onSuccess(RxLifecycle.ON_PAUSE);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        getLifecyclePublishSubject().onNext(RxLifecycle.ON_STOP);
        getLifecycleSingleSubject().onSuccess(RxLifecycle.ON_STOP);
    }

    @Override
    protected void onDestroy()
    {
        getLifecyclePublishSubject().onNext(RxLifecycle.ON_DESTROY);
        getLifecycleSingleSubject().onSuccess(RxLifecycle.ON_DESTROY);
        if (getPresenter() != null)
        {
            getPresenter().onDestroyPresenter();
        }
        super.onDestroy();
    }

    @Override
    public View getContentView()
    {
        return mContentViewImpl.getContentView();
    }

    @Override
    public <T extends View> T find(@IdRes int resId)
    {
        return mContentViewImpl.find(resId);
    }

    @Override
    public void addClick(int resId)
    {
        mContentViewImpl.addClick(resId);
    }

    @Override
    public void addClick(int resId, View.OnClickListener listener)
    {
        mContentViewImpl.addClick(resId, listener);
    }

    @Override
    public void addClick(int... resIds)
    {
        mContentViewImpl.addClick(resIds);
    }

    @Override
    public void addClick(View view)
    {
        mContentViewImpl.addClick(view);
    }

    @Override
    public void addClick(View view, View.OnClickListener listener)
    {
        mContentViewImpl.addClick(view, listener);
    }

    @Override
    public void addClick(View... views)
    {
        mContentViewImpl.addClick(views);
    }

    @Override
    public void showShortToast(int resId)
    {
        mMVPViewImpl.showShortToast(resId);
    }

    @Override
    public void showShortToast(CharSequence message)
    {
        mMVPViewImpl.showShortToast(message);
    }

    @Override
    public void showLongToast(int resId)
    {
        mMVPViewImpl.showLongToast(resId);
    }

    @Override
    public void showLongToast(CharSequence message)
    {
        mMVPViewImpl.showLongToast(message);
    }

    @Override
    public PublishSubject<Integer> getLifecyclePublishSubject()
    {
        return mMVPViewImpl.getLifecyclePublishSubject();
    }

    @Override
    public SingleSubject<Integer> getLifecycleSingleSubject()
    {
        return mMVPViewImpl.getLifecycleSingleSubject();
    }

    @Override
    public void onClick(View v)
    {
        onClick(v.getId(), v);
    }

    //反射实例化Presenter
    private P createPresenter()
    {
        try
        {
            Type superType = this.getClass().getGenericSuperclass();
            if (superType instanceof ParameterizedType)
            {
                ParameterizedType pt = (ParameterizedType) superType;
                Type[] types = pt.getActualTypeArguments();
                if (types != null && types.length > 0)
                {
                    Class pClass = (Class) types[0];
                    return ReflectUtils.reflect(pClass).newInstance().get();
                    //NOTICE ：不能通过下面的方式反射
                    //否则会报错：java.lang.SecurityException: Can not make a java.lang.Class constructor accessible
                    //                    return ReflectUtils.reflect(types[0]).newInstance().get();
                }
            } else
            {
                KLog.w(TAG, "Can not reflect INSTANCE of Presenter: can not get super class ParameterizedType.");
            }
        } catch (Exception e)
        {
            KLog.w(TAG, "Can not reflect INSTANCE of Presenter:" + e.toString());
        }

        return (P) new DefaultMVPPresenter();
    }

    //获取Presenter对象
    protected P getPresenter()
    {
        return mPresenter;
    }

    /**
     * 子类实现，设置状态栏/导航栏
     */
    protected abstract void setBarColor();

    /**
     * 子类实现，接收Intent数据
     *
     * @param intent    传递的Intent
     * @param newIntent 是否onNewIntent()调用
     */
    protected abstract void getIntentData(Intent intent, boolean newIntent);

    /**
     * 子类实现，指定布局id
     */
    protected abstract int getContentViewId();

    /**
     * 子类实现，初始化UI
     *
     * @param contentView 内容布局
     */
    protected abstract void initUI(View contentView);

    /**
     * 子类实现，初始化数据
     *
     * @param savedInstanceState
     */
    protected abstract void initData(Bundle savedInstanceState);

}
