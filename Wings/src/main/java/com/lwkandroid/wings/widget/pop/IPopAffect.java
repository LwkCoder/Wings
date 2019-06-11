package com.lwkandroid.wings.widget.pop;

import android.content.Context;

/**
 * Created by LWK
 * PopupWindow显示/关闭过程效果
 */
public interface IPopAffect
{
    void onShowingProgress(Context context, PopCreator creator, PopOptions options, float progress);

    void onDismissed(Context context, PopCreator creator, PopOptions options);
}
