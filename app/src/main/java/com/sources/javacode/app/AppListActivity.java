package com.sources.javacode.app;

import com.lwkandroid.wings.mvp.base.MVPBasePresenter;
import com.lwkandroid.wings.mvp.list.WingsListActivity;
import com.lwkandroid.wings.utils.BarUtils;
import com.sources.javacode.R;

/**
 * Created by LWK
 * TODO 列表界面基类
 */

public abstract class AppListActivity<P extends MVPBasePresenter, RV, D> extends WingsListActivity<P, RV, D>
{
    @Override
    protected void setBarColor()
    {
        BarUtils.setStatusBarColor(this, getResources().getColor(R.color.statusbar));
    }
}
