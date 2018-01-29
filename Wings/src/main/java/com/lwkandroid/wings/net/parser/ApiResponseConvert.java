package com.lwkandroid.wings.net.parser;

import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * Created by LWK
 * TODO 网络请求返回ResponseBody的转换类
 */

public class ApiResponseConvert
{
    /**
     * 将ResponseBody转换为String
     */
    public static ObservableTransformer<ResponseBody, String> responseToString()
    {
        return new ObservableTransformer<ResponseBody, String>()
        {
            @Override
            public ObservableSource<String> apply(Observable<ResponseBody> upstream)
            {
                return upstream.flatMap(new Function<ResponseBody, ObservableSource<String>>()
                {
                    @Override
                    public ObservableSource<String> apply(ResponseBody body) throws Exception
                    {
                        return Observable.just(body.string());
                    }
                });
            }
        };
    }

    /**
     * 将ResponseBody转换为bytes
     */
    public static ObservableTransformer<ResponseBody, byte[]> responseToBytes()
    {
        return new ObservableTransformer<ResponseBody, byte[]>()
        {
            @Override
            public ObservableSource<byte[]> apply(Observable<ResponseBody> upstream)
            {
                return upstream.flatMap(new Function<ResponseBody, ObservableSource<byte[]>>()
                {
                    @Override
                    public ObservableSource<byte[]> apply(ResponseBody body) throws Exception
                    {
                        return Observable.just(body.bytes());
                    }
                });
            }
        };
    }

    /**
     * 将ResponseBody转换为inputStream
     */
    public static ObservableTransformer<ResponseBody, InputStream> responseToInputStream()
    {
        return new ObservableTransformer<ResponseBody, InputStream>()
        {
            @Override
            public ObservableSource<InputStream> apply(Observable<ResponseBody> upstream)
            {
                return upstream.flatMap(new Function<ResponseBody, ObservableSource<InputStream>>()
                {
                    @Override
                    public ObservableSource<InputStream> apply(ResponseBody body) throws Exception
                    {
                        return Observable.just(body.byteStream());
                    }
                });
            }
        };
    }
}
