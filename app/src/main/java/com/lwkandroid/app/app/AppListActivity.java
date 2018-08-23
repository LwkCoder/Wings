package com.lwkandroid.app.app;

import com.lwkandroid.app.R;
import com.lwkandroid.wings.mvp.base.MVPBasePresenter;
import com.lwkandroid.wings.mvp.list.WingsListActivity;
import com.lwkandroid.wings.utils.BarUtils;
import com.lwkandroid.wings.utils.ResourceUtils;

/**
 * Created by LWK
 * TODO 带列表的Activity基类
 */

public abstract class AppListActivity<P extends MVPBasePresenter, D> extends WingsListActivity<P, D>
{
    @Override
    protected void setBarColor()
    {
        BarUtils.setStatusBarColor(this, ResourceUtils.getColor(R.color.statusbar));
    }
}
