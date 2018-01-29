package com.lwkandroid.wings.net.parser;

import com.lwkandroid.wings.net.bean.ApiException;
import com.lwkandroid.wings.net.constants.ApiExceptionCode;
import com.lwkandroid.wings.utils.StringUtils;
import com.lwkandroid.wings.utils.Utils;
import com.socks.library.KLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * Created by LWK
 * TODO 将InputSream转换为File并写入存储的实现类
 */

public class ApiFileParser implements IApiInputStreamParser
{
    private String mSavePath;
    private String mFileName;

    public ApiFileParser(String savePath, String fileName)
    {
        this.mSavePath = savePath;
        this.mFileName = fileName;
    }

    @Override
    public ObservableTransformer<InputStream, File> parseDataAsFile()
    {
        return new ObservableTransformer<InputStream, File>()
        {
            @Override
            public ObservableSource<File> apply(Observable<InputStream> upstream)
            {
                return upstream.flatMap(new Function<InputStream, ObservableSource<File>>()
                {
                    @Override
                    public ObservableSource<File> apply(InputStream inputStream) throws Exception
                    {
                        return Observable.just(writeIntoStroage(inputStream));
                    }
                });
            }
        };
    }

    //将流写入存储
    private File writeIntoStroage(InputStream inputStream) throws ApiException
    {
        if (StringUtils.isEmpty(mFileName))
            mFileName = String.valueOf(System.currentTimeMillis());

        if (mSavePath == null)
        {
            mSavePath = Utils.getContext().getExternalFilesDir(null) + File.separator + mFileName;
        } else
        {
            File file = new File(mSavePath);
            if (!file.exists())
                file.mkdirs();
            mSavePath = mSavePath + File.separator + mFileName;
            mSavePath = mSavePath.replaceAll("//", "/");
        }
        KLog.i("ApiFileParser create absolutely path = " + mSavePath);

        try
        {
            File file = new File(mSavePath);
            OutputStream outputStream = null;

            try
            {
                byte[] fileReader = new byte[4096];
                outputStream = new FileOutputStream(file);

                while (true)
                {
                    int read = inputStream.read(fileReader);
                    if (read == -1)
                        break;

                    outputStream.write(fileReader, 0, read);
                }

                outputStream.flush();
                KLog.i("ApiFileParser finish absolutely path = " + mSavePath);

                return file;
            } catch (IOException e)
            {
                throw new ApiException(ApiExceptionCode.IO_EXCEPTION, e.toString());
            } finally
            {
                if (inputStream != null)
                    inputStream.close();
                if (outputStream != null)
                    outputStream.close();
            }
        } catch (IOException e)
        {
            throw new ApiException(ApiExceptionCode.IO_EXCEPTION, e.toString());
        }
    }

}
