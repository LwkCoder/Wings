package com.lwkandroid.app.project.splash;

/**
 * Created by LWK
 * TODO 开屏页Presenter层
 */

public class SplashPresenter extends SplashContract.Presenter
{
    @Override
    public SplashContract.Model createModel()
    {
        return new SplashModel();
    }
}
