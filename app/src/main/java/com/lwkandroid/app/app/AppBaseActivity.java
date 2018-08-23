package com.lwkandroid.app.app;

import com.lwkandroid.app.R;
import com.lwkandroid.wings.mvp.base.MVPBasePresenter;
import com.lwkandroid.wings.mvp.base.WingsBaseActivity;
import com.lwkandroid.wings.utils.BarUtils;
import com.lwkandroid.wings.utils.ResourceUtils;

/**
 * Created by LWK
 * TODO Activity基类
 */

public abstract class AppBaseActivity<P extends MVPBasePresenter> extends WingsBaseActivity<P>
{
    @Override
    protected void setBarColor()
    {
        BarUtils.setStatusBarColor(this, ResourceUtils.getColor(R.color.statusbar));
    }
}
