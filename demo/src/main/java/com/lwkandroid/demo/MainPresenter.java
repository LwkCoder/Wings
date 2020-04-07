package com.lwkandroid.demo;

import com.lwkandroid.lib.common.mvp.MvpBasePresenterImpl;

/**
 * Description:Presenter层
 *
 * @author
 * @date
 */
class MainPresenter extends MvpBasePresenterImpl<MainContract.IView, MainContract.IModel>
        implements MainContract.IPresenter<MainContract.IView, MainContract.IModel>
{
    public MainPresenter(MainContract.IView viewImpl, MainContract.IModel modelImpl)
    {
        super(viewImpl, modelImpl);
    }
}
