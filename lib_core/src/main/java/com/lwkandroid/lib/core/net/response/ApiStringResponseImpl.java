package com.lwkandroid.lib.core.net.response;


import com.lwkandroid.lib.core.net.bean.ResultCacheWrapper;
import com.lwkandroid.lib.core.net.cache.RxCache;
import com.lwkandroid.lib.core.net.cache.func.CacheDataGetterFunc;
import com.lwkandroid.lib.core.net.cache.func.StringCacheToObjectDataCacheFunc;
import com.lwkandroid.lib.core.net.cache.func.StringCacheToObjectDataListCacheFunc;
import com.lwkandroid.lib.core.net.cache.func.StringCacheToRestfulDataCacheFunc;
import com.lwkandroid.lib.core.net.cache.func.StringCacheToRestfulDataListCacheFunc;
import com.lwkandroid.lib.core.net.exception.ApiExceptionConvertFunc;
import com.lwkandroid.lib.core.net.requst.ApiBaseRequest;
import com.lwkandroid.lib.core.net.response.func.ApiResponseBodyConverter;
import com.lwkandroid.lib.core.net.retry.AutoRetryFunc;

import java.util.List;

import io.reactivex.Observable;

/**
 * Description: 字符串网络请求结果的转换方法的实现类
 *
 * @author LWK
 * @date 2019/5/15
 */
public final class ApiStringResponseImpl<R extends ApiBaseRequest<R>> implements IApiStringResponse
{
    private ApiBaseRequest<R> mRequest;

    public ApiStringResponseImpl(ApiBaseRequest<R> request)
    {
        this.mRequest = request;
    }

    @Override
    public Observable<ResultCacheWrapper<String>> returnStringResponseCache()
    {
        return mRequest.invokeRequest()
                .map(ApiResponseBodyConverter.convertToString())
                .compose(RxCache.transform(mRequest.getFinalCacheOptions(), String.class))
                .onErrorResumeNext(new ApiExceptionConvertFunc<>())
                .retryWhen(new AutoRetryFunc(mRequest.getSubUrl(), mRequest.getAutoRetryCount(),
                        mRequest.getAutoRetryDelay(), mRequest.getAutoRetryJudge()));
    }

    @Override
    public Observable<String> returnStringResponse()
    {
        return returnStringResponseCache()
                .map(new CacheDataGetterFunc<>());
    }

    @Override
    public <T> Observable<ResultCacheWrapper<T>> parseDataObjectCache(Class<T> tOfClass)
    {
        return returnStringResponseCache()
                .flatMap(new StringCacheToRestfulDataCacheFunc<>(mRequest.getApiStringParser(), tOfClass));
    }

    @Override
    public <T> Observable<T> parseDataObject(Class<T> tOfClass)
    {
        return parseDataObjectCache(tOfClass)
                .map(new CacheDataGetterFunc<>());
    }

    @Override
    public <T> Observable<ResultCacheWrapper<T>> parseDataObjectByCustomCache(Class<T> tOfClass)
    {
        return returnStringResponseCache()
                .flatMap(new StringCacheToObjectDataCacheFunc<>(mRequest.getApiStringParser(), tOfClass));
    }

    @Override
    public <T> Observable<T> parseDataObjectByCustom(Class<T> tOfClass)
    {
        return parseDataObjectByCustomCache(tOfClass)
                .map(new CacheDataGetterFunc<>());
    }

    @Override
    public <T> Observable<ResultCacheWrapper<List<T>>> parseDataListCache(Class<T> tOfClass)
    {
        return returnStringResponseCache()
                .flatMap(new StringCacheToRestfulDataListCacheFunc<>(mRequest.getApiStringParser(), tOfClass));
    }

    @Override
    public <T> Observable<List<T>> parseDataList(Class<T> tOfClass)
    {
        return parseDataListCache(tOfClass)
                .map(new CacheDataGetterFunc<>());
    }

    @Override
    public <T> Observable<ResultCacheWrapper<List<T>>> parseDataListByCustomCache(Class<T> tOfClass)
    {
        return returnStringResponseCache()
                .flatMap(new StringCacheToObjectDataListCacheFunc<>(mRequest.getApiStringParser(), tOfClass));
    }

    @Override
    public <T> Observable<List<T>> parseDataListByCustom(Class<T> tOfClass)
    {
        return parseDataListByCustomCache(tOfClass)
                .map(new CacheDataGetterFunc<>());
    }
}
