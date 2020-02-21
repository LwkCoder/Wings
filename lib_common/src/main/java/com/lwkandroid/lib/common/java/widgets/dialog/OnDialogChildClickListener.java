package com.lwkandroid.lib.common.java.widgets.dialog;

import android.view.View;

/**
 * Description:Dialog子控件点击事件
 *
 * @author LWK
 * @date 2019/6/10
 */
public interface OnDialogChildClickListener
{
    void onDialogChildClicked(int viewId, View view, View contentView, DialogCreator creator);
}
