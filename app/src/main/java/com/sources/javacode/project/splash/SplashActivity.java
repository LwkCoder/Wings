package com.sources.javacode.project.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lwkandroid.lib.core.kt.utils.AppUtils;
import com.sources.javacode.R;
import com.sources.javacode.app.AppBaseActivity;

/**
 * View层
 *
 * @author LWK
 */
public class SplashActivity extends AppBaseActivity<SplashPresenter> implements SplashContract.IView
{

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
        ImageView imageView = find(R.id.img);
        imageView.setImageDrawable(AppUtils.Companion.getAppIcon());
    }

    @Override
    protected void initData(Bundle savedInstanceState)
    {

    }

    @Override
    public void onClick(int id, View view)
    {

    }

}
