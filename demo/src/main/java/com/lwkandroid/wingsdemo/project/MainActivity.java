package com.lwkandroid.wingsdemo.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lwkandroid.widget.comactionbar.ComActionBar;
import com.lwkandroid.wingsdemo.R;
import com.lwkandroid.wingsdemo.app.AppBaseActivity;
import com.lwkandroid.wingsdemo.project.dialog.DialogDemoActivity;
import com.lwkandroid.wingsdemo.project.element.ImageGridActivity;
import com.lwkandroid.wingsdemo.project.encrypt.EncryptActivity;
import com.lwkandroid.wingsdemo.project.image.ImageLoaderDemoActivity;
import com.lwkandroid.wingsdemo.project.list.ListDemoActivity;
import com.lwkandroid.wingsdemo.project.pop.PopDemoActivity;
import com.lwkandroid.wingsdemo.project.qrcode.QRCodeDemoActivity;
import com.lwkandroid.wingsdemo.project.rxhttp.RxHttpDemoActivity;
import com.lwkandroid.wingsdemo.project.state.StateActivity;

import androidx.annotation.Nullable;

/**
 * RxHttpDemoActivity
 *
 * @author LWK
 */
public class MainActivity extends AppBaseActivity<MainPresenter> implements MainContract.View
{
    private ComActionBar mActionBar;

    @Override
    public int getContentViewId()
    {
        return R.layout.activity_main;
    }

    @Override
    protected void initUI(View contentView)
    {
        mActionBar = find(R.id.comactionbar);
        addClick(R.id.btn_main_imageloader, v -> startActivity(new Intent(MainActivity.this, ImageLoaderDemoActivity.class)));
        addClick(R.id.btn_main_rxhttp, v -> startActivity(new Intent(MainActivity.this, RxHttpDemoActivity.class)));
        addClick(R.id.btn_main_pop, v -> startActivity(new Intent(MainActivity.this, PopDemoActivity.class)));
        addClick(R.id.btn_main_qrcode, v -> startActivity(new Intent(MainActivity.this, QRCodeDemoActivity.class)));
        addClick(R.id.btn_main_list, v -> startActivity(new Intent(MainActivity.this, ListDemoActivity.class)));
        addClick(R.id.btn_main_element, v -> startActivity(new Intent(MainActivity.this, ImageGridActivity.class)));
        addClick(R.id.btn_main_dialog, v -> startActivity(new Intent(MainActivity.this, DialogDemoActivity.class)));
        addClick(R.id.btn_main_encrypt, v -> startActivity(new Intent(MainActivity.this, EncryptActivity.class)));
        addClick(R.id.btn_main_state, v -> startActivity(new Intent(MainActivity.this, StateActivity.class)));
        //        addClick(R.id.btn_main_test, v -> startActivity(new Intent(MainActivity.this, TestActivity.class)));
        addClick(R.id.btn_main_test, v -> getPresenter().test());
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {
        //测试合并数据
        getPresenter().queryTestDataList();
        getPresenter().test();
    }

    @Override
    public void onClick(int id, View v)
    {
    }

    @Override
    protected void getIntentData(Intent intent, boolean newIntent)
    {

    }

}
