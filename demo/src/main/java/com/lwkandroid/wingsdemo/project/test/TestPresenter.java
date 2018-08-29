package com.lwkandroid.wingsdemo.project.test;

/**
 * Created by LWK
 * TODO Presenter层
 */

public class TestPresenter extends TestContract.Presenter
{
    @Override
    public TestContract.Model createModel()
    {
        return new TestModel();
    }
}
