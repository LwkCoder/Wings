package com.lwkandroid.wings.net.cache.func;

import com.lwkandroid.wings.net.bean.ResultCacheWrapper;
import com.lwkandroid.wings.net.parser.IApiStringParser;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by LWK
 * 转换缓存包装体内数据为某一对象集合数据
 */

public class CacheDataToDataListFunc<T> implements Function<ResultCacheWrapper<String>,
        ObservableSource<ResultCacheWrapper<List<T>>>>
{
    private IApiStringParser mParser;
    private Class<T> mClass;

    public CacheDataToDataListFunc(IApiStringParser parser, Class<T> clazz)
    {
        this.mParser = parser;
        this.mClass = clazz;
    }

    @Override
    public ObservableSource<ResultCacheWrapper<List<T>>> apply(final ResultCacheWrapper<String> resultBean) throws Exception
    {
        return Observable.just(resultBean.getData())
                .compose(mParser.parseDataListFromResponse(mClass))
                .map(new Function<List<T>, ResultCacheWrapper<List<T>>>()
                {
                    @Override
                    public ResultCacheWrapper<List<T>> apply(List<T> ts) throws Exception
                    {
                        return new ResultCacheWrapper<>(resultBean.isCache(), ts);
                    }
                });
    }
}
