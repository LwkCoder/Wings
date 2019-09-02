package com.lwkandroid.wings.widget.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lwkandroid.wings.Wings;

import java.lang.ref.WeakReference;

/**
 * Dialog UI层，继承该类实现数据处理和UI交互
 *
 * @author LWK
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
        this.mContextReference = new WeakReference<>(context != null ? context : Wings.getContext());
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
        final View layout = inflater.inflate(getContentViewLayoutResId(), mRealContentView, false);
        mRealContentView.addView(layout);
        initUIAndData(getRealContentView(), mOptions, mCreator);
        final SparseArray<OnDialogChildClickListener> listenerArray = mOptions.getChildClickListenerArray();
        if (listenerArray != null)
        {
            for (int i = 0, size = listenerArray.size(); i < size; i++)
            {
                final int index = i;
                final int viewId = listenerArray.keyAt(index);
                View view = mRealContentView.findViewById(viewId);
                if (view != null)
                {
                    view.setOnClickListener(v -> {
                        OnDialogChildClickListener listener = listenerArray.valueAt(index);
                        if (listener != null)
                        {
                            listener.onDialogChildClicked(v.getId(), v, mRealContentView, mCreator);
                        }
                    });
                }
            }
        }
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
        return mContextReference != null ? mContextReference.get() : Wings.getContext();
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
     */
    public abstract void initUIAndData(View contentView, DialogOptions options, DialogCreator creator);
}
