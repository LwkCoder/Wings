package com.sources.javacode.project.splash;

/**
 * Created by LWK
 *  Presenter层
 */
class SplashPresenter extends SplashContract.Presenter
{
    @Override
    public SplashContract.Model createModel()
    {
        return new SplashModel();
    }
}
