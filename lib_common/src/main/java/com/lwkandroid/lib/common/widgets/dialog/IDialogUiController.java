package com.lwkandroid.lib.common.widgets.dialog;

import android.content.DialogInterface;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.lifecycle.LifecycleObserver;

/**
 * Description:定义Dialog UI层的操作接口
 *
 * @author LWK
 * @date 2020/4/7
 */
public interface IDialogUiController extends LifecycleObserver
{
    @LayoutRes
    int getLayoutId();

    void onCreateView(View contentView, WingsDialog dialog);

    void onShow(DialogInterface dialog);

    void onDismiss(DialogInterface dialog);

    void onCancel(DialogInterface dialog);
}
