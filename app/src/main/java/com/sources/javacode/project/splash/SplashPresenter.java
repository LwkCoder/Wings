package com.sources.javacode.project.splash;

import com.lwkandroid.lib.common.mvp.MvpBasePresenterImpl;

/**
 * Description:Presenter层
 *
 * @author
 * @date
 */
class SplashPresenter extends MvpBasePresenterImpl<SplashContract.IView, SplashContract.IModel>
        implements SplashContract.IPresenter<SplashContract.IView, SplashContract.IModel>
{
    public SplashPresenter(SplashContract.IView viewImpl, SplashContract.IModel modelImpl)
    {
        super(viewImpl, modelImpl);
    }
}
