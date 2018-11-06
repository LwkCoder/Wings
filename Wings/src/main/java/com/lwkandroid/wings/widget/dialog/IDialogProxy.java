package com.lwkandroid.wings.widget.dialog;

import androidx.fragment.app.FragmentActivity;

/**
 * Created by LWK
 * TODO 设置给配置参数调用Dialog显示方式的接口
 */
public interface IDialogProxy
{
    DialogCreator show(FragmentActivity activity);
}
