package com.lwkandroid.wingsdemo.project;

/**
 * Created by LWK
 * TODO RxHttpDemoActivity Presenter层
 */

public class MainPresenter extends MainConstract.Presenter
{

    @Override
    public MainConstract.Model createModel()
    {
        return new MainModel();
    }
}
