package com.lwkandroid.wings.widget.pop;

import android.content.Context;

/**
 * PopupWindow显示/关闭过程效果
 *
 * @author LWK
 */
public interface IPopAffect
{
    void onShowingProgress(Context context, PopCreator creator, PopOptions options, float progress);

    void onDismissed(Context context, PopCreator creator, PopOptions options);
}
