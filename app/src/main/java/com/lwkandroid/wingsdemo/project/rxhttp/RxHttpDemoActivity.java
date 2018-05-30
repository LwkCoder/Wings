package com.lwkandroid.wingsdemo.project.rxhttp;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.lwkandroid.rtpermission.RTPermission;
import com.lwkandroid.rtpermission.listener.OnPermissionResultListener;
import com.lwkandroid.wings.net.RxHttp;
import com.lwkandroid.wings.net.bean.ApiException;
import com.lwkandroid.wings.net.bean.ProgressInfo;
import com.lwkandroid.wings.net.observer.ApiBaseObserver;
import com.lwkandroid.wings.rx.utils.RxSchedulers;
import com.lwkandroid.wingsdemo.R;
import com.lwkandroid.wingsdemo.app.AppBaseActivity;
import com.lwkandroid.wingsdemo.bean.NonRestFulResult;
import com.lwkandroid.wingsdemo.bean.TabsBean;
import com.socks.library.KLog;

import java.io.File;
import java.util.List;

/**
 * 网络请求框架使用示例
 * 数据来源：https://github.com/jokermonn/-Api/blob/master/Neihan.md#recommend
 */
public class RxHttpDemoActivity extends AppBaseActivity<RxHttpDemoPresenter> implements RxHttpDemoConstract.View
{
    private TextView mTextView;

    @Override
    public int getContentViewId()
    {
        return R.layout.activity_rxhttp_demo;
    }

    @Override
    protected void initUI(View contentView)
    {
        super.initUI(contentView);
        mTextView = find(R.id.tv_rxhttp_demo);
        addClick(R.id.btn_rxhttp_demo01, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mPresenter.requestData();
            }
        });
        addClick(R.id.btn_rxhttp_demo02, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mPresenter.requestDataByService();
            }
        });
        addClick(R.id.btn_rxhttp_download, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new RTPermission.Builder()
                        .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .start(RxHttpDemoActivity.this, new OnPermissionResultListener()
                        {
                            @Override
                            public void onAllGranted(String[] allPermissions)
                            {
                                mPresenter.requestMovieData();
                            }

                            @Override
                            public void onDeined(String[] dinedPermissions)
                            {
                                showShortToast("不给权限我咋下载啊大兄弟");
                            }
                        });
            }
        });
        addClick(R.id.btn_rxhttp_demo03, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mPresenter.requestNonRestFul();
            }
        });
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {
        /*测试返回结果中data为空的情况*/
        RxHttp.GET("http://oi5vnsj5b.bkt.clouddn.com/empty_data_json")
                .addRemoveInterceptor("sign")
                .parseAsList(String.class)
                .compose(RxSchedulers.<List<String>>applyIo2Main())
                .subscribe(new ApiBaseObserver<List<String>>()
                {
                    @Override
                    public void _OnNext(List<String> strings)
                    {
                        KLog.e("结果：" + strings);
                    }

                    @Override
                    public void _OnError(ApiException e)
                    {
                        KLog.e("错误：" + e.toString());
                    }
                });
        //                .compose(RxSchedulers.<String>applyIo2Main())
        //                .subscribe(new ApiBaseObserver<String>()
        //                {
        //                    @Override
        //                    public void _OnNext(String s)
        //                    {
        //                        KLog.e("结果：" + s);
        //                    }
        //
        //                    @Override
        //                    public void _OnError(ApiException e)
        //                    {
        //                        KLog.e("错误：" + e);
        //                    }
        //                });
    }

    @Override
    protected void onClick(int id, View v)
    {
    }

    @Override
    protected RxHttpDemoPresenter createPresenter()
    {
        return new RxHttpDemoPresenter();
    }


    @Override
    public void setWeatherHttpResultData(List<TabsBean> dataList)
    {
        if (dataList == null)
            mTextView.setText(null);
        else
            mTextView.setText(dataList.toString());
    }

    @Override
    public void showDownloadResult(File file)
    {
        mTextView.setText("下载完成：" + file.getAbsolutePath());
    }

    @Override
    public void setDownLoadEnable(boolean enable)
    {
        find(R.id.btn_rxhttp_download).setEnabled(enable);
    }

    @Override
    public void showDownloadProgress(ProgressInfo info)
    {
        mTextView.setText("下载进度：" + info.getPercent() + "%");
    }

    @Override
    public void showNonRestFulResult(NonRestFulResult result)
    {
        if (result == null)
            mTextView.setText(null);
        else
            mTextView.setText(result.toString());
    }

    @Override
    public void showHttpError(ApiException e)
    {
        mTextView.setText("请求错误：" + e.toString());
    }
}
