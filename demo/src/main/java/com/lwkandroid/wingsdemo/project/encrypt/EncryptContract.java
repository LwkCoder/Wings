package com.lwkandroid.wingsdemo.project.encrypt;

import com.lwkandroid.wingsdemo.app.AppBaseModel;
import com.lwkandroid.wingsdemo.app.AppBasePresenter;
import com.lwkandroid.wingsdemo.app.IAppBaseView;

/**
 * Created by LWK
 * TODO 契约层
 */
interface EncryptContract
{
    interface IView extends IAppBaseView
    {
    }

    abstract class Model extends AppBaseModel
    {
    }

    abstract class Presenter extends AppBasePresenter
    {
    }

}
