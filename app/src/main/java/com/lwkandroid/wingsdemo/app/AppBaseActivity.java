package com.lwkandroid.wingsdemo.app;


import android.os.Bundle;

import com.lwkandroid.wings.rx.mvp.RxBasePresenter;
import com.lwkandroid.wings.rx.mvp.RxMvpBaseActivity;
import com.lwkandroid.wings.utils.BarUtils;
import com.lwkandroid.wingsdemo.R;

/**
 * Created by LWK
 * TODO 项目定制Acitvity基类
 */

public abstract class AppBaseActivity<P extends RxBasePresenter> extends RxMvpBaseActivity<P>
{
    @Override
    protected void beforeOnCreate(Bundle savedInstanceState)
    {
        super.beforeOnCreate(savedInstanceState);
        BarUtils.setStatusBarColor(this, getResources().getColor(R.color.deep_darker));
    }
}
