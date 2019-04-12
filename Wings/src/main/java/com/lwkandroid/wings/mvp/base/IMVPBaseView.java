package com.lwkandroid.wings.mvp.base;

import androidx.annotation.StringRes;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.SingleSubject;

/**
 * Created by LWK
 * TODO MVP模版中View的基础接口
 */

public interface IMVPBaseView
{
    PublishSubject<Integer> LIFECYCLE_PUBLISH_SUBJECT = PublishSubject.create();

    SingleSubject<Integer> LIFECYCLE_SINGLE_SUBJECT = SingleSubject.create();

    void showShortToast(@StringRes int resId);

    void showShortToast(CharSequence message);

    void showLongToast(@StringRes int resId);

    void showLongToast(CharSequence message);

    PublishSubject<Integer> getLifecyclePublishSubject();

    SingleSubject<Integer> getLifecycleSingleSubject();
}
