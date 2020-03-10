package com.lwkandroid.lib.core.net.response.func;

import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Description:ResponseBody转InputStream的方法
 *
 * @author LWK
 */
final class InputStreamResponseConverter extends AbsResponseConverter<InputStream>
{
    @Override
    public InputStream convert(ResponseBody body) throws Exception
    {
        return body.byteStream();
    }
}
