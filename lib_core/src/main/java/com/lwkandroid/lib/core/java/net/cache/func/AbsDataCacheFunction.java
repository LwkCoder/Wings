package com.lwkandroid.lib.core.java.net.cache.func;

import com.lwkandroid.lib.core.java.net.bean.ResultCacheWrapper;
import com.lwkandroid.lib.core.java.net.parser.IApiStringParser;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Description:缓存转换为对象数据的基类
 *
 * @author LWK
 * @date 2019/6/6
 */
public abstract class AbsDataCacheFunction<T> implements Function<ResultCacheWrapper<String>,
        ObservableSource<ResultCacheWrapper<T>>>
{
    private IApiStringParser mParser;
    private Class<T> mClassType;

    AbsDataCacheFunction(IApiStringParser parser, Class<T> classType)
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
