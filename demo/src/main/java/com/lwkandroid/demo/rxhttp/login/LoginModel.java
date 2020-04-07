package com.lwkandroid.demo.rxhttp.login;

import com.lwkandroid.demo.rxhttp.AppUrl;
import com.lwkandroid.demo.rxhttp.bean.LoginResultBean;
import com.lwkandroid.lib.common.mvp.MvpBaseModelImpl;
import com.lwkandroid.lib.core.net.RxHttp;

import io.reactivex.Observable;

/**
 * Description:Model层
 *
 * @author
 * @date
 */
class LoginModel extends MvpBaseModelImpl implements LoginContract.IModel
{
    @Override
    public Observable<LoginResultBean> login(String account, String pwd)
    {
        return RxHttp.POST(AppUrl.LOGIN)
                .addFormData("account", account)
                .addFormData("pwd", pwd)
                .parseRestfulObject(LoginResultBean.class);
    }
}
