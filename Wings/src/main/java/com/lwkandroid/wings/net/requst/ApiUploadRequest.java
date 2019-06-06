package com.lwkandroid.wings.net.requst;

import android.text.TextUtils;

import com.lwkandroid.wings.log.KLog;
import com.lwkandroid.wings.net.ApiService;
import com.lwkandroid.wings.net.bean.ResultCacheWrapper;
import com.lwkandroid.wings.net.constants.ApiRequestType;
import com.lwkandroid.wings.net.response.ApiStringResponseImpl;
import com.lwkandroid.wings.net.response.IApiStringResponse;
import com.lwkandroid.wings.net.utils.MultipartBodyList;
import com.lwkandroid.wings.net.utils.MultipartBodyUtils;
import com.lwkandroid.wings.net.utils.RequestBodyUtils;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by LWK
 * Upload请求
 *
 * @author LWK
 */

public final class ApiUploadRequest extends ApiBaseRequest<ApiUploadRequest> implements IApiStringResponse
{
    /**
     * 单次请求的文件参数
     */
    private MultipartBodyList mBodyList;

    private ApiStringResponseImpl<ApiUploadRequest> mStringResponseImpl;

    public ApiUploadRequest(String url)
    {
        super(url, ApiRequestType.UPLOAD);
        mStringResponseImpl = new ApiStringResponseImpl<>(this);
    }

    /**
     * 获取该次请求的参数
     */
    public MultipartBodyList getPartBodyList()
    {
        return mBodyList;
    }

    /**
     * 添加该次请求的文件
     */
    public ApiUploadRequest addFiles(String key, List<File> fileList)
    {
        checkBodyListNotNull();
        mBodyList.addFiles(key, fileList);
        return this;
    }

    /**
     * 添加该次请求的文件
     */
    public ApiUploadRequest addFile(String key, File file)
    {
        checkBodyListNotNull();
        mBodyList.addFile(key, file);
        return this;
    }

    /**
     * 添加该次请求的字节数据
     */
    public ApiUploadRequest addBytes(String key, String fileName, byte[] bytes)
    {
        checkBodyListNotNull();
        mBodyList.addBytes(key, fileName, bytes);
        return this;
    }

    /**
     * 添加该次请求的流数据
     */
    public ApiUploadRequest addInputStream(String key, String fileName, InputStream stream)
    {
        checkBodyListNotNull();
        mBodyList.addInputStream(key, fileName, stream);
        return this;
    }

    private void checkBodyListNotNull()
    {
        if (mBodyList == null)
        {
            mBodyList = new MultipartBodyList();
        }
    }

    @Override
    protected Observable<ResponseBody> buildResponse(Map<String, String> headersMap,
                                                     Map<String, Object> formDataMap,
                                                     Object objectRequestBody,
                                                     RequestBody okHttp3RequestBody,
                                                     String jsonBody,
                                                     ApiService service)
    {
        checkBodyListNotNull();
        if (objectRequestBody != null)
        {
            KLog.w("RxHttp method UPLOAD must not have a Object body：\n" + objectRequestBody.toString());
        } else if (okHttp3RequestBody != null)
        {
            checkBodyListNotNull();
            mBodyList.add(MultipartBodyUtils.createPart(okHttp3RequestBody));
        } else if (!TextUtils.isEmpty(jsonBody))
        {
            checkBodyListNotNull();
            RequestBody jsonRequestBody = RequestBodyUtils.createJsonBody(jsonBody);
            mBodyList.add(MultipartBodyUtils.createPart(jsonRequestBody));
        } else
        {
            checkBodyListNotNull();
            for (Map.Entry<String, Object> entry : formDataMap.entrySet())
            {
                mBodyList.addFormData(entry.getKey(), entry.getValue());
            }
        }

        return service.uploadFiles(getSubUrl(), headersMap, mBodyList);
    }

    @Override
    public Observable<ResultCacheWrapper<String>> returnStringResponseWithCacheWrapped()
    {
        return mStringResponseImpl.returnStringResponseWithCacheWrapped();
    }

    @Override
    public Observable<String> returnStringResponse()
    {
        return mStringResponseImpl.returnStringResponse();
    }

    @Override
    public <T> Observable<ResultCacheWrapper<T>> parseDataObjectFromRestfulWithCacheWrapped(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseDataObjectFromRestfulWithCacheWrapped(tOfClass);
    }

    @Override
    public <T> Observable<T> parseDataObjectFromRestful(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseDataObjectFromRestful(tOfClass);
    }

    @Override
    public <T> Observable<ResultCacheWrapper<T>> parseDataObjectFromResponseWithCacheWrapped(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseDataObjectFromResponseWithCacheWrapped(tOfClass);
    }

    @Override
    public <T> Observable<T> parseDataObjectFromResponse(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseDataObjectFromResponse(tOfClass);
    }

    @Override
    public <T> Observable<ResultCacheWrapper<List<T>>> parseDataListFromRestfulWithCacheWrapped(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseDataListFromRestfulWithCacheWrapped(tOfClass);
    }

    @Override
    public <T> Observable<List<T>> parseDataListFromRestful(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseDataListFromRestful(tOfClass);
    }

    @Override
    public <T> Observable<ResultCacheWrapper<List<T>>> parseDataListFromResponseWithCacheWrapped(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseDataListFromResponseWithCacheWrapped(tOfClass);
    }

    @Override
    public <T> Observable<List<T>> parseDataListFromResponse(Class<T> tOfClass)
    {
        return mStringResponseImpl.parseDataListFromResponse(tOfClass);
    }
}
