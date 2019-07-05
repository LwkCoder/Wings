package com.sources.javacode.app;

import com.lwkandroid.wings.mvp.base.IMVPBaseView;
import com.lwkandroid.wings.mvp.base.MVPBasePresenter;

/**
 * MVP模式中Presenter层基类
 *
 * @author LWK
 */

public abstract class AppBasePresenter<V extends IMVPBaseView, M> extends MVPBasePresenter<V, M>
{
}
