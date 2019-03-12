package com.lwkandroid.wingsdemo.project.rxhttp;

import android.graphics.Bitmap;

import com.lwkandroid.wings.log.KLog;
import com.lwkandroid.wings.net.bean.ApiException;
import com.lwkandroid.wings.net.bean.ApiResultCacheWrapper;
import com.lwkandroid.wings.net.bean.ProgressInfo;
import com.lwkandroid.wings.net.listener.OnProgressListener;
import com.lwkandroid.wings.net.manager.OkProgressManger;
import com.lwkandroid.wings.net.observer.ApiBaseObserver;
import com.lwkandroid.wingsdemo.bean.NonRestFulResult;
import com.lwkandroid.wingsdemo.bean.TabsBean;
import com.lwkandroid.wingsdemo.net.ApiURL;

import java.io.File;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by LWK
 * TODO RxHttpDemo Presenter层
 */

public class RxHttpDemoPresenter extends RxHttpDemoContract.Presenter
{
    @Override
    void requestData()
    {
        getModelImpl().requestData()
                .compose(this.<List<TabsBean>>applyIo2MainWithLifeCycle())
                .subscribe(new ApiBaseObserver<List<TabsBean>>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                        super.onSubscribe(d);
                        getViewImpl().setWeatherHttpResultData(null);
                    }

                    @Override
                    public void _onNext(List<TabsBean> dataList)
                    {
                        getViewImpl().setWeatherHttpResultData(dataList);
                    }

                    @Override
                    public void onApiException(ApiException e)
                    {
                        KLog.e("无法获取数据：" + e.toString());
                        getViewImpl().showHttpError(e);
                    }
                });
    }

    @Override
    void requestDataByService()
    {
        getModelImpl().requestDataByService()
                .compose(this.<List<TabsBean>>applyIo2MainWithLifeCycle())
                .subscribe(new ApiBaseObserver<List<TabsBean>>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                        super.onSubscribe(d);
                        getViewImpl().setWeatherHttpResultData(null);
                    }

                    @Override
                    public void _onNext(List<TabsBean> dataList)
                    {
                        getViewImpl().setWeatherHttpResultData(dataList);
                    }

                    @Override
                    public void onApiException(ApiException e)
                    {
                        getViewImpl().showHttpError(e);
                    }
                });
    }

    @Override
    void requestFileData()
    {
        getModelImpl().requestFileData()
                .compose(this.<File>applyIo2MainWithLifeCycle())
                .subscribe(new ApiBaseObserver<File>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                        super.onSubscribe(d);
                        getViewImpl().setDownLoadEnable(false);
                    }

                    @Override
                    public void _onNext(File file)
                    {
                        getViewImpl().showDownloadResult(file);
                        getViewImpl().setDownLoadEnable(true);
                    }

                    @Override
                    public void onApiException(ApiException e)
                    {
                        getViewImpl().showHttpError(e);
                        getViewImpl().setDownLoadEnable(true);
                    }
                });

        OkProgressManger.get().addDownloadListener(ApiURL.DOWNLOAD_TEST, new OnProgressListener()
        {
            @Override
            public void onProgress(ProgressInfo info)
            {
                getViewImpl().showDownloadProgress(info);
            }

            @Override
            public void onError(long id, Exception e)
            {
                KLog.e("监听下载时发生错误：" + e.toString());
            }
        });
    }

    @Override
    void requestNonRestFul()
    {
        getModelImpl().requestNonRestFulData()
                .compose(this.<ApiResultCacheWrapper<NonRestFulResult>>applyIo2MainWithLifeCycle())
                .subscribe(new ApiBaseObserver<ApiResultCacheWrapper<NonRestFulResult>>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                        super.onSubscribe(d);
                        getViewImpl().showNonRestFulResult(null);
                    }

                    @Override
                    public void _onNext(ApiResultCacheWrapper<NonRestFulResult> resultBean)
                    {
                        KLog.i("是否为缓存：" + resultBean.isCache());
                        getViewImpl().showNonRestFulResult(resultBean.getData());
                    }

                    @Override
                    public void onApiException(ApiException e)
                    {
                        getViewImpl().showHttpError(e);
                    }
                });
        //                .subscribe(new ApiBaseObserver<NonRestFulResult>()
        //                {
        //                    @Override
        //                    public void onSubscribe(Disposable d)
        //                    {
        //                        super.onSubscribe(d);
        //                        getViewImpl().showNonRestFulResult(null);
        //                    }
        //
        //                    @Override
        //                    public void _onNext(NonRestFulResult nonRestFulResult)
        //                    {
        //                        KLog.e();
        //                        getViewImpl().showNonRestFulResult(nonRestFulResult);
        //                    }
        //
        //                    @Override
        //                    public void onApiException(ApiException e)
        //                    {
        //                        getViewImpl().showHttpError(e);
        //                    }
        //                });
    }

    @Override
    void requestBitmapData()
    {
        getModelImpl().requestBitmapData()
                .compose(this.<Bitmap>applyIo2MainWithLifeCycle())
                .subscribe(new ApiBaseObserver<Bitmap>()
                {
                    @Override
                    public void _onNext(Bitmap bitmap)
                    {
                        getViewImpl().showImageBitmap(bitmap);
                    }

                    @Override
                    public void onApiException(ApiException e)
                    {
                        getViewImpl().showHttpError(e);
                    }
                });
    }

    @Override
    void uploadImages(List<File> files)
    {
        getModelImpl().uploadImages(files)
                .compose(this.<String>applyIo2MainWithLifeCycle())
                .subscribe(new ApiBaseObserver<String>()
                {
                    @Override
                    public void _onNext(String s)
                    {
                        getViewImpl().showShortToast("上传成功");
                    }

                    @Override
                    public void onApiException(ApiException e)
                    {
                        getViewImpl().showHttpError(e);
                    }
                });
        OkProgressManger.get().addUploadListener(ApiURL.UPLOAD_TEST, new OnProgressListener()
        {
            @Override
            public void onProgress(ProgressInfo info)
            {
                getViewImpl().showUploadProgress(info);
            }

            @Override
            public void onError(long id, Exception e)
            {
                KLog.e("监听上传时发生错误：" + e.toString());
            }
        });
    }

    @Override
    public RxHttpDemoContract.Model createModel()
    {
        return new RxHttpDemoModel();
    }

    @Override
    public void onDestoryPresenter()
    {
        super.onDestoryPresenter();
        OkProgressManger.get().removeDownloadListener(ApiURL.DOWNLOAD_TEST);
        OkProgressManger.get().removeUploadListener(ApiURL.UPLOAD_TEST);
    }
}
