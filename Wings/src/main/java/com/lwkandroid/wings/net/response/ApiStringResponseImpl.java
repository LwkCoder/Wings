package com.lwkandroid.wings.net.response;

import com.lwkandroid.wings.net.bean.ApiResultCacheWrapper;
import com.lwkandroid.wings.net.cache.RxCache;
import com.lwkandroid.wings.net.cache.func.ApiCacheDataGetterFunc;
import com.lwkandroid.wings.net.cache.func.ApiCacheDataParseAsDataFunc;
import com.lwkandroid.wings.net.cache.func.ApiCacheDataParseAsListFunc;
import com.lwkandroid.wings.net.exception.ApiExceptionTransformer;
import com.lwkandroid.wings.net.requst.ApiBaseRequest;
import com.lwkandroid.wings.net.response.convert.ApiResponseBodyConverter;
import com.lwkandroid.wings.net.retry.AutoRetryFunc;

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
    public Observable<ApiResultCacheWrapper<String>> returnStringResponseWithCacheWrapped()
    {
        return mRequest.invokeRequest()
                .compose(ApiResponseBodyConverter.transformToString())
                .compose(RxCache.transform(mRequest.getFinalCacheOptions(), String.class))
                .compose(new ApiExceptionTransformer<ApiResultCacheWrapper<String>>())
                .retryWhen(new AutoRetryFunc(mRequest.getSubUrl(), mRequest.getAutoRetryCount(), mRequest.getAutoRetryDelay(), mRequest.getAutoRetryJudge()));
    }

    @Override
    public Observable<String> returnStringResponse()
    {
        return returnStringResponseWithCacheWrapped()
                .map(new ApiCacheDataGetterFunc<String>());
    }

    @Override
    public <T> Observable<ApiResultCacheWrapper<T>> parseAsObjectWithCacheWrapped(Class<T> tOfClass)
    {
        return returnStringResponseWithCacheWrapped()
                .flatMap(new ApiCacheDataParseAsDataFunc<T>(mRequest.getApiStringParser(), tOfClass));
    }

    @Override
    public <T> Observable<T> parseAsObject(Class<T> tOfClass)
    {
        return parseAsObjectWithCacheWrapped(tOfClass)
                .map(new ApiCacheDataGetterFunc<T>());
    }

    @Override
    public <T> Observable<ApiResultCacheWrapper<List<T>>> parseAsListWithCacheWrapped(Class<T> tOfClass)
    {
        return returnStringResponseWithCacheWrapped()
                .flatMap(new ApiCacheDataParseAsListFunc<T>(mRequest.getApiStringParser(), tOfClass));
    }

    @Override
    public <T> Observable<List<T>> parseAsList(Class<T> tOfClass)
    {
        return parseAsListWithCacheWrapped(tOfClass)
                .map(new ApiCacheDataGetterFunc<List<T>>());
    }
}
