package com.lwkandroid.app.project.home;

/**
 * Created by LWK
 * TODO 主页Presenter层
 */

public class HomePresenter extends HomeContract.Presenter
{
    @Override
    public HomeContract.Model createModel()
    {
        return new HomeModel();
    }
}
