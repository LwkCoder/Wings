package com.lwkandroid.wings.mvp.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lwkandroid.wings.rx.lifecycle.RxLifeCycleConstants;
import com.lwkandroid.wings.rx.lifecycle.RxLifeCycleEvent;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by LWK
 * Activity基类
 */

public abstract class WingsBaseActivity<P extends MVPBasePresenter> extends AppCompatActivity implements
        IContentView, IMVPBaseView, ContentViewImpl.onClickListenerDispatcher, View.OnClickListener
{
    private final String TAG = getClass().getSimpleName();
    private MVPBaseViewImpl<P> mMVPViewImpl = new MVPBaseViewImpl<P>();
    private ContentViewImpl mContentViewImpl = new ContentViewImpl(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        setBarColor();
        super.onCreate(savedInstanceState);
        mMVPViewImpl.createPresenterAndAttachView(this);
        getIntentData(getIntent(), false);
        setContentView(mContentViewImpl.inflateContentView(this, getContentViewId()));
        initUI(getContentView());
        initData(savedInstanceState);
        publishLifeCycleEvent(RxLifeCycleConstants.ON_CREATE);
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
        publishLifeCycleEvent(RxLifeCycleConstants.ON_START);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        publishLifeCycleEvent(RxLifeCycleConstants.ON_RESUME);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        publishLifeCycleEvent(RxLifeCycleConstants.ON_PAUSE);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        publishLifeCycleEvent(RxLifeCycleConstants.ON_STOP);
    }

    @Override
    protected void onDestroy()
    {
        publishLifeCycleEvent(RxLifeCycleConstants.ON_DESTROY);
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
    public void onClick(View v)
    {
        onClick(v.getId(), v);
    }

    @Override
    public PublishSubject<Integer> getLifeCycleSubject()
    {
        return mMVPViewImpl.getLifeCycleSubject();
    }

    @Override
    public void publishLifeCycleEvent(@RxLifeCycleEvent Integer lifeCycleEvent)
    {
        mMVPViewImpl.publishLifeCycleEvent(lifeCycleEvent);
    }

    @Override
    public P getPresenter()
    {
        return mMVPViewImpl.getPresenter();
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
