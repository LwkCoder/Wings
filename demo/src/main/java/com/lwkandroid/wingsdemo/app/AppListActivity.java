package com.lwkandroid.wingsdemo.app;

import com.lwkandroid.wings.mvp.base.MVPBasePresenter;
import com.lwkandroid.wings.mvp.list.WingsListActivity;
import com.lwkandroid.wings.utils.BarUtils;
import com.lwkandroid.wingsdemo.R;

/**
 * Created by LWK
 * TODO 列表界面基类
 */

public abstract class AppListActivity<P extends MVPBasePresenter, D> extends WingsListActivity<P, D>
{
    @Override
    protected void setBarColor()
    {
        BarUtils.setStatusBarColor(this, getResources().getColor(R.color.deep_darker));
    }
}
