package com.lwkandroid.wings.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lwkandroid.wings.utils.Utils;

import java.lang.ref.WeakReference;

/**
 * Created by LWK
 *  Dialog UI层，继承该类实现数据处理和UI交互
 */
public abstract class DialogBaseContentView
{
    private WeakReference<Context> mContextReference;
    private DialogCreator mCreator;
    private DialogOptions mOptions;
    private ViewGroup mRealContentView;

    /**
     * 和DialogCreator关联起来，子类不需要调用这个方法
     */
    void attachToCreator(Context context, DialogOptions options, DialogCreator creator)
    {
        this.mContextReference = new WeakReference<>(context != null ? context : Utils.getContext());
        this.mOptions = options;
        mCreator = creator;
    }

    /**
     * 初始化布局
     */
    void initContentView(LayoutInflater inflater)
    {
        //解决xml根布局定义宽高无效的问题
        mRealContentView = new FrameLayout(getContext());
        View layout = inflater.inflate(getContentViewLayoutResId(), mRealContentView, false);
        mRealContentView.addView(layout);
        initUIAndData(getRealContentView(), mOptions, mCreator);
    }

    void onDismiss()
    {
        if (mContextReference != null)
        {
            mContextReference.clear();
        }
        mContextReference = null;
    }

    /**
     * 获取Context对象
     */
    public Context getContext()
    {
        return mContextReference != null ? mContextReference.get() : Utils.getContext();
    }

    /**
     * 获取Creator对象
     */
    public DialogCreator getCreator()
    {
        return mCreator;
    }

    /**
     * 获取配置参数
     */
    public DialogOptions getOptions()
    {
        return mOptions;
    }

    /**
     * 获取实际使用的布局
     */
    public ViewGroup getRealContentView()
    {
        return mRealContentView;
    }

    /**
     * 子类实现，指定布局id
     */
    public abstract int getContentViewLayoutResId();

    /**
     * 子类实现，设置UI和数据
     *
     * @param contentView Dialog的ContentView
     * @param options     配置参数
     * @param creator     DialogCreator对象
     * @param <T>         配置参数的泛型
     */
    public abstract <T extends DialogOptions<T>> void initUIAndData(View contentView, T options, DialogCreator creator);
}
