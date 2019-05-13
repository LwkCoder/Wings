package com.lwkandroid.wings.net.convert;

import java.io.InputStream;

import io.reactivex.ObservableTransformer;
import okhttp3.ResponseBody;

/**
 * Created by LWK
 * 网络请求返回ResponseBody的转换类入口
 */

public final class ApiResponseConverter
{
    private static final AbsConverter<String> STRING_CONVERTER = new StringConverter();
    private static final AbsConverter<byte[]> BYTE_ARRAY_CONVERTER = new BytesConverter();
    private static final AbsConverter<InputStream> INPUT_STREAM_CONVERTER = new InputStreamConverter();

    /**
     * 将ResponseBody转换为String
     */
    public static ObservableTransformer<ResponseBody, String> responseToString()
    {
        return STRING_CONVERTER;
    }

    /**
     * 将ResponseBody转换为bytes
     */
    public static ObservableTransformer<ResponseBody, byte[]> responseToBytes()
    {
        return BYTE_ARRAY_CONVERTER;
    }

    /**
     * 将ResponseBody转换为inputStream
     */
    public static ObservableTransformer<ResponseBody, InputStream> responseToInputStream()
    {
        return INPUT_STREAM_CONVERTER;
    }
}
