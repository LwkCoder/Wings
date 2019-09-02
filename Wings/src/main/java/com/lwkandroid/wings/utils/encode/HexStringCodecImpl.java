package com.lwkandroid.wings.utils.encode;

import java.nio.charset.Charset;

/**
 * Description: 16进制编解码实现类
 *
 * @author LWK
 * @date 2019/5/28
 */
public final class HexStringCodecImpl implements IHexStringCodec
{
    private static final String HEX_STRING = "0123456789ABCDEF";
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
    public String decodeToString(String hexString)
    {
        return new String(decodeToBytes(hexString), UTF8);
    }

    @Override
    public byte[] decodeToBytes(String hexString)
    {
        return decodeToBytes(hexString, UTF8);
    }

    @Override
    public String decodeToString(String hexString, Charset charset)
    {
        return new String(decodeToBytes(hexString, charset), charset);
    }

    @Override
    public byte[] decodeToBytes(String hexString, Charset charset)
    {
        if (hexString == null || hexString.length() == 0)
        {
            return new byte[0];
        }
        hexString = hexString.toUpperCase();
        char[] chars = hexString.toCharArray();
        byte[] bytes = new byte[chars.length / 2];
        for (int i = 0, n, length = bytes.length; i < length; i++)
        {
            n = HEX_STRING.indexOf(chars[2 * i]) * 16 + HEX_STRING.indexOf(chars[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return bytes;
    }

}
