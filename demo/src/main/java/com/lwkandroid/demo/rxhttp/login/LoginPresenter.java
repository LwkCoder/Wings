package com.lwkandroid.demo.rxhttp.login;

import com.lwkandroid.demo.rxhttp.AppTokenModel;
import com.lwkandroid.demo.rxhttp.bean.LoginResultBean;
import com.lwkandroid.lib.common.mvp.MvpBasePresenterImpl;
import com.lwkandroid.lib.common.rx.ApiLoadingObserver;
import com.lwkandroid.lib.core.net.bean.ApiException;

/**
 * Description:Presenter层
 *
 * @author
 * @date
 */
class LoginPresenter extends MvpBasePresenterImpl<LoginContract.IView, LoginContract.IModel>
        implements LoginContract.IPresenter<LoginContract.IView, LoginContract.IModel>
{
    public LoginPresenter(LoginContract.IView viewImpl, LoginContract.IModel modelImpl)
    {
        super(viewImpl, modelImpl);
    }

    @Override
    public void login(String account, String pwd)
    {
        getModelImpl().login(account, pwd)
                .doOnNext(resultBean -> {
                    AppTokenModel.getInstance().setPrivateToken(resultBean.getPrivate_token());
                    //                    AppTokenModel.getInstance().setAccessToken(resultBean.getAccess_token());
                })
                .compose(applyIo2MainUntilOnDestroy())
                .subscribe(new ApiLoadingObserver<LoginResultBean>()
                {
                    @Override
                    public void onAccept(LoginResultBean loginResultBean)
                    {
                        getViewImpl().onLoginSuccess();
                    }

                    @Override
                    public void onError(ApiException e)
                    {
                        getViewImpl().onLoginFail();
                    }
                });
    }
}
