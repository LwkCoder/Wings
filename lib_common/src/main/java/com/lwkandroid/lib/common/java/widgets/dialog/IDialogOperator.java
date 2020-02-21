package com.lwkandroid.lib.common.java.widgets.dialog;

import androidx.fragment.app.FragmentActivity;

/**
 * Created by LWK
 *  定义DialogCreator外部可调方法的接口
 */
 interface IDialogOperator
{
    DialogCreator show(FragmentActivity activity, DialogOptions options);

    boolean isShowing();

    void dismiss();

    void cancel();
}
