package com.lwkandroid.demo.rxhttp.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lwkandroid.demo.R;
import com.lwkandroid.lib.common.mvp.MvpBaseActivity;

import androidx.annotation.Nullable;

/**
 * Description:View层
 *
 * @author
 * @date
 */
public class LoginActivity extends MvpBaseActivity<LoginPresenter> implements LoginContract.IView<LoginPresenter>
{

    @Override
    protected LoginPresenter createPresenter()
    {
        return new LoginPresenter(this, new LoginModel());
    }


    @Override
    protected void getIntentData(Intent intent, boolean newIntent)
    {

    }

    @Override
    protected int getContentViewId()
    {
        return R.layout.activity_login;
    }

    @Override
    protected void initUI(View contentView)
    {
        addClick(R.id.btn_login);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {

    }

    @Override
    public void onClick(int id, View view)
    {
        switch (id)
        {
            case R.id.btn_login:
                getPresenter().login("111", "122");
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoginSuccess()
    {
        showShortToast("登录成功");
        finish();
    }

    @Override
    public void onLoginFail()
    {

    }
}
