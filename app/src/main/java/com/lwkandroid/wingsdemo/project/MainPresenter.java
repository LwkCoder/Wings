package com.lwkandroid.wingsdemo.project;

/**
 * Created by LWK
 * TODO MainActivity Presenter层
 */

public class MainPresenter extends MainConstract.Presenter
{

    @Override
    protected MainConstract.Model createModel()
    {
        return new MainModel();
    }
}
