package com.lwkandroid.wingsdemo.project;


import android.graphics.Color;

import com.lwkandroid.lib.core.java.utils.ToastUtils;
import com.lwkandroid.wingsdemo.R;

/**
 * Created by LWK
 * RxHttpDemoActivity Presenter层
 *
 * @author LWK
 */
public class MainPresenter extends MainContract.Presenter
{

    @Override
    public MainContract.Model createModel()
    {
        return new MainModel();
    }

    @Override
    public void queryTestDataList()
    {
    }

    @Override
    public void test()
    {
        ToastUtils.setBgColor(Color.RED);
        ToastUtils.setTextSizeByResId(R.dimen.text_size_large);
        ToastUtils.setTextColor(Color.GREEN);
        ToastUtils.showLong("e8231892y3e8912");
    }

}
