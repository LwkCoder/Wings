package com.sources.javacode.project.home;

import com.sources.javacode.app.AppBaseModel;
import com.sources.javacode.app.AppBasePresenter;
import com.sources.javacode.app.IAppBaseView;

/**
 * Created by LWK
 * TODO 契约层
 */
interface Contract
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
