package com.lwkandroid.wings.net.cache.func;

import com.lwkandroid.wings.net.bean.ApiResultCacheWrapper;
import com.lwkandroid.wings.net.parser.IApiStringParser;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by LWK
 *  转换缓存包装体内数据为某一对象的方法
 */

public class ApiCacheDataToDataFunc<T> implements Function<ApiResultCacheWrapper<String>,
        ObservableSource<ApiResultCacheWrapper<T>>>
{
    private IApiStringParser mParser;
    private Class<T> mClass;

    public ApiCacheDataToDataFunc(IApiStringParser parser, Class<T> clazz)
    {
        this.mParser = parser;
        this.mClass = clazz;
    }

    @Override
    public ObservableSource<ApiResultCacheWrapper<T>> apply(final ApiResultCacheWrapper<String> resultBean) throws Exception
    {
        return Observable.just(resultBean.getData())
                .compose(mParser.parseAsObject(mClass))
                .map(new Function<T, ApiResultCacheWrapper<T>>()
                {
                    @Override
                    public ApiResultCacheWrapper<T> apply(T t) throws Exception
                    {
                        return new ApiResultCacheWrapper<T>(resultBean.isCache(), t);
                    }
                });
    }
}
