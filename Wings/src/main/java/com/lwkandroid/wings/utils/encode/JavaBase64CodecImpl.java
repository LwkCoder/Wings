package com.lwkandroid.wings.utils.encode;

import java.nio.charset.Charset;
import java.util.Base64;

import androidx.annotation.RequiresApi;

import static android.os.Build.VERSION_CODES.O;

/**
 * Description: Java上Base64编解码方法实现类
 *
 * @author LWK
 * @date 2019/5/27
 */
@RequiresApi(O)
public final class JavaBase64CodecImpl implements IBase64Codec
{
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public byte[] encode(byte[] data)
    {
        return Base64.getEncoder().encode(data);
    }

    @Override
    public String encode2String(byte[] data)
    {
        return encode2String(data, UTF8);
    }

    @Override
    public String encode2String(byte[] data, Charset charset)
    {
        return new String(encode(data), charset);
    }

    @Override
    public String encode2String(String data)
    {
        return encode2String(data, UTF8);
    }

    @Override
    public String encode2String(String data, Charset charset)
    {
        return encode2String(data.getBytes(charset), charset);
    }

    @Override
    public byte[] decode(byte[] base64Data)
    {
        return Base64.getDecoder().decode(base64Data);
    }

    @Override
    public byte[] decodeString(String base64String)
    {
        return decodeString(base64String, UTF8);
    }

    @Override
    public byte[] decodeString(String base64String, Charset charset)
    {
        return decode(base64String.getBytes(charset));
    }
}
