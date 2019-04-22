package com.sources.javacode.project.splash;

import com.sources.javacode.app.AppBaseModel;
import com.sources.javacode.app.AppBasePresenter;
import com.sources.javacode.app.IAppBaseView;

/**
 * Created by LWK
 *  契约层
 */
interface SplashContract
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
