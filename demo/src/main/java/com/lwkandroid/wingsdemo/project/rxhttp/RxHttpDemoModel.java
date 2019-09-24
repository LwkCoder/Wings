package com.lwkandroid.wingsdemo.project.rxhttp;

import android.graphics.Bitmap;

import com.lwkandroid.wings.log.KLog;
import com.lwkandroid.wings.net.RxHttp;
import com.lwkandroid.wings.net.bean.ApiException;
import com.lwkandroid.wings.net.bean.ResultCacheWrapper;
import com.lwkandroid.wings.net.constants.ApiCacheMode;
import com.lwkandroid.wings.net.response.convert.ApiResponseBodyConverter;
import com.lwkandroid.wings.net.utils.FormDataMap;
import com.lwkandroid.wings.utils.SDCardUtils;
import com.lwkandroid.wings.utils.StringUtils;
import com.lwkandroid.wingsdemo.bean.AccessTokenBean;
import com.lwkandroid.wingsdemo.bean.NonRestFulResult;
import com.lwkandroid.wingsdemo.bean.TabsBean;
import com.lwkandroid.wingsdemo.net.ApiURL;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * RxHttpDemo Model层
 *
 * @author LWK
 */

public class RxHttpDemoModel extends RxHttpDemoContract.Model
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
                .parseDataList(TabsBean.class);
    }

    @Override
    Observable<List<TabsBean>> requestCustomGet()
    {
        return RxHttp.RETROFIT()
                .createWithGlobalOptions()
                .create(CustomService.class)
                .customGet(ApiURL.TEST, new FormDataMap().addParam("webp", "1"))
                .compose(ApiResponseBodyConverter.convertToString())//先将ResponseBody转为String结果的数据
                .compose(RxHttp.getGlobalOptions().getApiStringParser().parseRestfulDataList(TabsBean.class));//再将String数据解析为所需数据集合
    }

    @Override
    Observable<String> requestCustomPost()
    {
        Map<String, Object> map = new HashMap<>(2);
        map.put("account", "lwk");
        map.put("password", "123456");
        return RxHttp.RETROFIT()
                .createWithGlobalOptions()
                .create(CustomService.class)
                .customPost(ApiURL.CUSTOM_POST, map)
                .compose(ApiResponseBodyConverter.convertToString());
    }

    @Override
    Observable<File> requestFileData()
    {
        return RxHttp.DOWNLOAD(ApiURL.DOWNLOAD_TEST) //下载链接的请求域名和全局域名不一样没关系，内部retrofit会自动识别
                .ignoreAllGlobalFormData() //去除所有的全局参数，避免无法监听下载过程
                .addIgnoreInterceptors("sign") //去除模拟签名用的拦截器，避免无法监听下载过程
                .setFileName("app.apk")
                .setSaveFolderPath(SDCardUtils.getSDCardPath() + "/WingsDemo/")
                .parseAsFileFromIS();
    }

    @Override
    Observable<ResultCacheWrapper<NonRestFulResult>> requestNonRestFulData()
    {
        return RxHttp.GET(ApiURL.TEST02)
                .setCacheKey("TestKey")
                .setCacheTime(5000)
                .setCacheMode(ApiCacheMode.CACHE_FIRST_OR_REMOTE)
                .addFormData("code", "utf-8")
                .addFormData("q", "iphone")
                .parseDataObjectByCustomCache(NonRestFulResult.class);
    }

    @Override
    Observable<Bitmap> requestBitmapData()
    {
        return RxHttp.DOWNLOAD("http://oi5vnsj5b.bkt.clouddn.com/good_pic02.jpg")
                .ignoreAllGlobalFormData() //去除所有的全局参数，避免无法监听下载过程
                .addIgnoreInterceptors("ParamsInterceptor") //去除模拟签名用的拦截器，避免无法监听下载过程
                .setBitmapMaxSize(400, 400)
                .parseAsBitmapFromBytes();
    }

    @Override
    Observable<String> uploadImages(List<File> files)
    {
        return RxHttp.UPLOAD(ApiURL.UPLOAD_TEST)
                .setWriteTimeOut(600000)
                .setReadTimeOut(600000)
                .setConnectTimeOut(600000)
                .addFiles("image", files)
                .returnStringResponse();
    }

    @Override
    Observable<String> requestTestDataWithAccessToken()
    {
        //实际情况应该是网络请求时带上AccessToken，由服务端判定是否过期
        return Observable.create(emitter -> {
            //模拟服务端判定AccessToken是否可用
            AccessTokenBean bean = AccessTokenDao.get().getToken();
            if (bean == null || StringUtils.isEmpty(bean.getAccess_token()) || bean.isExpire())
            {
                KLog.e("服务端判定AccessToken不可用，需要刷新");
                emitter.onError(new ApiException(1000, "Access Token unavailable"));
            } else
            {
                emitter.onNext("SUCCESS");
                emitter.onComplete();
            }
        });
    }

    @Override
    Observable<String> requestHttps()
    {
        return RxHttp.GET("https://javatest.hqxapp.com/demo")
                .setAutoRetryCount(3)
                .returnStringResponse();
    }
}
