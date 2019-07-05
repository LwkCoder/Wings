package com.lwkandroid.wingsdemo.app;

import com.lwkandroid.wings.mvp.base.IMVPBaseView;
import com.lwkandroid.wings.mvp.base.MVPBasePresenter;

/**
 * 项目定制Presenter层基类,定制公共方法
 *
 * @author LWK
 */

public abstract class AppBasePresenter<V extends IMVPBaseView, M> extends MVPBasePresenter<V, M>
{
}
