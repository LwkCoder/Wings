package com.lwkandroid.demo.pop;

import com.lwkandroid.lib.common.mvp.MvpBasePresenterImpl;

/**
 * Description:Presenter层
 *
 * @author
 * @date
 */
class PopPresenter extends MvpBasePresenterImpl<PopContract.IView, PopContract.IModel>
        implements PopContract.IPresenter<PopContract.IView, PopContract.IModel>
{
    public PopPresenter(PopContract.IView viewImpl, PopContract.IModel modelImpl)
    {
        super(viewImpl, modelImpl);
    }
}
