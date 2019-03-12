package com.lwkandroid.wingsdemo.app;


import com.lwkandroid.wings.mvp.base.MVPBasePresenter;
import com.lwkandroid.wings.mvp.base.WingsBaseActivity;
import com.lwkandroid.wings.utils.BarUtils;
import com.lwkandroid.wings.utils.ResourceUtils;
import com.lwkandroid.wingsdemo.R;

/**
 * Created by LWK
 * TODO 项目定制Activity基类
 */

public abstract class AppBaseActivity<P extends MVPBasePresenter> extends WingsBaseActivity<P>
{
    @Override
    protected void setBarColor()
    {
        BarUtils.setStatusBarColor(this, ResourceUtils.getColor(R.color.deep_darker));
    }
}
