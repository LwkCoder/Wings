package com.lwkandroid.demo2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lwkandroid.lib.core.java.imageloader.ImageLoader;
import com.lwkandroid.lib.core.java.imageloader.callback.ImageDownLoadCallBack;
import com.lwkandroid.lib.core.java.log.KLog;
import com.lwkandroid.lib.core.java.net.bean.ProgressInfo;
import com.lwkandroid.lib.core.java.net.listener.OnProgressListener;
import com.lwkandroid.lib.core.java.net.manager.OKProgressManger;

import java.io.File;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Description:
 *
 * @author 20180004
 * @date 2020/1/13
 */
public class MainActivity extends AppCompatActivity
{
    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.img_test);
        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                test();
            }
        });

        OKProgressManger.get().addDownloadListener("http://192.168.5.22:8080/mappp.png", new OnProgressListener()
        {
            @Override
            public void onProgress(ProgressInfo info)
            {
                KLog.e("info->" + info);
            }

            @Override
            public void onError(long id, Exception e)
            {
                KLog.e("e->" + e.toString());
            }
        });
    }

    private void test()
    {
//        RxHttp.DOWNLOAD("http://192.168.5.22:8080/mappp.png")
//                .parseAsFileFromBytes()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new ApiBaseObserver<File>()
//                {
//                    @Override
//                    public void subOnNext(File file)
//                    {
//                        KLog.e("file->" + file.getAbsolutePath());
//                    }
//
//                    @Override
//                    public void subOnError(ApiException e)
//                    {
//                        KLog.e(e.toString());
//                    }
//                });
        ImageLoader.downloadFile(this, "http://192.168.5.22:8080/mappp.png", new ImageDownLoadCallBack<File>()
        {
            @Override
            public void onImageDownloadStarted()
            {

            }

            @Override
            public void onImageDownloadSuccess(File data)
            {
                KLog.e("file->" + data.getAbsolutePath());
            }

            @Override
            public void onImageDownloadFailed()
            {

            }
        });
    }
}
