package com.lwkandroid.wingsdemo.project.rxhttp;

import android.graphics.Bitmap;

import com.lwkandroid.wings.net.RxHttp;
import com.lwkandroid.wings.net.bean.ApiResultCacheWrapper;
import com.lwkandroid.wings.net.constants.ApiCacheMode;
import com.lwkandroid.wings.net.convert.ApiResponseConvert;
import com.lwkandroid.wings.net.utils.FormDataMap;
import com.lwkandroid.wings.utils.SDCardUtils;
import com.lwkandroid.wingsdemo.bean.NonRestFulResult;
import com.lwkandroid.wingsdemo.bean.TabsBean;
import com.lwkandroid.wingsdemo.net.ApiURL;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by LWK
 * TODO RxHttpDemo Model层
 */

public class RxHttpDemoModel extends RxHttpDemoConstract.Model
{
    @Override
    Observable<List<TabsBean>> requestData()
    {
        return RxHttp.GET(ApiURL.TEST)
                .addFormData("webp", "1")
                .addFormData("essence", "1")
                .addFormData("message_cursor", "1")
                .addFormData("double_col_mode", "1")
                .setAutoRetryCount(5)
                .parseAsList(TabsBean.class);
    }

    @Override
    Observable<List<TabsBean>> requestDataByService()
    {
        return RxHttp.RETROFIT()
                .createWithGlobalOptions()
                .create(CustomService.class)
                .customGet(ApiURL.TEST, new FormDataMap().addParam("webp", "1"))
                .compose(ApiResponseConvert.responseToString())//先将ResponseBody转为String结果的数据
                .compose(RxHttp.getGlobalOptions().getApiStringParser().parseAsList(TabsBean.class));//再将String数据解析为所需数据集合
    }

    @Override
    Observable<File> requestMovieData()
    {
        return RxHttp.DOWNLOAD(ApiURL.DOWNLOAD_TEST) //下载链接的请求域名和全局域名不一样没关系，内部retrofit会自动识别
                .removeAllGlobalFormDatas() //去除所有的全局参数，避免无法监听下载过程
                .addRemoveInterceptors("sign") //去除模拟签名用的拦截器，避免无法监听下载过程
                .setFileName("Movie.mp4")
                .setSaveFloderPath(SDCardUtils.getSDCardPath() + "/WingsDemo/")
                .parseAsFileFromIS();
    }

    @Override
    Observable<ApiResultCacheWrapper<NonRestFulResult>> requestNonRestFulData()
    {
        return RxHttp.GET(ApiURL.TEST02)
                .setCachekey("TestKey")
                .setCacheTime(5000)
                .setCacheMode(ApiCacheMode.CACHE_FIRST_OR_REMOTE)
                .addFormData("code", "utf-8")
                .addFormData("q", "iphone")
                .setApiResultStringParser(new NonRestFulStringResultParser())
                //                .parseAsObject(NonRestFulResult.class);
                .parseAsObjectWithCacheWraped(NonRestFulResult.class);
    }

    @Override
    Observable<Bitmap> requestBitmapData()
    {
        return RxHttp.DOWNLOAD("http://oi5vnsj5b.bkt.clouddn.com/good_pic02.jpg")
                .removeAllGlobalFormDatas() //去除所有的全局参数，避免无法监听下载过程
                .addRemoveInterceptors("sign") //去除模拟签名用的拦截器，避免无法监听下载过程
                .setBitmapMaxSize(400, 400)
                .parseAsBitmapFromBytes();
    }
}
