package com.lwkandroid.wings.net.requst;

import com.lwkandroid.wings.net.ApiService;
import com.lwkandroid.wings.net.RxHttp;
import com.lwkandroid.wings.net.constants.ApiRequestType;
import com.lwkandroid.wings.net.parser.ApiResponseConvert;
import com.lwkandroid.wings.net.utils.MultipartBodyList;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
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
    protected Observable<ResponseBody> buildResponse(Map<String, String> headersMap, Map<String, String> formDatasMap, ApiService service)
    {
        checkBodyListNotNull();
        for (Map.Entry<String, String> entry : formDatasMap.entrySet())
        {
            mBodyList.addFormData(entry.getKey(), entry.getValue());
        }

        return service.uploadFiles(getUrl(), headersMap, mBodyList);
    }

    @Override
    public <T> Observable<T> parseAsObject(Class<T> tOfClass)
    {
        return invokeRequest()
                .compose(ApiResponseConvert.responseToString())
                .compose(RxHttp.getGlobalOptions().getApiStringParser().parseDataAsObject(tOfClass));
    }

    @Override
    public <T> Observable<List<T>> parseAsList(Class<T> tOfClass)
    {
        return invokeRequest()
                .compose(ApiResponseConvert.responseToString())
                .compose(RxHttp.getGlobalOptions().getApiStringParser().parseDataAsList(tOfClass));
    }
}
