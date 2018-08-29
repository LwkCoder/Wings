package com.lwkandroid.wingsdemo.project.test;

import com.lwkandroid.wingsdemo.app.AppBaseModel;
import com.lwkandroid.wingsdemo.app.AppBasePresenter;
import com.lwkandroid.wingsdemo.app.AppBaseView;

/**
 * Created by LWK
 * TODO 契约类
 */

public interface TestContract
{
    interface IView extends AppBaseView
    {
    }

    abstract class Model extends AppBaseModel
    {
    }

    abstract class Presenter extends AppBasePresenter<IView, Model>
    {
    }
}
