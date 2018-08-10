package com.lwkandroid.wingsdemo.app;


import com.lwkandroid.wings.rx.mvp.MVPBasePresenter;
import com.lwkandroid.wings.rx.mvp.WingsBaseActivity;
import com.lwkandroid.wings.utils.BarUtils;
import com.lwkandroid.wingsdemo.R;

/**
 * Created by LWK
 * TODO 项目定制Acitvity基类
 */

public abstract class AppBaseActivity<P extends MVPBasePresenter> extends WingsBaseActivity<P>
{
    @Override
    protected void setBarColor()
    {
        BarUtils.setStatusBarColor(this, getResources().getColor(R.color.deep_darker));
    }
}
