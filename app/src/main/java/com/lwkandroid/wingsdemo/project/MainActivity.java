package com.lwkandroid.wingsdemo.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lwkandroid.imagepicker.utils.IImagePickerDisplayer;
import com.lwkandroid.widget.comactionbar.ComActionBar;
import com.lwkandroid.wingsdemo.R;
import com.lwkandroid.wingsdemo.app.AppBaseActivity;
import com.lwkandroid.wingsdemo.project.image.ImageLoaderDemoActivity;
import com.lwkandroid.wingsdemo.project.pop.PopDemoActivity;
import com.lwkandroid.wingsdemo.project.rxhttp.RxHttpDemoActivity;

/**
 * RxHttpDemoActivity
 */
public class MainActivity extends AppBaseActivity<MainPresenter> implements MainConstract.View
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
        super.initUI(contentView);
        mActionBar = find(R.id.comactionbar);
        addClick(R.id.btn_main_imageloader, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, ImageLoaderDemoActivity.class));
            }
        });
        addClick(R.id.btn_main_rxhttp, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, RxHttpDemoActivity.class));
            }
        });
        addClick(R.id.btn_main_pop, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, PopDemoActivity.class));
            }
        });

    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {
    }

    @Override
    protected void onClick(int id, View v)
    {
    }

    @Override
    protected MainPresenter createPresenter()
    {
        return new MainPresenter();
    }

    private class Loader implements IImagePickerDisplayer
    {

        @Override
        public void display(Context context, String url, ImageView imageView, int maxWidth, int maxHeight)
        {
            RequestOptions options = new RequestOptions();
            options.override(maxWidth, maxHeight);
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    .into(imageView);
        }

        @Override
        public void display(Context context, String url, ImageView imageView, int placeHolder, int errorHolder, int maxWidth, int maxHeight)
        {
            RequestOptions options = new RequestOptions();
            options.placeholder(placeHolder);
            options.error(errorHolder);
            options.override(maxWidth, maxHeight);
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    .into(imageView);
        }
    }
}
