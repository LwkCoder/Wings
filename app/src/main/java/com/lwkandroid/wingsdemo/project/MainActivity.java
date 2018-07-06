package com.lwkandroid.wingsdemo.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lwkandroid.imagepicker.ImagePicker;
import com.lwkandroid.imagepicker.data.ImageBean;
import com.lwkandroid.imagepicker.data.ImagePickType;
import com.lwkandroid.imagepicker.utils.IImagePickerDisplayer;
import com.lwkandroid.widget.comactionbar.ComActionBar;
import com.lwkandroid.wings.net.RxHttp;
import com.lwkandroid.wings.net.bean.ApiException;
import com.lwkandroid.wings.net.bean.ProgressInfo;
import com.lwkandroid.wings.net.listener.OnProgressListener;
import com.lwkandroid.wings.net.manager.OkProgressManger;
import com.lwkandroid.wings.net.observer.ApiBaseObserver;
import com.lwkandroid.wings.rx.utils.RxSchedulers;
import com.lwkandroid.wingsdemo.R;
import com.lwkandroid.wingsdemo.app.AppBaseActivity;
import com.lwkandroid.wingsdemo.project.image.ImageLoaderDemoActivity;
import com.lwkandroid.wingsdemo.project.rxhttp.RxHttpDemoActivity;
import com.socks.library.KLog;

import java.io.File;
import java.util.List;

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

        addClick(R.id.btn_image, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new ImagePicker()
                        .pickType(ImagePickType.SINGLE)
                        .displayer(new Loader())
                        .start(MainActivity.this, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null)
        {
            List<ImageBean> list = data.getParcelableArrayListExtra(ImagePicker.INTENT_RESULT_DATA);
            KLog.e("选择的图片：" + list.toString());
            uploadImage(list.get(0).getImagePath());
        }
    }

    String url = "http://192.168.3.189:8081/user/upload";

    private void uploadImage(String path)
    {
        RxHttp.UPLOAD(url)
                .addFile("image_data", new File(path))
                .parseAsObject(String.class)
                .compose(RxSchedulers.<String>applyIo2Main())
                .subscribe(new ApiBaseObserver<String>()
                {
                    @Override
                    public void _OnNext(String s)
                    {
                        KLog.e("s==" + s);
                    }

                    @Override
                    public void onApiExcetion(ApiException e)
                    {
                        KLog.e("错误：" + e.toString());
                    }
                });
        OkProgressManger.get().addDownloadListener(url, new OnProgressListener()
        {
            @Override
            public void onProgress(ProgressInfo info)
            {
                KLog.e("进度：" + info.getPercent());
            }

            @Override
            public void onError(long id, Exception e)
            {
                KLog.e("无法追踪进度：id=" + id + " e=" + e.toString());
            }
        });
    }

    @Override
    protected void onDestroy()
    {
        OkProgressManger.get().removeDownloadListener(url);
        super.onDestroy();
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
