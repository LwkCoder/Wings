package com.lwkandroid.wingsdemo.app;

import com.lwkandroid.wings.rx.mvp.IMVPBaseView;
import com.lwkandroid.wings.rx.mvp.MVPBasePresenter;

/**
 * Created by LWK
 * TODO 项目定制Presenter层基类,定制公共方法
 */

public abstract class AppBasePresenter<V extends IMVPBaseView, M> extends MVPBasePresenter<V, M>
{
}
