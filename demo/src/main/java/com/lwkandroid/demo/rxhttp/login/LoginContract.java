package com.lwkandroid.demo.rxhttp.login;

import com.lwkandroid.demo.rxhttp.bean.LoginResultBean;
import com.lwkandroid.lib.common.mvp.IMvpBaseModel;
import com.lwkandroid.lib.common.mvp.IMvpBasePresenter;
import com.lwkandroid.lib.common.mvp.IMvpBaseView;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.rxjava3.core.Observable;

/**
 * Description:契约层
 *
 * @author
 * @date
 */
interface LoginContract
{
    interface IView<P extends LifecycleObserver> extends IMvpBaseView<P>
    {
        void onLoginSuccess();

        void onLoginFail();
    }

    interface IModel extends IMvpBaseModel
    {
        Observable<LoginResultBean> login(String account, String pwd);
    }

    interface IPresenter<V extends LifecycleOwner, M> extends IMvpBasePresenter<V, M>
    {
        void login(String account, String pwd);
    }
}
