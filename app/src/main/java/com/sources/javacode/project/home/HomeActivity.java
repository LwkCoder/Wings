package com.sources.javacode.project.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lwkandroid.lib.common.mvp.MvpBaseActivity;
import com.lwkandroid.lib.common.rx.ApiDialogObserver;
import com.lwkandroid.lib.core.log.KLog;
import com.lwkandroid.lib.core.net.RxHttp;
import com.lwkandroid.lib.core.net.bean.ApiException;
import com.lwkandroid.lib.core.rx.life.RxLife;
import com.lwkandroid.lib.core.rx.scheduler.RxSchedulers;
import com.sources.javacode.R;

import androidx.annotation.Nullable;

/**
 * Description:View层
 *
 * @author
 * @date
 */
public class HomeActivity extends MvpBaseActivity<HomePresenter> implements HomeContract.IView<HomePresenter>
{

    @Override
    protected HomePresenter createPresenter()
    {
        return new HomePresenter(this, new HomeModel());
    }


    @Override
    protected void getIntentData(Intent intent, boolean newIntent)
    {

    }

    @Override
    protected int getContentViewId()
    {
        return R.layout.activity_home;
    }

    @Override
    protected void initUI(View contentView)
    {
        addClick(R.id.btn_home_test01);
        addClick(R.id.btn_home_test02);
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
            case R.id.btn_home_test01:
                //                QRCodeScanHelper.startScanQRCode(this, 100);
                RxHttp.GET("http://localhost:8080/getdata")
                        .returnString()
                        .compose(RxSchedulers.applyIo2Main())
                        .compose(RxLife.with(this).bindUtilOnDestroy())
                        .subscribe(new ApiDialogObserver<String>()
                        {
                            @Override
                            public void subOnNext(String s)
                            {

                            }

                            @Override
                            public void subOnError(ApiException e)
                            {
                                KLog.e("Error----》" + e.getDisplayMessage());
                            }
                        });
                break;
            default:
                break;
        }
    }

}
