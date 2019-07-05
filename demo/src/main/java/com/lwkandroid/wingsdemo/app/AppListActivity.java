package com.lwkandroid.wingsdemo.app;

import com.lwkandroid.wings.mvp.base.MVPBasePresenter;
import com.lwkandroid.wings.mvp.list.WingsListActivity;
import com.lwkandroid.wings.utils.BarUtils;
import com.lwkandroid.wings.utils.ResourceUtils;
import com.lwkandroid.wingsdemo.R;

/**
 * 列表界面基类
 *
 * @author LWK
 */
public abstract class AppListActivity<P extends MVPBasePresenter, RV, D> extends WingsListActivity<P, RV, D>
{
    @Override
    protected void setBarColor()
    {
        BarUtils.setStatusBarColor(this, ResourceUtils.getColor(R.color.deep_darker));
    }
}
