package com.lwkandroid.wings.net.response;

import android.graphics.Bitmap;

import java.io.File;

import io.reactivex.Observable;

/**
 * Created by LWK
 * TODO 定义Byte数组网络请求结果的转换方法
 */

public interface IApiBytesArrayResponse
{
    /**
     * 直接返回Byte数组网络请求结果
     *
     * @return
     */
    Observable<byte[]> returnByteArrayResponse();

    /**
     * 将Bytes解析为File并保存
     */
    Observable<File> parseAsFileFromBytes();

    /**
     * 将Bytes解析为Bitmap
     */
    Observable<Bitmap> parseAsBitmapFromBytes();
}
