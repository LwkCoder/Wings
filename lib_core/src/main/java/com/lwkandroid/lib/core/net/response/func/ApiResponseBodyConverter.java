package com.lwkandroid.lib.core.net.response.func;

import java.io.InputStream;

import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * 网络请求返回ResponseBody的转换类入口
 *
 * @author LWK
 */

public final class ApiResponseBodyConverter
{
    private static final StringResponseConverter STRING_CONVERTER = new StringResponseConverter();
    private static final BytesResponseConverter BYTE_ARRAY_CONVERTER = new BytesResponseConverter();
    private static final InputStreamResponseConverter INPUT_STREAM_CONVERTER = new InputStreamResponseConverter();

    /**
     * 将ResponseBody转换为String
     */
    public static Function<ResponseBody, String> convertToString()
    {
        return STRING_CONVERTER;
    }

    /**
     * 将ResponseBody转换为bytes
     */
    public static Function<ResponseBody, byte[]> convertToBytes()
    {
        return BYTE_ARRAY_CONVERTER;
    }

    /**
     * 将ResponseBody转换为inputStream
     */
    public static Function<ResponseBody, InputStream> convertToInputStream()
    {
        return INPUT_STREAM_CONVERTER;
    }
}
