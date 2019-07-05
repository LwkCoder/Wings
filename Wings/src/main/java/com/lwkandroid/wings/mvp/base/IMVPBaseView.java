package com.lwkandroid.wings.mvp.base;

import com.lwkandroid.wings.rx.lifecycle.IRxLifeCyclePublisher;

import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentActivity;

/**
 * MVP模版中View的基础接口
 *
 * @author LWK
 */
public interface IMVPBaseView<P extends MVPBasePresenter> extends IRxLifeCyclePublisher
{
    FragmentActivity getFragmentActivity();

    P getPresenter();

    void showShortToast(@StringRes int resId);

    void showShortToast(CharSequence message);

    void showLongToast(@StringRes int resId);

    void showLongToast(CharSequence message);
}
