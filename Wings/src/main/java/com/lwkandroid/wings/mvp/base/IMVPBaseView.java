package com.lwkandroid.wings.mvp.base;

import androidx.annotation.StringRes;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by LWK
 * TODO MVP模版中View的基础接口
 */

public interface IMVPBaseView
{
    PublishSubject<Integer> LIFECYCLE_SUBJECT = PublishSubject.create();

    void showShortToast(@StringRes int resId);

    void showShortToast(CharSequence message);

    void showLongToast(@StringRes int resId);

    void showLongToast(CharSequence message);

    PublishSubject<Integer> getLifecycleSubject();
}
