package com.lwkandroid.wings.mvp.base;

import android.app.Activity;

import com.lwkandroid.wings.rx.lifecycle.IRxLifeCyclePublisher;

import androidx.annotation.StringRes;

/**
 * Created by LWK
 * MVP模版中View的基础接口
 */

public interface IMVPBaseView extends IRxLifeCyclePublisher
{
    Activity getAttachedActivity();

    void showShortToast(@StringRes int resId);

    void showShortToast(CharSequence message);

    void showLongToast(@StringRes int resId);

    void showLongToast(CharSequence message);
}
