package com.sources.javacode.project.home;

import com.sources.javacode.app.AppBaseModel;
import com.sources.javacode.app.AppBasePresenter;
import com.sources.javacode.app.IAppBaseView;

/**
 * Created by LWK
 * TODO 主页契约类
 */

public interface HomeContract
{
    interface IView extends IAppBaseView
    {
    }

    abstract class Model extends AppBaseModel
    {
    }

    abstract class Presenter extends AppBasePresenter<IView, Model>
    {
    }
}
