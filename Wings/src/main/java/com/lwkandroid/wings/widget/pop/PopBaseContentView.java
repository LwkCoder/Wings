package com.lwkandroid.wings.widget.pop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lwkandroid.wings.utils.Utils;

import java.lang.ref.WeakReference;

/**
 * Created by LWK
 * TODO PopupWindow UI层，继承该类实现数据处理和UI交互
 * 【该类下不应调用PopCreator实现的IPopOperator显示方法】
 */

public abstract class PopBaseContentView
{
    private WeakReference<Context> mContextReference;
    private PopCreator mPopCreator;
    private PopOptions mOptions;
    private ViewGroup mRealContentView;

    /**
     * 和PopCreator关联起来，子类不需要调用这个方法
     */
    void attachToCreator(Context context, PopOptions options, PopCreator popCreator)
    {
        this.mContextReference = new WeakReference<>(context != null ? context : Utils.getContext());
        this.mOptions = options;
        this.mPopCreator = popCreator;

        //解决xml根布局定义宽高无效的问题
        mRealContentView = new FrameLayout(context);
        mRealContentView.setFocusable(options.isFocusable());
        mRealContentView.setFocusableInTouchMode(options.isCanceledOnTouchOutside());
        View layout = LayoutInflater.from(context).inflate(getContentViewLayoutResId(), mRealContentView, false);
        mRealContentView.addView(layout);
        initUIAndData(getRealContentView(), options, popCreator);
    }

    /**
     * 获取Context对象
     */
    public Context getContext()
    {
        return mContextReference != null ? mContextReference.get() : Utils.getContext();
    }

    /**
     * 获取配置参数
     */
    public PopOptions getOptions()
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
     * 获取PopCreator对象
     */
    public PopCreator getCreator()
    {
        return mPopCreator;
    }

    /**
     * 子类实现，指定布局id
     */
    public abstract int getContentViewLayoutResId();

    /**
     * 子类实现，设置UI和数据
     *
     * @param contentView PopupWindow的ContentView
     * @param options     配置参数
     * @param popCreator  PopCreator对象
     * @param <T>         配置参数的泛型
     */
    public abstract <T extends PopOptions<T>> void initUIAndData(View contentView, T options, PopCreator popCreator);
}
