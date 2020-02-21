package com.lwkandroid.lib.common.java.widgets.pop;

import android.view.View;

/**
 * Description:Pop子控件点击事件
 *
 * @author LWK
 * @date 2019/6/10
 */
public interface OnPopChildClickListener
{
    void onPopChildClicked(int viewId, View view, View contentView, PopCreator popCreator);
}
