package com.lwkandroid.wings.net.cache.func;

import com.lwkandroid.wings.net.bean.ResultCacheWrapper;
import com.lwkandroid.wings.net.parser.IApiStringParser;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by LWK
 * 转换缓存包装体内数据为ApiResult中某一对象数据
 */

public class CacheDataToApiResultDataFunc<T> implements Function<ResultCacheWrapper<String>,
        ObservableSource<ResultCacheWrapper<T>>>
{
    private IApiStringParser mParser;
    private Class<T> mClass;

    public CacheDataToApiResultDataFunc(IApiStringParser parser, Class<T> clazz)
    {
        this.mParser = parser;
        this.mClass = clazz;
    }

    @Override
    public ObservableSource<ResultCacheWrapper<T>> apply(final ResultCacheWrapper<String> resultBean) throws Exception
    {
        return Observable.just(resultBean.getData())
                .compose(mParser.parseDataObjectFromApiResult(mClass))
                .map(new Function<T, ResultCacheWrapper<T>>()
                {
                    @Override
                    public ResultCacheWrapper<T> apply(T t) throws Exception
                    {
                        return new ResultCacheWrapper<T>(resultBean.isCache(), t);
                    }
                });
    }
}
