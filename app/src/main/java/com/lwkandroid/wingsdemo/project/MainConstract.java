package com.lwkandroid.wingsdemo.project;

import com.lwkandroid.wingsdemo.app.AppBaseModel;
import com.lwkandroid.wingsdemo.app.AppBasePresenter;
import com.lwkandroid.wingsdemo.app.AppBaseView;

/**
 * Created by LWK
 * TODO MainActivity契约类
 */

public interface MainConstract
{
    interface View extends AppBaseView
    {
    }

    abstract class Model extends AppBaseModel
    {
    }

    abstract class Presenter extends AppBasePresenter<View, Model>
    {
    }
}
