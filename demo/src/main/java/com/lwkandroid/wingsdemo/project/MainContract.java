package com.lwkandroid.wingsdemo.project;

import com.lwkandroid.wingsdemo.app.AppBaseModel;
import com.lwkandroid.wingsdemo.app.AppBasePresenter;
import com.lwkandroid.wingsdemo.app.IAppBaseView;

/**
 * Created by LWK
 * TODO MainActivity契约类
 */

public interface MainContract
{
    interface View extends IAppBaseView
    {
    }

    abstract class Model extends AppBaseModel
    {
    }

    abstract class Presenter extends AppBasePresenter<View, Model>
    {
    }
}
