package com.lwkandroid.demo.dialog;

import com.lwkandroid.lib.common.mvp.MvpBasePresenterImpl;

/**
 * Description:Presenter层
 *
 * @author
 * @date
 */
class DialogPresenter extends MvpBasePresenterImpl<DialogContract.IView, DialogContract.IModel>
        implements DialogContract.IPresenter<DialogContract.IView, DialogContract.IModel>
{
    public DialogPresenter(DialogContract.IView viewImpl, DialogContract.IModel modelImpl)
    {
        super(viewImpl, modelImpl);
    }
}
