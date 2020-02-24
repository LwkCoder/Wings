package com.lwkandroid.lib.core.net.response.convert;

import java.io.InputStream;

import io.reactivex.ObservableTransformer;
import okhttp3.ResponseBody;

/**
 * 网络请求返回ResponseBody的转换类入口
 *
 * @author LWK
 */

public final class ApiResponseBodyConverter
{
    private static final AbsApiResponseBodyConverter<String> STRING_CONVERTER = new StringResponseBodyConverter();
    private static final AbsApiResponseBodyConverter<byte[]> BYTE_ARRAY_CONVERTER = new BytesResponseBodyConverter();
    private static final AbsApiResponseBodyConverter<InputStream> INPUT_STREAM_CONVERTER = new InputStreamResponseBodyConverter();

    /**
     * 将ResponseBody转换为String
     */
    public static ObservableTransformer<ResponseBody, String> convertToString()
    {
        return STRING_CONVERTER;
    }

    /**
     * 将ResponseBody转换为bytes
     */
    public static ObservableTransformer<ResponseBody, byte[]> convertToBytes()
    {
        return BYTE_ARRAY_CONVERTER;
    }

    /**
     * 将ResponseBody转换为inputStream
     */
    public static ObservableTransformer<ResponseBody, InputStream> convertToInputStream()
    {
        return INPUT_STREAM_CONVERTER;
    }
}
