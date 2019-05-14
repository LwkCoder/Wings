package com.lwkandroid.wingsdemo.project.test;

/**
 * Description:Presenter层
 *
 * @author
 * @date
 */
class MyFragmentPresenter extends MyFragmentContract.Presenter
{
    @Override
    public MyFragmentContract.Model createModel()
    {
        return new MyFragmentModel();
    }
}
