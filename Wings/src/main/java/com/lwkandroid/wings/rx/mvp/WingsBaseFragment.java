package com.lwkandroid.wings.rx.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lwkandroid.wings.rx.constant.RxLifecycle;
import com.lwkandroid.wings.utils.ReflectUtils;
import com.socks.library.KLog;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by LWK
 * TODO Fragment基类
 */

public abstract class WingsBaseFragment<P extends MVPBasePresenter> extends Fragment implements
        IContentView, IMVPBaseView, ContentViewImpl.onClickListenerDispatcher
        , View.OnClickListener
{
    private final String TAG = getClass().getSimpleName();
    private P mPresenter;
    private MVPBaseViewImpl mMVPViewImpl = new MVPBaseViewImpl();
    private ContentViewImpl mContentViewImpl = new ContentViewImpl(this);

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        getLifecycleSubject().onNext(RxLifecycle.ON_ATTACH);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getLifecycleSubject().onNext(RxLifecycle.ON_CREATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mContentViewImpl.inflateContentView(inflater, getContentViewId(), container);
        getLifecycleSubject().onNext(RxLifecycle.ON_CREATE_VIEW);
        return mContentViewImpl.getContentView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        getArgumentsData(getArguments(), savedInstanceState);
        mPresenter = createPresenter();
        if (getPresenter() != null)
            getPresenter().attachWithView(this);
        initUI(getContentView());
        initData(savedInstanceState);
        getLifecycleSubject().onNext(RxLifecycle.ON_ACTIVITY_CREATED);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        getLifecycleSubject().onNext(RxLifecycle.ON_START);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        getLifecycleSubject().onNext(RxLifecycle.ON_RESUME);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        getLifecycleSubject().onNext(RxLifecycle.ON_PAUSE);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        getLifecycleSubject().onNext(RxLifecycle.ON_STOP);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        getLifecycleSubject().onNext(RxLifecycle.ON_DESTROY_VIEW);
    }

    @Override
    public void onDestroy()
    {
        getLifecycleSubject().onNext(RxLifecycle.ON_DESTROY);
        if (getPresenter() != null)
            getPresenter().onDestoryPresenter();
        super.onDestroy();
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        getLifecycleSubject().onNext(RxLifecycle.ON_DETACH);
    }

    @Override
    public View getContentView()
    {
        return mContentViewImpl.getContentView();
    }

    @Override
    public <T extends View> T find(int resId)
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
    public PublishSubject<Integer> getLifecycleSubject()
    {
        return mMVPViewImpl.getLifecycleSubject();
    }

    @Override
    public void onClick(View v)
    {
        onClick(v.getId(), v);
    }

    //反射实例化Presenter
    protected P createPresenter()
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
                KLog.w(TAG, "Can not reflect instance of Presenter: can not get super class ParameterizedType.");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            KLog.w(TAG, "Can not reflect instance of Presenter:" + e.toString());
        }

        return (P) new DefaultMVPPresenter();
    }

    //获取Presenter对象
    protected P getPresenter()
    {
        return mPresenter;
    }

    /**
     * 子类实现，指定布局id
     */
    protected abstract int getContentViewId();


    /**
     * 子类实现，获取getArgument()传递的数据
     *
     * @param bundle             argument数据
     * @param savedInstanceState
     */
    protected abstract void getArgumentsData(Bundle bundle, Bundle savedInstanceState);

    /**
     * 子类实现此方法初始化UI
     */
    protected abstract void initUI(View contentView);

    /**
     * 子类实现此方法初始化数据
     */
    protected abstract void initData(@Nullable Bundle savedInstanceState);
}
