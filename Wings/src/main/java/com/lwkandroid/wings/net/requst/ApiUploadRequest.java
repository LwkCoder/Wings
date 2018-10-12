package com.lwkandroid.wings.net.requst;

import android.text.TextUtils;

import com.lwkandroid.wings.net.ApiService;
import com.lwkandroid.wings.net.bean.ApiResultCacheWrapper;
import com.lwkandroid.wings.net.cache.RxCache;
import com.lwkandroid.wings.net.cache.func.ApiCacheDataGetterFunc;
import com.lwkandroid.wings.net.cache.func.ApiCacheDataParseAsDataFunc;
import com.lwkandroid.wings.net.cache.func.ApiCacheDataParseAsListFunc;
import com.lwkandroid.wings.net.constants.ApiRequestType;
import com.lwkandroid.wings.net.convert.ApiResponseConvert;
import com.lwkandroid.wings.net.error.ApiExceptionTransformer;
import com.lwkandroid.wings.net.response.IApiStringResponse;
import com.lwkandroid.wings.net.retry.AutoRetryFunc;
import com.lwkandroid.wings.net.utils.MultipartBodyList;
import com.lwkandroid.wings.net.utils.MultipartBodyUtils;
import com.lwkandroid.wings.net.utils.RequestBodyUtils;
import com.socks.library.KLog;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by LWK
 * TODO Upload请求
 */

public class ApiUploadRequest extends ApiBaseRequest<ApiUploadRequest> implements IApiStringResponse
{
    /*单次请求的文件参数*/
    private MultipartBodyList mBodyList;

    public ApiUploadRequest(String url)
    {
        super(url, ApiRequestType.UPLOAD);
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
            mBodyList = new MultipartBodyList();
    }

    @Override
    protected Observable<ResponseBody> buildResponse(Map<String, String> headersMap,
                                                     Map<String, String> formDatasMap,
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
            for (Map.Entry<String, String> entry : formDatasMap.entrySet())
            {
                mBodyList.addFormData(entry.getKey(), entry.getValue());
            }
        }

        return service.uploadFiles(getUrl(), headersMap, mBodyList);
    }

    @Override
    public Observable<ApiResultCacheWrapper<String>> returnStringResponseWithCacheWrapped()
    {
        return invokeRequest()
                .compose(ApiResponseConvert.responseToString())
                .compose(RxCache.transform(getFinalCacheOptions(), String.class))
                .compose(new ApiExceptionTransformer<ApiResultCacheWrapper<String>>())
                .retryWhen(new AutoRetryFunc(getUrl(), getAutoRetryCount(), getAutoRetryDelay(), getAutoRetryJudge()));
    }

    @Override
    public Observable<String> returnStringResponse()
    {
        return returnStringResponseWithCacheWrapped()
                .map(new ApiCacheDataGetterFunc<String>());
    }

    @Override
    public <T> Observable<ApiResultCacheWrapper<T>> parseAsObjectWithCacheWrapped(final Class<T> tOfClass)
    {
        return returnStringResponseWithCacheWrapped()
                .flatMap(new ApiCacheDataParseAsDataFunc<T>(getApiStringParser(), tOfClass));
    }

    @Override
    public <T> Observable<T> parseAsObject(Class<T> tOfClass)
    {
        return parseAsObjectWithCacheWrapped(tOfClass)
                .map(new ApiCacheDataGetterFunc<T>());
    }

    @Override
    public <T> Observable<ApiResultCacheWrapper<List<T>>> parseAsListWithCacheWrapped(final Class<T> tOfClass)
    {
        return returnStringResponseWithCacheWrapped()
                .flatMap(new ApiCacheDataParseAsListFunc<T>(getApiStringParser(), tOfClass));
    }

    @Override
    public <T> Observable<List<T>> parseAsList(Class<T> tOfClass)
    {
        return parseAsListWithCacheWrapped(tOfClass)
                .map(new ApiCacheDataGetterFunc<List<T>>());
    }
}
