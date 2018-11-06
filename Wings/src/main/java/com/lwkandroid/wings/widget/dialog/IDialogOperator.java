package com.lwkandroid.wings.widget.dialog;

import androidx.fragment.app.FragmentActivity;

/**
 * Created by LWK
 * TODO 定义DialogCreator外部可调方法的接口
 */
public interface IDialogOperator
{
    DialogCreator show(FragmentActivity activity, DialogOptions options);

    boolean isShowing();

    void dismiss();
}
