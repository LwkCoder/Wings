package com.lwkandroid.lib.core.net.cache.func;


import com.lwkandroid.lib.core.net.bean.ResultCacheWrapper;
import com.lwkandroid.lib.core.net.parser.IApiStringParser;

import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Description:缓存转换为对象集合数据的基类
 *
 * @author LWK
 * @date 2019/6/6
 */
public abstract class AbsDataListCacheFunction<T> implements Function<ResultCacheWrapper<String>,
        ObservableSource<ResultCacheWrapper<List<T>>>>
{
    private IApiStringParser mParser;
    private Class<T> mClassType;

    AbsDataListCacheFunction(IApiStringParser parser, Class<T> classType)
    {
        this.mParser = parser;
        this.mClassType = classType;
    }

    public IApiStringParser getParser()
    {
        return mParser;
    }

    public Class<T> getClassType()
    {
        return mClassType;
    }
}
