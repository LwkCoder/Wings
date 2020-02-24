package com.lwkandroid.lib.common.widgets.dialog;

import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.ViewGroup;

import com.lwkandroid.lib.common.R;

import java.io.Serializable;

import androidx.annotation.FloatRange;
import androidx.annotation.StyleRes;
import androidx.fragment.app.FragmentActivity;

/**
 * Dialog配置参数
 *
 * @author LWK
 */
public class DialogOptions implements IDialogProxy, Serializable
{
    private static final long serialVersionUID = 8073983675073865834L;
    private boolean mFocusable = true;
    private boolean mCancelable = true;
    private boolean mCanceledOnTouchOutside = true;
    private @StyleRes
    int mThemeStyle = R.style.BaseDialogStyle;
    private @FloatRange(from = 0.0, to = 1.0f)
    float mDarkWindowDegree = 0.5f;
    private @StyleRes
    int mAnimStyle = android.R.style.Animation_Dialog;
    private int mLayoutGravity = Gravity.CENTER;
    private ViewGroup.LayoutParams mLayoutParams = new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private DialogBaseContentView mContentView;
    private DialogInterface.OnShowListener mOnShowListener;
    private DialogInterface.OnDismissListener mOnDismissListener;
    private DialogInterface.OnCancelListener mOnCancelListener;
    private DialogInterface.OnKeyListener mOnKeyListener;
    private SparseArray<OnDialogChildClickListener> mChildClickListenerArray;

    public boolean isFocusable()
    {
        return mFocusable;
    }

    public DialogOptions setFocusable(boolean focusable)
    {
        this.mFocusable = focusable;
        return this;
    }

    public boolean isCanceledOnTouchOutside()
    {
        return mCanceledOnTouchOutside;
    }

    public boolean isCancelable()
    {
        return mCancelable;
    }

    public DialogOptions setCancelable(boolean cancelable)
    {
        this.mCancelable = cancelable;
        return this;
    }

    public DialogOptions setCanceledOnTouchOutside(boolean canceledOnTouchOutside)
    {
        this.mCanceledOnTouchOutside = canceledOnTouchOutside;
        return this;
    }

    public float getDarkWindowDegree()
    {
        return mDarkWindowDegree;
    }

    public DialogOptions setDarkWindowDegree(float darkWindowDegree)
    {
        this.mDarkWindowDegree = darkWindowDegree;
        return this;
    }

    public int getAnimStyle()
    {
        return mAnimStyle;
    }

    public DialogOptions setAnimStyle(int animStyle)
    {
        this.mAnimStyle = animStyle;
        return this;
    }

    public int getLayoutGravity()
    {
        return mLayoutGravity;
    }

    public DialogOptions setLayoutGravity(int layoutGravity)
    {
        this.mLayoutGravity = layoutGravity;
        return this;
    }

    public ViewGroup.LayoutParams getLayoutParams()
    {
        return mLayoutParams;
    }

    public DialogOptions setLayoutParams(ViewGroup.LayoutParams layoutParams)
    {
        this.mLayoutParams = layoutParams;
        return this;
    }

    public DialogBaseContentView getContentView()
    {
        return mContentView;
    }

    public DialogOptions setContentView(DialogBaseContentView contentView)
    {
        this.mContentView = contentView;
        return this;
    }

    public int getThemeStyle()
    {
        return mThemeStyle;
    }

    public DialogOptions setThemeStyle(@StyleRes int themeStyle)
    {
        this.mThemeStyle = themeStyle;
        return this;
    }

    public DialogInterface.OnShowListener getShowListener()
    {
        return mOnShowListener;
    }

    public DialogOptions setOnShowListener(DialogInterface.OnShowListener showListener)
    {
        this.mOnShowListener = showListener;
        return this;
    }

    public DialogInterface.OnDismissListener getDismissListener()
    {
        return mOnDismissListener;
    }

    public DialogOptions setOnDismissListener(DialogInterface.OnDismissListener dismissListener)
    {
        this.mOnDismissListener = dismissListener;
        return this;
    }

    public DialogInterface.OnCancelListener getCancelListener()
    {
        return mOnCancelListener;
    }

    public DialogOptions setOnCancelListener(DialogInterface.OnCancelListener cancelListener)
    {
        this.mOnCancelListener = cancelListener;
        return this;
    }

    public DialogInterface.OnKeyListener getKeyListener()
    {
        return mOnKeyListener;
    }

    public DialogOptions setOnKeyListener(DialogInterface.OnKeyListener keyListener)
    {
        this.mOnKeyListener = keyListener;
        return this;
    }

    public DialogOptions addOnChildClickListener(int viewId, OnDialogChildClickListener listener)
    {
        if (mChildClickListenerArray == null)
        {
            mChildClickListenerArray = new SparseArray<>();
        }
        mChildClickListenerArray.put(viewId, listener);
        return this;
    }

    public SparseArray<OnDialogChildClickListener> getChildClickListenerArray()
    {
        return mChildClickListenerArray;
    }

    @Override
    public DialogCreator show(FragmentActivity activity)
    {
        return new DialogCreator().show(activity, this);
    }
}
