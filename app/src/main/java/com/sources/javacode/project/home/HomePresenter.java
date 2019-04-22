package com.sources.javacode.project.home;

/**
 * Created by LWK
 *  Presenter层
 */
class HomePresenter extends HomeContract.Presenter
{
    @Override
    public HomeContract.Model createModel()
    {
        return new HomeModel();
    }
}
