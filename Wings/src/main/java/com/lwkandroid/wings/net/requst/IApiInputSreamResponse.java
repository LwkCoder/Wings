package com.lwkandroid.wings.net.requst;

import java.io.File;

import io.reactivex.Observable;

/**
 * Created by LWK
 * TODO 定义InputStream网络请求结果的转换方法
 */

public interface IApiInputSreamResponse
{
    Observable<File> parseAsFile();
}
