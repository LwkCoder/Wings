package com.lwkandroid.wings.net.response;

import android.graphics.Bitmap;

import java.io.File;

import io.reactivex.Observable;

/**
 * Created by LWK
 * TODO 定义InputStream网络请求结果的转换方法
 */

public interface IApiInputSreamResponse
{
    /**
     * 将InputStream解析为File并保存
     */
    Observable<File> parseAsFileFromIS();

    /**
     * 将InputStream解析为Bitmap
     */
    Observable<Bitmap> parseAsBitmapFromIS();
}
