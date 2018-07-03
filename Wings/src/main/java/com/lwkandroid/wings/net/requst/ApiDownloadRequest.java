package com.lwkandroid.wings.net.requst;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.lwkandroid.wings.net.ApiService;
import com.lwkandroid.wings.net.constants.ApiRequestType;
import com.lwkandroid.wings.net.error.ApiErrorTransformer;
import com.lwkandroid.wings.net.parser.ApiBytes2BitmapParser;
import com.lwkandroid.wings.net.parser.ApiBytes2FileParser;
import com.lwkandroid.wings.net.parser.ApiIS2BitmapParser;
import com.lwkandroid.wings.net.parser.ApiIS2FileParser;
import com.lwkandroid.wings.net.convert.ApiResponseConvert;
import com.lwkandroid.wings.net.response.IApiBytesArrayResponse;
import com.lwkandroid.wings.net.response.IApiInputSreamResponse;
import com.socks.library.KLog;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by LWK
 * TODO Download请求
 */

public class ApiDownloadRequest extends ApiBaseRequest<ApiDownloadRequest> implements IApiInputSreamResponse,
        IApiBytesArrayResponse
{
    /*存储文件夹*/
    private String mSaveFolder;
    /*存储名称*/
    private String mFileName;
    /*Bitmap最大宽度*/
    private int mBitmapMaxWidth = Integer.MAX_VALUE;
    /*Bitmap最大高度*/
    private int mBitmapMaxHeight = Integer.MAX_VALUE;

    public ApiDownloadRequest(String url)
    {
        super(url, ApiRequestType.DOWNLOAD);
    }

    public ApiDownloadRequest setSaveFloderPath(String saveFolderPath)
    {
        this.mSaveFolder = saveFolderPath;
        return this;
    }

    public ApiDownloadRequest setFileName(String fileName)
    {
        this.mFileName = fileName;
        return this;
    }

    public ApiDownloadRequest setBitmapMaxSize(int maxWidth, int maxHeight)
    {
        this.mBitmapMaxWidth = maxWidth;
        this.mBitmapMaxHeight = maxHeight;
        return this;
    }

    public int getBitmapMaxHeight()
    {
        return mBitmapMaxHeight;
    }

    public int getBitmapMaxWidth()
    {
        return mBitmapMaxWidth;
    }

    public String getSaveFloderPath()
    {
        return mSaveFolder;
    }

    public String getFileName()
    {
        return mFileName;
    }

    @Override
    protected Observable<ResponseBody> buildResponse(Map<String, String> headersMap,
                                                     Map<String, String> formDatasMap,
                                                     Object objectRequestBody,
                                                     RequestBody okHttp3RequestBody,
                                                     String jsonBody,
                                                     ApiService service)
    {
        if (objectRequestBody != null)
        {
            KLog.w("RxHttp method DOWNLOAD must not have a request body：\n" + objectRequestBody.toString());
        } else if (okHttp3RequestBody != null)
        {
            KLog.w("RxHttp method DOWNLOAD must not have a request body：\n" + okHttp3RequestBody.toString());
        } else if (!TextUtils.isEmpty(jsonBody))
        {
            KLog.w("RxHttp method DOWNLOAD must not have a request body：\n" + jsonBody);
        }
        return service.downloadFile(getUrl(), headersMap, formDatasMap);
    }

    @Override
    public Observable<InputStream> returnISResponse()
    {
        return invokeRequest()
                .compose(ApiResponseConvert.responseToInputStream())
                .compose(new ApiErrorTransformer<InputStream>());
    }

    @Override
    public Observable<File> parseAsFileFromIS()
    {
        return returnISResponse()
                .compose(new ApiIS2FileParser(getSaveFloderPath(), getFileName()).parseAsFile());
    }

    @Override
    public Observable<Bitmap> parseAsBitmapFromIS()
    {
        return returnISResponse()
                .compose(new ApiIS2BitmapParser(getBitmapMaxWidth(), getBitmapMaxHeight()).parseAsBitmap());
    }

    @Override
    public Observable<byte[]> returnByteArrayResponse()
    {
        return invokeRequest()
                .compose(ApiResponseConvert.responseToBytes())
                .compose(new ApiErrorTransformer<byte[]>());
    }

    @Override
    public Observable<File> parseAsFileFromBytes()
    {
        return returnByteArrayResponse()
                .compose(new ApiBytes2FileParser(getSaveFloderPath(), getFileName()).parseAsFile());
    }

    @Override
    public Observable<Bitmap> parseAsBitmapFromBytes()
    {
        return returnByteArrayResponse()
                .compose(new ApiBytes2BitmapParser(getBitmapMaxWidth(), getBitmapMaxHeight()).parseAsBitmap());
    }
}
