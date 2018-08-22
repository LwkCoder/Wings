package com.lwkandroid.wingsdemo.project;

/**
 * Created by LWK
 * TODO RxHttpDemoActivity Presenter层
 */

public class MainPresenter extends MainContract.Presenter
{

    @Override
    public MainContract.Model createModel()
    {
        return new MainModel();
    }
}
