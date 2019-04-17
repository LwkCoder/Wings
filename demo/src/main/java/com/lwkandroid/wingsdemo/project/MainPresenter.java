package com.lwkandroid.wingsdemo.project;

/**
 * Created by LWK
 * TODO RxHttpDemoActivity Presenter层
 *
 * @author IED_WENKANG
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
