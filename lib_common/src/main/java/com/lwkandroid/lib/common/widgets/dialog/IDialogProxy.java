package com.lwkandroid.lib.common.widgets.dialog;

import androidx.fragment.app.FragmentActivity;

/**
 * Created by LWK
 * 设置给配置参数调用Dialog显示方式的接口
 */
interface IDialogProxy
{
    DialogCreator show(FragmentActivity activity);
}
