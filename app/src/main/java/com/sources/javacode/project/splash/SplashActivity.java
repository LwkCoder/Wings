package com.sources.javacode.project.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lwkandroid.lib.common.mvp.MvpBaseActivity;
import com.lwkandroid.lib.common.permission.AndPermissionDialogHelper;
import com.lwkandroid.lib.core.context.AppContext;
import com.sources.javacode.R;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import androidx.annotation.Nullable;

/**
 * Description:View层
 *
 * @author
 * @date
 */
public class SplashActivity extends MvpBaseActivity<SplashPresenter> implements SplashContract.IView<SplashPresenter>
{

    @Override
    protected SplashPresenter createPresenter()
    {
        return new SplashPresenter(this, new SplashModel());
    }


    @Override
    protected void getIntentData(Intent intent, boolean newIntent)
    {

    }

    @Override
    protected int getContentViewId()
    {
        return R.layout.activity_splash;
    }

    @Override
    protected void initUI(View contentView)
    {
        showLongToast("全局Context是否为空：" + (AppContext.get() == null));
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {
        AndPermission.with(this)
                .notification()
                .permission()
                .rationale(AndPermissionDialogHelper.getNotificationShowDeniedRationale())
                .onGranted(new Action<Void>()
                {
                    @Override
                    public void onAction(Void data)
                    {
                        showShortToast("已授权");
                    }
                })
                .onDenied(AndPermissionDialogHelper.getNotificationShowDeniedAction())
                .start();

    }

    @Override
    public void onClick(int id, View view)
    {
        switch (id)
        {
            default:
                break;
        }
    }

}
