package com.lwkandroid.wings.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lwkandroid.wings.utils.ToastUtils;

/**
 * Created by LWK
 * TODO Activity基类
 * 2017/5/7
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener
{
    protected View mContentView;
    protected ViewFinder mFinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        //执行预操作
        beforeOnCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        //处理Intent
        getIntentData(getIntent(), false);
        //设置ContentView
        if (mContentView == null)
            mContentView = getLayoutInflater().inflate(getContentViewId(), null);
        setContentView(mContentView);
        //初始化UI
        mFinder = new ViewFinder(mContentView);
        initUI(mContentView);
        //初始化数据
        initData(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        getIntentData(intent, true);
    }

    /**
     * 子类可重写此方法在onCreate之前执行一些准备操作
     */
    protected void beforeOnCreate(Bundle savedInstanceState)
    {

    }

    /**
     * 子类可重写此方法获取Intent传递过来的参数
     */
    protected void getIntentData(Intent data, boolean onNewIntent)
    {

    }

    /**
     * 子类实现此方法指定contentView的id
     */
    public abstract int getContentViewId();

    /**
     * 子类实现此方法初始化UI
     */
    protected abstract void initUI(View contentView);

    /**
     * 子类实现此方法初始化数据
     */
    protected abstract void initData(@Nullable Bundle savedInstanceState);

    /**
     * 查找View
     */
    protected <T extends View> T find(int resId)
    {
        return mFinder.findView(resId);
    }

    /**
     * 添加点击事件
     */
    protected void addClick(View view)
    {
        mFinder.addClick(view, this);
    }

    /**
     * 添加点击事件
     */
    protected void addClick(View view, View.OnClickListener listener)
    {
        if (view != null && listener != null)
            view.setOnClickListener(listener);
        else
            mFinder.addClick(view, this);
    }

    /**
     * 添加点击事件
     */
    protected void addClick(int id)
    {
        mFinder.addClick(id, this);
    }

    /**
     * 添加点击事件
     */
    protected void addClick(int id, View.OnClickListener listener)
    {
        View view = mFinder.findView(id);
        if (view != null && listener != null)
            view.setOnClickListener(listener);
        else
            mFinder.addClick(view, this);
    }

    /**
     * 添加点击事件
     */
    protected void addClick(int... ids)
    {
        for (int i = 0; i < ids.length; i++)
        {
            mFinder.addClick(ids[i], this);
        }
    }

    @Override
    public void onClick(View v)
    {
        onClick(v.getId(), v);
    }

    /**
     * 弹出时间较短的Toast
     */
    public void showShortToast(CharSequence message)
    {
        ToastUtils.showShort(message);
    }

    /**
     * 弹出时间较短的Toast
     */
    public void showShortToast(@StringRes int resId)
    {
        ToastUtils.showShort(resId);
    }

    /**
     * 弹出时间较长的Toast
     */
    public void showLongToast(CharSequence message)
    {
        ToastUtils.showLong(message);
    }

    /**
     * 弹出时间较长的Toast
     */
    public void showLongToast(@StringRes int resId)
    {
        ToastUtils.showLong(resId);
    }

    /**
     * 子类实现此方法处理点击事件
     */
    protected abstract void onClick(int id, View v);
}
