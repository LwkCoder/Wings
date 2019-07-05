package com.sources.javacode.app;

import com.lwkandroid.wings.mvp.base.MVPBasePresenter;
import com.lwkandroid.wings.mvp.base.WingsBaseActivity;
import com.lwkandroid.wings.utils.BarUtils;
import com.lwkandroid.wings.utils.ResourceUtils;
import com.sources.javacode.R;

/**
 * Activity基类
 *
 * @author LWK
 */

public abstract class AppBaseActivity<P extends MVPBasePresenter> extends WingsBaseActivity<P>
{
    @Override
    protected void setBarColor()
    {
        BarUtils.setStatusBarColor(this, ResourceUtils.getColor(R.color.statusbar));
    }
}
