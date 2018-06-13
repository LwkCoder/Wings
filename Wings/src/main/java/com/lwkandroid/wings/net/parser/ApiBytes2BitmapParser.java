package com.lwkandroid.wings.net.parser;

import android.graphics.Bitmap;

import com.lwkandroid.wings.utils.ImageUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * Created by LWK
 * TODO 将Byte数组的网络请求结果转换为Bitmap的实现类
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
        return new ObservableTransformer<byte[], Bitmap>()
        {
            @Override
            public ObservableSource<Bitmap> apply(Observable<byte[]> upstream)
            {
                return upstream.map(new Function<byte[], Bitmap>()
                {
                    @Override
                    public Bitmap apply(byte[] bytes) throws Exception
                    {
                        return ImageUtils.getBitmap(bytes, 0, mMaxWidth, mMaxHeight);
                    }
                });
            }
        };
    }
}
