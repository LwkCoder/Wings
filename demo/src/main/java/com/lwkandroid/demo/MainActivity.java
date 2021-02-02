package com.lwkandroid.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lwkandroid.demo.dialog.DialogActivity;
import com.lwkandroid.demo.pop.PopActivity;
import com.lwkandroid.demo.qrcode.QrCodeActivity;
import com.lwkandroid.demo.rxhttp.RxHttpActivity;
import com.lwkandroid.lib.common.mvp.MvpBaseActivity;
import com.lwkandroid.lib.core.annotation.ClickViews;
import com.lwkandroid.lib.core.annotation.ViewInjector;
import com.lwkandroid.lib.core.log.KLog;
import com.lwkandroid.lib.core.net.RxHttp;
import com.lwkandroid.lib.core.net.bean.ApiException;
import com.lwkandroid.lib.core.net.observer.ApiObserver;
import com.lwkandroid.lib.core.rx.life.RxLife;
import com.lwkandroid.lib.core.rx.scheduler.RxSchedulers;
import com.lwkandroid.lib.core.utils.common.SPUtils;
import com.lwkandroid.lib.core.utils.common.TimeUtils;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import io.reactivex.rxjava3.core.Observable;

/**
 * Description:View层
 *
 * @author
 * @date
 */
public class MainActivity extends MvpBaseActivity<MainPresenter> implements MainContract.IView<MainPresenter>
{

    @Override
    protected MainPresenter createPresenter()
    {
        return new MainPresenter(this, new MainModel());
    }


    @Override
    protected void getIntentData(Intent intent, boolean newIntent)
    {

    }

    @Override
    protected int getContentViewId()
    {
        return R.layout.activity_main;
    }

    @Override
    protected void initUI(View contentView)
    {
        ViewInjector.with(this);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {
    }

    @Override
    @ClickViews(values = {R.id.btn_main_01, R.id.btn_main_02,
            R.id.btn_main_03, R.id.btn_main_04, R.id.btn_main_05})
    public void onClick(int id, View view)
    {
        switch (id)
        {
            case R.id.btn_main_01:
                startActivity(new Intent(MainActivity.this, RxHttpActivity.class));
                break;
            case R.id.btn_main_02:
                startActivity(new Intent(MainActivity.this, PopActivity.class));
                break;
            case R.id.btn_main_03:
                startActivity(new Intent(MainActivity.this, DialogActivity.class));
                break;
            case R.id.btn_main_04:
                startActivity(new Intent(MainActivity.this, QrCodeActivity.class));
                break;
            case R.id.btn_main_05:
                test();
                break;
            default:
                break;
        }
    }

    private void test()
    {
        Observable.interval(0, 30000, TimeUnit.MILLISECONDS)
                .compose(RxSchedulers.applyComputation2Main())
                .compose(RxLife.with(this).bindUtilOnDestroy())
                .subscribe(new ApiObserver<Long>()
                {
                    @Override
                    public void onAccept(Long aLong)
                    {
                        KLog.i("触发上报：" + aLong);
                        int dataId = SPUtils.getInstance().getInt("dataId", 0);
                        dataId++;
                        SPUtils.getInstance().put("dataId", dataId);
                        String date = TimeUtils.date2String(new Date(System.currentTimeMillis()));
                        float initValue = new Random().nextInt(5);
                        float[] waterArr = new float[10];
                        for (int i = 0; i < waterArr.length; i++)
                        {
                            float random = new Random().nextFloat();
                            waterArr[i] = initValue + random > 5.0f ? initValue : initValue + random;
                        }

                        RxHttp.POST("http://183.6.36.230:20207/api/data/save")
                                .addFormData("stationType", 7)
                                .addFormData("dataId", dataId)
                                .addFormData("stationId", "LWK001")
                                .addFormData("dataProtocal", 1)
                                .addFormData("sendFrequency", 10)
                                .addFormData("dataTime", date)
                                .addFormData("waterLevel", waterArr)
                                .addFormData("rainfall", new int[]{12, 11, 12, 12, 12, 11, 12, 12, 12, 12})
                                .addFormData("tcase", 43.0)
                                .addFormData("batteryVoltage", 13.0)
                                .addFormData("cardMemory", 13)
                                .convertToJsonRequest()
                                .returnString()
                                .compose(RxSchedulers.applyIo2Main())
                                .subscribe(new ApiObserver<String>()
                                {
                                    @Override
                                    public void onAccept(String s)
                                    {
                                        showShortToast("上传完成：" + s);
                                    }

                                    @Override
                                    public void onError(ApiException e)
                                    {
                                        showShortToast("上传失败");
                                        KLog.e(e.toString());
                                    }
                                });
                    }

                    @Override
                    public void onError(ApiException e)
                    {

                    }
                });
    }
}
