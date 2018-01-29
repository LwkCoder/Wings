package com.lwkandroid.wings.net.parser;

import java.io.File;
import java.io.InputStream;

import io.reactivex.ObservableTransformer;

/**
 * Created by LWK
 * TODO 将InputStream类型的网络请求结果转换为Fil的接口
 */

public interface IApiInputStreamParser
{
    ObservableTransformer<InputStream, File> parseDataAsFile();
}
