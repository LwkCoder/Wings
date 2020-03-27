package com.lwkandroid.lib.core.net.response;


import com.lwkandroid.lib.core.net.bean.ResultCacheWrapper;
import com.lwkandroid.lib.core.net.cache.RxCache;
import com.lwkandroid.lib.core.net.cache.func.CacheDataGetterFunc;
import com.lwkandroid.lib.core.net.cache.func.StringCacheToCustomDataArrayCacheFunc;
import com.lwkandroid.lib.core.net.cache.func.StringCacheToCustomDataListCacheFunc;
import com.lwkandroid.lib.core.net.cache.func.StringCacheToCustomDataObjectCacheFunc;
import com.lwkandroid.lib.core.net.cache.func.StringCacheToRestfulDataArrayCacheFunc;
import com.lwkandroid.lib.core.net.cache.func.StringCacheToRestfulDataListCacheFunc;
import com.lwkandroid.lib.core.net.cache.func.StringCacheToRestfulDataObjectCacheFunc;
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
    public <T> Observable<ResultCacheWrapper<T>> parseRestfulDataObjectWithCache(Class<T> tOfClass)
    {
        return returnStringResponseCache()
                .flatMap(new StringCacheToRestfulDataObjectCacheFunc<>(mRequest.getApiStringParser(), tOfClass));
    }

    @Override
    public <T> Observable<T> parseRestfulDataObject(Class<T> tOfClass)
    {
        return parseRestfulDataObjectWithCache(tOfClass)
                .map(new CacheDataGetterFunc<>());
    }

    @Override
    public <T> Observable<ResultCacheWrapper<T>> parseCustomDataObjectWithCache(Class<T> tOfClass)
    {
        return returnStringResponseCache()
                .flatMap(new StringCacheToCustomDataObjectCacheFunc<>(mRequest.getApiStringParser(), tOfClass));
    }

    @Override
    public <T> Observable<T> parseCustomDataObject(Class<T> tOfClass)
    {
        return parseCustomDataObjectWithCache(tOfClass)
                .map(new CacheDataGetterFunc<>());
    }

    @Override
    public <T> Observable<ResultCacheWrapper<List<T>>> parseRestfulDataListWithCache(Class<T> tOfClass)
    {
        return returnStringResponseCache()
                .flatMap(new StringCacheToRestfulDataListCacheFunc<>(mRequest.getApiStringParser(), tOfClass));
    }

    @Override
    public <T> Observable<List<T>> parseRestfulDataList(Class<T> tOfClass)
    {
        return parseRestfulDataListWithCache(tOfClass)
                .map(new CacheDataGetterFunc<>());
    }

    @Override
    public <T> Observable<ResultCacheWrapper<List<T>>> parseCustomDataListWithCache(Class<T> tOfClass)
    {
        return returnStringResponseCache()
                .flatMap(new StringCacheToCustomDataListCacheFunc<>(mRequest.getApiStringParser(), tOfClass));
    }

    @Override
    public <T> Observable<List<T>> parseCustomDataList(Class<T> tOfClass)
    {
        return parseCustomDataListWithCache(tOfClass)
                .map(new CacheDataGetterFunc<>());
    }

    @Override
    public <T> Observable<ResultCacheWrapper<T[]>> parseRestfulDataArrayWithCache(Class<T> tOfClass)
    {
        return returnStringResponseCache()
                .flatMap(new StringCacheToRestfulDataArrayCacheFunc<>(mRequest.getApiStringParser(), tOfClass));
    }

    @Override
    public <T> Observable<T[]> parseRestfulDataArray(Class<T> tOfClass)
    {
        return parseRestfulDataArrayWithCache(tOfClass)
                .map(new CacheDataGetterFunc<>());
    }

    @Override
    public <T> Observable<ResultCacheWrapper<T[]>> parseCustomDataArrayWithCache(Class<T> tOfClass)
    {
        return returnStringResponseCache()
                .flatMap(new StringCacheToCustomDataArrayCacheFunc<>(mRequest.getApiStringParser(), tOfClass));
    }

    @Override
    public <T> Observable<T[]> parseCustomDataArray(Class<T> tOfClass)
    {
        return parseCustomDataArrayWithCache(tOfClass)
                .map(new CacheDataGetterFunc<>());
    }
}
