package com.lwkandroid.wings.mvp;

import android.app.Activity;
import android.support.annotation.StringRes;

/**
 * MVP模版中View的公共接口
 */

public interface IBaseView
{
    void showShortToast(CharSequence message);

    void showShortToast(@StringRes int resId);

    void showLongToast(CharSequence message);

    void showLongToast(@StringRes int resId);

    Activity getActivityContext();
}
