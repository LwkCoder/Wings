package com.lwkandroid.lib.core.java.net.parser;

import com.lwkandroid.lib.core.java.log.KLog;
import com.lwkandroid.lib.core.java.net.bean.ApiException;
import com.lwkandroid.lib.core.java.net.constants.ApiExceptionCode;
import com.lwkandroid.lib.core.java.utils.FileIOUtils;
import com.lwkandroid.lib.core.java.utils.PathUtils;
import com.lwkandroid.lib.core.java.utils.StringUtils;

import java.io.File;

import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * 将Byte数组类型的网络请求结果转换为File并写入存储的实现类
 *
 * @author LWK
 */
public class ApiBytes2FileParser implements IApiBytesArrayParser.FileParser
{
    private String mSavePath;
    private String mFileName;

    public ApiBytes2FileParser(String savePath, String fileName)
    {
        this.mSavePath = savePath;
        this.mFileName = fileName;
    }

    @Override
    public ObservableTransformer<byte[], File> parseAsFile()
    {
        return upstream -> upstream.map((Function<byte[], File>) this::writeIntoStorage);
    }

    /**
     * 将数据写入存储
     */
    private File writeIntoStorage(byte[] bytes) throws ApiException
    {
        if (StringUtils.isEmpty(mFileName))
        {
            mFileName = String.valueOf(System.currentTimeMillis());
        }
        if (StringUtils.isEmpty(mSavePath))
        {
            mSavePath = PathUtils.getExternalAppFilesPath();
        }
        mSavePath = mSavePath.replaceAll("//", "/");

        try
        {
            File file = new File(mSavePath, mFileName);
            KLog.i("ApiBytes2FileParser create absolutely path = " + file.getAbsolutePath());
            if (FileIOUtils.writeFileFromBytesByStream(file, bytes))
            {
                return file;
            } else
            {
                throw new ApiException(ApiExceptionCode.IO_EXCEPTION, null);
            }
        } catch (Exception e)
        {
            KLog.e("ApiBytes2FileParser writeIntoStorage exception:" + e.toString());
            throw new ApiException(ApiExceptionCode.IO_EXCEPTION, e.toString());
        }
    }
}
