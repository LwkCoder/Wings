package com.lwkandroid.wings.rx.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lwkandroid.wings.rx.constant.RxLifecycle;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by LWK
 * TODO Activity基类
 */

public abstract class WingsBaseActivity<P extends MVPBasePresenter> extends AppCompatActivity implements
        IContentView, IMVPBaseView, ContentViewImpl.onClickListenerDispatcher, View.OnClickListener
{
    private P mPresenter;
    private MVPBaseViewImpl mMVPViewImpl = new MVPBaseViewImpl();
    private ContentViewImpl mContentViewImpl = new ContentViewImpl(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        setBarColor();
        super.onCreate(savedInstanceState);
        getIntentData(getIntent(), false);
        setContentView(mContentViewImpl.inflateContentView(this, getContentViewId()));
        mPresenter = createPresenter();
        if (getPresenter() != null)
            getPresenter().attachWithView(this);
        initUI(getContentView());
        initData(savedInstanceState);
        getLifecycleSubject().onNext(RxLifecycle.ON_CREATE);
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
        getLifecycleSubject().onNext(RxLifecycle.ON_START);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        getLifecycleSubject().onNext(RxLifecycle.ON_RESUME);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        getLifecycleSubject().onNext(RxLifecycle.ON_PAUSE);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        getLifecycleSubject().onNext(RxLifecycle.ON_STOP);
    }

    @Override
    protected void onDestroy()
    {
        getLifecycleSubject().onNext(RxLifecycle.ON_DESTROY);
        if (getPresenter() != null)
            getPresenter().onDestoryPresenter();
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
        return (T) mContentViewImpl.find(resId);
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
     * 创建Presenter
     */
    protected abstract P createPresenter();

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
