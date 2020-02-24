package com.lwkandroid.lib.core.net.parser;

import android.graphics.Bitmap;

import com.lwkandroid.lib.core.utils.ImageUtils;

import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * 将Byte数组的网络请求结果转换为Bitmap的实现类
 *
 * @author LWK
 */
public class ApiBytes2BitmapParser implements IApiBytesArrayParser.BitmapParser
{
    private int mMaxWidth, mMaxHeight;

    public ApiBytes2BitmapParser(int maxWidth, int maxHeight)
    {
        this.mMaxWidth = maxWidth;
        this.mMaxHeight = maxHeight;
    }

    @Override
    public ObservableTransformer<byte[], Bitmap> parseAsBitmap()
    {
        return upstream -> upstream.map((Function<byte[], Bitmap>) bytes -> {
            return ImageUtils.getBitmap(bytes, 0, mMaxWidth, mMaxHeight);
        });
    }
}
