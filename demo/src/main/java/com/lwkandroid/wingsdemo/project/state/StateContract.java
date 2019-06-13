package com.lwkandroid.wingsdemo.project.state;

import com.lwkandroid.widget.StateFrameLayout;
import com.lwkandroid.wingsdemo.app.AppBaseModel;
import com.lwkandroid.wingsdemo.app.AppBasePresenter;
import com.lwkandroid.wingsdemo.app.IAppBaseView;

import io.reactivex.Observable;

/**
 * Description:契约层
 *
 * @author
 * @date
 */
interface StateContract
{
    interface IView extends IAppBaseView
    {
        StateFrameLayout getStateLayout();
    }

    abstract class Model extends AppBaseModel
    {
        abstract Observable<String> requestEmpty();

        abstract Observable<String> requestError();

        abstract Observable<String> requestContent();
    }

    abstract class Presenter extends AppBasePresenter<IView, Model>
    {
        abstract void requestEmpty();

        abstract void requestError();

        abstract void requestContent();
    }

}
