package com.lwkandroid.wings.rx.mvp;

/**
 * Created by LWK
 * TODO 默认Presenter（当View层泛型为空的情况下创建）
 */
class DefaultMVPPresenter extends MVPBasePresenter<IMVPBaseView, Void>
{
    @Override
    public Void createModel()
    {
        return null;
    }
}
