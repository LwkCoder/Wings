package com.lwkandroid.wings.mvp.base;

import com.lwkandroid.wings.rx.lifecycle.IRxLifeCyclePublisher;

import androidx.annotation.StringRes;

/**
 * Created by LWK
 * MVP模版中View的基础接口
 */

public interface IMVPBaseView<P extends MVPBasePresenter> extends IRxLifeCyclePublisher
{
    P getPresenter();

    void showShortToast(@StringRes int resId);

    void showShortToast(CharSequence message);

    void showLongToast(@StringRes int resId);

    void showLongToast(CharSequence message);
}
