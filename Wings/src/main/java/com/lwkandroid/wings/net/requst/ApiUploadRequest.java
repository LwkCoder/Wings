package com.lwkandroid.wings.net.requst;

import android.text.TextUtils;

import com.lwkandroid.wings.net.ApiService;
import com.lwkandroid.wings.net.bean.ApiCacheResultBean;
import com.lwkandroid.wings.net.cache.RxCache;
import com.lwkandroid.wings.net.constants.ApiRequestType;
import com.lwkandroid.wings.net.convert.ApiResponseConvert;
import com.lwkandroid.wings.net.response.IApiStringResponse;
import com.lwkandroid.wings.net.utils.MultipartBodyList;
import com.lwkandroid.wings.net.utils.MultipartBodyUtils;
import com.lwkandroid.wings.net.utils.RequestBodyUtils;
import com.socks.library.KLog;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
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
            KLog.w("RxHttp method PATCH must not have a Object body：\n" + objectRequestBody.toString());
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
    public Observable<ApiCacheResultBean<String>> returnStringResponseWithCacheWraped()
    {
        return invokeRequest()
                .compose(ApiResponseConvert.responseToString())
                .compose(RxCache.transform(getFinalCacheOptions(), String.class));
    }

    @Override
    public Observable<String> returnStringResponse()
    {
        return returnStringResponseWithCacheWraped()
                .map(new Function<ApiCacheResultBean<String>, String>()
                {
                    @Override
                    public String apply(ApiCacheResultBean<String> resultBean) throws Exception
                    {
                        return resultBean.getData();
                    }
                });
    }

    @Override
    public <T> Observable<ApiCacheResultBean<T>> parseAsObjectWithCacheWraped(final Class<T> tOfClass)
    {
        return returnStringResponseWithCacheWraped()
                .flatMap(new Function<ApiCacheResultBean<String>, ObservableSource<ApiCacheResultBean<T>>>()
                {
                    @Override
                    public ObservableSource<ApiCacheResultBean<T>> apply(final ApiCacheResultBean<String> resultBean) throws Exception
                    {
                        return Observable.just(resultBean.getData())
                                .compose(getApiStringParser().parseAsObject(tOfClass))
                                .map(new Function<T, ApiCacheResultBean<T>>()
                                {
                                    @Override
                                    public ApiCacheResultBean<T> apply(T t) throws Exception
                                    {
                                        return new ApiCacheResultBean<>(resultBean.isCache(), t);
                                    }
                                });
                    }
                });
    }

    @Override
    public <T> Observable<T> parseAsObject(Class<T> tOfClass)
    {
        return parseAsObjectWithCacheWraped(tOfClass)
                .map(new Function<ApiCacheResultBean<T>, T>()
                {
                    @Override
                    public T apply(ApiCacheResultBean<T> cacheResultBean) throws Exception
                    {
                        return cacheResultBean.getData();
                    }
                });
    }

    @Override
    public <T> Observable<ApiCacheResultBean<List<T>>> parseAsListWithCacheWraped(final Class<T> tOfClass)
    {
        return returnStringResponseWithCacheWraped()
                .flatMap(new Function<ApiCacheResultBean<String>, ObservableSource<ApiCacheResultBean<List<T>>>>()
                {
                    @Override
                    public ObservableSource<ApiCacheResultBean<List<T>>> apply(final ApiCacheResultBean<String> resultBean) throws Exception
                    {
                        return Observable.just(resultBean.getData())
                                .compose(getApiStringParser().parseAsList(tOfClass))
                                .map(new Function<List<T>, ApiCacheResultBean<List<T>>>()
                                {
                                    @Override
                                    public ApiCacheResultBean<List<T>> apply(List<T> ts) throws Exception
                                    {
                                        return new ApiCacheResultBean<>(resultBean.isCache(), ts);
                                    }
                                });
                    }
                });
    }

    @Override
    public <T> Observable<List<T>> parseAsList(Class<T> tOfClass)
    {
        return parseAsListWithCacheWraped(tOfClass)
                .map(new Function<ApiCacheResultBean<List<T>>, List<T>>()
                {
                    @Override
                    public List<T> apply(ApiCacheResultBean<List<T>> resultBean) throws Exception
                    {
                        return resultBean.getData();
                    }
                });
    }
}
