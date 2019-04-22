package com.lwkandroid.wings.mvp.base;

/**
 * Created by LWK
 *  默认Presenter（当View层泛型为空的情况下创建）
 */
class DefaultMVPPresenter extends MVPBasePresenter<IMVPBaseView, Void>
{
    @Override
    public Void createModel()
    {
        return null;
    }
}
