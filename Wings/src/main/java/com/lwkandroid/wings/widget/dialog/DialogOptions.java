package com.lwkandroid.wings.widget.dialog;

import android.content.DialogInterface;

import com.lwkandroid.wings.R;

import java.io.Serializable;

import androidx.annotation.StyleRes;
import androidx.fragment.app.FragmentActivity;

/**
 * Created by LWK
 * TODO Dialog配置参数
 */
public class DialogOptions<T extends DialogOptions> implements IDialogProxy, Serializable
{
    private static final long serialVersionUID = -1386963135133670863L;
    private boolean focusable = true;
    private boolean cancelable = true;
    private boolean canceledOnTouchOutside = true;
    private @StyleRes
    int themeStyle = R.style.BaseDialogStyle;
    private DialogBaseContentView contentView;
    private DialogInterface.OnShowListener showListener;
    private DialogInterface.OnDismissListener dismissListener;
    private DialogInterface.OnCancelListener cancelListener;
    private DialogInterface.OnKeyListener keyListener;

    public boolean isFocusable()
    {
        return focusable;
    }

    public T setFocusable(boolean focusable)
    {
        this.focusable = focusable;
        return (T) this;
    }

    public boolean isCanceledOnTouchOutside()
    {
        return canceledOnTouchOutside;
    }

    public boolean isCancelable()
    {
        return cancelable;
    }

    public T setCancelable(boolean cancelable)
    {
        this.cancelable = cancelable;
        return (T) this;
    }

    public T setCanceledOnTouchOutside(boolean canceledOnTouchOutside)
    {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
        return (T) this;
    }

    public DialogBaseContentView getContentView()
    {
        return contentView;
    }

    public T setContentView(DialogBaseContentView contentView)
    {
        this.contentView = contentView;
        return (T) this;
    }

    public int getThemeStyle()
    {
        return themeStyle;
    }

    public T setThemeStyle(@StyleRes int themeStyle)
    {
        this.themeStyle = themeStyle;
        return (T) this;
    }

    public DialogInterface.OnShowListener getShowListener()
    {
        return showListener;
    }

    public T setOnShowListener(DialogInterface.OnShowListener showListener)
    {
        this.showListener = showListener;
        return (T) this;
    }

    public DialogInterface.OnDismissListener getDismissListener()
    {
        return dismissListener;
    }

    public T setOnDismissListener(DialogInterface.OnDismissListener dismissListener)
    {
        this.dismissListener = dismissListener;
        return (T) this;
    }

    public DialogInterface.OnCancelListener getCancelListener()
    {
        return cancelListener;
    }

    public T setOnCancelListener(DialogInterface.OnCancelListener cancelListener)
    {
        this.cancelListener = cancelListener;
        return (T) this;
    }

    public DialogInterface.OnKeyListener getKeyListener()
    {
        return keyListener;
    }

    public T setOnKeyListener(DialogInterface.OnKeyListener keyListener)
    {
        this.keyListener = keyListener;
        return (T) this;
    }

    @Override
    public DialogCreator show(FragmentActivity activity)
    {
        return new DialogCreator().show(activity, this);
    }
}
