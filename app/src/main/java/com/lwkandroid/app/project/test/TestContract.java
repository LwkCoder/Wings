package com.lwkandroid.app.project.test;

import com.lwkandroid.app.app.AppBaseModel;
import com.lwkandroid.app.app.AppBasePresenter;
import com.lwkandroid.app.app.IAppBaseView;

/**
 * Created by LWK
 * TODO 契约类
 */

public interface TestContract
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
