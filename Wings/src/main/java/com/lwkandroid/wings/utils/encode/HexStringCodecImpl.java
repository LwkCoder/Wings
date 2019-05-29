package com.lwkandroid.wings.utils.encode;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

/**
 * Description: 16进制编解码实现类
 *
 * @author LWK
 * @date 2019/5/28
 */
public final class HexStringCodecImpl implements IHexStringCodec
{
    private static final String HEX_STRING = "0123456789abcdef";
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public String encode(byte[] data)
    {
        if (data == null || data.length == 0)
        {
            return "";
        }
        StringBuilder sb = new StringBuilder(data.length * 2);
        //将字节数组中每个字节拆解成2位16进制整数
        for (byte b : data)
        {
            sb.append(HEX_STRING.charAt((b & 0xf0) >> 4));
            sb.append(HEX_STRING.charAt((b & 0x0f)));
        }
        return sb.toString();
    }

    @Override
    public String encode(String data)
    {
        return encode(data, UTF8);
    }

    @Override
    public String encode(String data, Charset charset)
    {
        if (data == null || data.length() == 0)
        {
            return "";
        }
        return encode(data.getBytes(charset));
    }

    @Override
    public String decode(String hexString)
    {
        return decode(hexString, UTF8);
    }

    @Override
    public String decode(String hexString, Charset charset)
    {
        if (hexString == null || hexString.length() == 0)
        {
            return "";
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream(hexString.length() / 2);
        //将每2位16进制整数组装成一个字节
        for (int i = 0, length = hexString.length(); i < length; i += 2)
        {
            stream.write((hexString.indexOf(hexString.charAt(i)) << 4 | hexString.indexOf(hexString.charAt(i + 1))));
        }
        return new String(stream.toByteArray(), charset);
    }

}
