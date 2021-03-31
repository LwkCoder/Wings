package com.lwkandroid.lib.common.widgets.pop;

import android.view.ViewGroup;

import androidx.annotation.LayoutRes;

/**
 * @description: 仅仅作为设置布局的PopUI层实现
 * @author: LWK
 * @date: 2021/3/31 14:24
 */
public final class PopUiControllerSimpleImpl implements IPopUiController
{
    @LayoutRes
    private int mLayoutResId;

    public PopUiControllerSimpleImpl(@LayoutRes int layoutResId)
    {
        this.mLayoutResId = layoutResId;
    }

    @Override
    public int getLayoutId()
    {
        return this.mLayoutResId;
    }

    @Override
    public void onCreateView(ViewGroup parentView, WingsPopupWindow popupWindow)
    {

    }

    @Override
    public void onDismiss()
    {

    }
}
