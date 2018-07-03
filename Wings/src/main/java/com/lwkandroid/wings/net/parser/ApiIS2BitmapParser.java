package com.lwkandroid.wings.net.parser;

import android.graphics.Bitmap;

import com.lwkandroid.wings.utils.CloseUtils;
import com.lwkandroid.wings.utils.ImageUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * Created by LWK
 * TODO 将InputSream的网络请求结果转换为Bitmap的实现类
 */

public class ApiIS2BitmapParser implements IApiInputStreamParser.BitmapParser
{
    private int mMaxWidth, mMaxHeight;

    public ApiIS2BitmapParser(int maxWidth, int maxHeight)
    {
        this.mMaxWidth = maxWidth;
        this.mMaxHeight = maxHeight;
    }

    @Override
    public ObservableTransformer<InputStream, Bitmap> parseAsBitmap()
    {
        return new ObservableTransformer<InputStream, Bitmap>()
        {
            @Override
            public ObservableSource<Bitmap> apply(Observable<InputStream> upstream)
            {
                return upstream.map(new Function<InputStream, Bitmap>()
                {
                    @Override
                    public Bitmap apply(InputStream inputStream) throws Exception
                    {
                        return ImageUtils.getBitmap(readStream(inputStream), 0, mMaxWidth, mMaxHeight);
                        //                        return ImageUtils.getBitmap(inputStream, mMaxWidth, mMaxHeight);
                    }
                });
            }
        };
    }

    /**
     * 将InputStream读取为字节数组
     */
    private byte[] readStream(InputStream inStream) throws Exception
    {
        if (inStream == null)
            return null;

        ByteArrayOutputStream outStream = null;
        try
        {
            outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1)
            {
                outStream.write(buffer, 0, len);
            }
            CloseUtils.closeIO(inStream);
            CloseUtils.closeIO(outStream);
            return outStream.toByteArray();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
