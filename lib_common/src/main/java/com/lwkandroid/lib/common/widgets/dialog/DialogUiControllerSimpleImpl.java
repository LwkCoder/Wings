package com.lwkandroid.lib.common.widgets.dialog;

import android.content.DialogInterface;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

/**
 * @description: 仅仅作为设置布局的DialogUI层实现
 * @author: LWK
 * @date: 2021/3/31 14:22
 */
public final class DialogUiControllerSimpleImpl implements IDialogUiController
{
    @LayoutRes
    private int mLayoutResId;

    public DialogUiControllerSimpleImpl(@LayoutRes int layoutResId)
    {
        this.mLayoutResId = layoutResId;
    }

    @Override
    public int getLayoutId()
    {
        return this.mLayoutResId;
    }

    @Override
    public void onCreateView(ViewGroup parentView, WingsDialog dialog)
    {

    }

    @Override
    public void onShow(DialogInterface dialog)
    {

    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {

    }

    @Override
    public void onCancel(DialogInterface dialog)
    {

    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event)
    {

    }
}
