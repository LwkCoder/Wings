package com.lwkandroid.wings.net.response.convert;

import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Description:ResponseBody转InputStream的方法
 *
 * @author LWK
 * @date 2019/5/13
 */
 final class InputStreamResponseBodyConverter extends AbsApiResponseBodyConverter<InputStream>
{
    @Override
    public InputStream convert(ResponseBody response) throws Exception
    {
        return response.byteStream();
    }
}
