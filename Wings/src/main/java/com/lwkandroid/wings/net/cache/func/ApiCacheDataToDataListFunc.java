package com.lwkandroid.wings.net.cache.func;

import com.lwkandroid.wings.net.bean.ApiResultCacheWrapper;
import com.lwkandroid.wings.net.parser.IApiStringParser;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by LWK
 *  转换缓存包装体内数据为某一对象的集合的方法
 */

public class ApiCacheDataToDataListFunc<T> implements Function<ApiResultCacheWrapper<String>,
        ObservableSource<ApiResultCacheWrapper<List<T>>>>
{
    private IApiStringParser mParser;
    private Class<T> mClass;

    public ApiCacheDataToDataListFunc(IApiStringParser parser, Class<T> clazz)
    {
        this.mParser = parser;
        this.mClass = clazz;
    }

    @Override
    public ObservableSource<ApiResultCacheWrapper<List<T>>> apply(final ApiResultCacheWrapper<String> resultBean) throws Exception
    {
        return Observable.just(resultBean.getData())
                .compose(mParser.parseAsList(mClass))
                .map(new Function<List<T>, ApiResultCacheWrapper<List<T>>>()
                {
                    @Override
                    public ApiResultCacheWrapper<List<T>> apply(List<T> ts) throws Exception
                    {
                        return new ApiResultCacheWrapper<>(resultBean.isCache(), ts);
                    }
                });
    }
}
