package com.lwkandroid.wings.mvp.base;

import androidx.annotation.IdRes;
import android.view.View;

/**
 * Created by LWK
 *  内容布局方法接口
 */

interface IContentView
{
    View getContentView();

    <T extends View> T find(@IdRes int resId);

    void addClick(@IdRes int resId);

    void addClick(@IdRes int resId, View.OnClickListener listener);

    void addClick(@IdRes int... resIds);

    void addClick(View view);

    void addClick(View view, View.OnClickListener listener);

    void addClick(View... views);
}
