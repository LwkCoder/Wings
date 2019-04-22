package com.lwkandroid.wings.net.convert;

import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * Created by LWK
 *  网络请求返回ResponseBody的转换类入口
 */

public final class ApiResponseConvert
{
    private static final IApiResponseConvert<String> STRING_CONVERTER = new StringConverter();
    private static final IApiResponseConvert<byte[]> BYTE_ARRAY_CONVERTER = new BytesArrayConverter();
    private static final IApiResponseConvert<InputStream> INPUT_STREAM_CONVERTER = new InputStreamConverter();

    /**
     * 将ResponseBody转换为String
     */
    public static ObservableTransformer<ResponseBody, String> responseToString()
    {
        return STRING_CONVERTER.convert();
    }

    /**
     * 将ResponseBody转换为bytes
     */
    public static ObservableTransformer<ResponseBody, byte[]> responseToBytes()
    {
        return BYTE_ARRAY_CONVERTER.convert();
    }

    /**
     * 将ResponseBody转换为inputStream
     */
    public static ObservableTransformer<ResponseBody, InputStream> responseToInputStream()
    {
        return INPUT_STREAM_CONVERTER.convert();
    }

    /**
     * 将网络请求结果得到的ResonpseBody转为String的实现类
     */
    public static final class StringConverter implements IApiResponseConvert<String>
    {
        @Override
        public ObservableTransformer<ResponseBody, String> convert()
        {
            return new ObservableTransformer<ResponseBody, String>()
            {
                @Override
                public ObservableSource<String> apply(Observable<ResponseBody> upstream)
                {
                    return upstream.map(new Function<ResponseBody, String>()
                    {
                        @Override
                        public String apply(ResponseBody responseBody) throws Exception
                        {
                            return responseBody.string();
                        }
                    });
                }
            };
        }
    }

    /**
     * 将网络请求结果得到的ResonpseBody转为Byte[]的类
     */
    public static final class BytesArrayConverter implements IApiResponseConvert<byte[]>
    {
        @Override
        public ObservableTransformer<ResponseBody, byte[]> convert()
        {
            return new ObservableTransformer<ResponseBody, byte[]>()
            {
                @Override
                public ObservableSource<byte[]> apply(Observable<ResponseBody> upstream)
                {
                    return upstream.map(new Function<ResponseBody, byte[]>()
                    {
                        @Override
                        public byte[] apply(ResponseBody responseBody) throws Exception
                        {
                            return responseBody.bytes();
                        }
                    });
                }
            };
        }
    }

    /**
     * 将网络请求结果得到的ResonpseBody转为InputStream的类
     */
    public static final class InputStreamConverter implements IApiResponseConvert<InputStream>
    {
        @Override
        public ObservableTransformer<ResponseBody, InputStream> convert()
        {
            return new ObservableTransformer<ResponseBody, InputStream>()
            {
                @Override
                public ObservableSource<InputStream> apply(Observable<ResponseBody> upstream)
                {
                    return upstream.map(new Function<ResponseBody, InputStream>()
                    {
                        @Override
                        public InputStream apply(ResponseBody responseBody) throws Exception
                        {
                            return responseBody.byteStream();
                        }
                    });
                }
            };
        }
    }
}
