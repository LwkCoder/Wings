package com.lwkandroid.wingsdemo.project.rxhttp;

import android.graphics.Bitmap;

import com.lwkandroid.wings.net.bean.ApiResultCacheWrapper;
import com.lwkandroid.wings.net.bean.ApiException;
import com.lwkandroid.wings.net.bean.ProgressInfo;
import com.lwkandroid.wingsdemo.app.AppBaseModel;
import com.lwkandroid.wingsdemo.app.AppBasePresenter;
import com.lwkandroid.wingsdemo.app.IAppBaseView;
import com.lwkandroid.wingsdemo.bean.NonRestFulResult;
import com.lwkandroid.wingsdemo.bean.TabsBean;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by LWK
 * TODO RxHttpDemo契约类
 */

public interface RxHttpDemoContract
{
    interface View extends IAppBaseView
    {
        void setWeatherHttpResultData(List<TabsBean> dataList);

        void showDownloadResult(File file);

        void setDownLoadEnable(boolean enable);

        void showDownloadProgress(ProgressInfo info);

        void showUploadProgress(ProgressInfo info);

        void showNonRestFulResult(NonRestFulResult result);

        void showImageBitmap(Bitmap bitmap);

        void showHttpError(ApiException e);
    }

    abstract class Model extends AppBaseModel
    {
        /**
         * 获取的数据来自开源接口：https://github.com/jokermonn/-Api/blob/master/Neihan.md#recommend
         */
        abstract Observable<List<TabsBean>> requestData();

        /**
         * 获取的数据来自开源接口：https://github.com/jokermonn/-Api/blob/master/Neihan.md#recommend
         */
        abstract Observable<List<TabsBean>> requestDataByService();

        /**
         * 七牛上的静态资源
         */
        abstract Observable<File> requestFileData();

        /**
         * 请求非RestFul风格的数据
         */
        abstract Observable<ApiResultCacheWrapper<NonRestFulResult>> requestNonRestFulData();

        abstract Observable<Bitmap> requestBitmapData();

        abstract Observable<String> uploadImages(List<File> files);
    }

    abstract class Presenter extends AppBasePresenter<View, Model>
    {
        abstract void requestData();

        abstract void requestDataByService();

        abstract void requestFileData();

        abstract void requestNonRestFul();

        abstract void requestBitmapData();

        abstract void uploadImages(List<File> files);
    }
}
