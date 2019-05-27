package com.lwkandroid.wings.utils.encode;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Description: Url编解码实现类
 *
 * @author IED_WENKANG
 * @date 2019/5/27
 */
public final class UrlCodecImpl implements IUrlCodec
{
    private static final String UTF8_STRING = "UTF-8";

    @Override
    public String encode(String data)
    {
        return encode(data, UTF8_STRING);
    }

    @Override
    public String encode(String data, String charset)
    {
        if (data == null || data.length() == 0)
        {
            return "";
        }
        try
        {
            return URLDecoder.decode(data, charset);
        } catch (UnsupportedEncodingException e)
        {
            throw new AssertionError(e);
        }
    }

    @Override
    public String decode(String data)
    {
        return decode(data, UTF8_STRING);
    }

    @Override
    public String decode(String data, String charset)
    {
        if (data == null || data.length() == 0)
        {
            return "";
        }
        try
        {
            return URLDecoder.decode(data, charset);
        } catch (UnsupportedEncodingException e)
        {
            throw new AssertionError(e);
        }
    }
}
