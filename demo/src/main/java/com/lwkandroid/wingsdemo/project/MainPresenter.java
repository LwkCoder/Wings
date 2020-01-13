package com.lwkandroid.wingsdemo.project;


/**
 * Created by LWK
 * RxHttpDemoActivity Presenter层
 *
 * @author LWK
 */
public class MainPresenter extends MainContract.Presenter
{

    @Override
    public MainContract.Model createModel()
    {
        return new MainModel();
    }

    @Override
    public void queryTestDataList()
    {
    }

    @Override
    public void test()
    {
    }

}
