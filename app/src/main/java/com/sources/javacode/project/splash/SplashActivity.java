package com.sources.javacode.project.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lwkandroid.lib.common.mvp.MvpBaseActivity;
import com.lwkandroid.lib.common.qrcode.QRCodeScanActivity;
import com.sources.javacode.R;

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
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {
        addClick(R.id.btn_test);
    }

    @Override
    public void onClick(int id, View view)
    {
        switch (id)
        {
            case R.id.btn_test:
                //                Observable.interval(1, TimeUnit.SECONDS)
                //                        .compose(RxLife.with(this).bindUtilOnDestroy())
                //                        .onTerminateDetach()
                //                        .subscribe(new ApiDialogObserver<Long>()
                //                        {
                //                            @Override
                //                            public void subOnNext(Long aLong)
                //                            {
                //                                KLog.e("收到：" + aLong);
                //                            }
                //
                //                            @Override
                //                            public void subOnError(ApiException e)
                //                            {
                //                                KLog.e(e.toString());
                //                            }
                //                        }.setMessage("发送中")
                //                                .setCancelable(true)
                //                                .setCanceledOnTouchOutside(true));
                QRCodeScanActivity.start(this, 100);
                break;
            default:
                break;
        }
    }

}
