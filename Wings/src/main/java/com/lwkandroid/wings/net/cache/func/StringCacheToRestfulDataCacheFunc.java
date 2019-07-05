package com.lwkandroid.wings.net.cache.func;

import com.lwkandroid.wings.net.bean.ResultCacheWrapper;
import com.lwkandroid.wings.net.parser.IApiStringParser;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * 转换缓存包装体内String数据为Restful风格中对象数据
 * @author LWK
 */

public class StringCacheToRestfulDataCacheFunc<T> extends AbsDataCacheFunction<T>
{
    public StringCacheToRestfulDataCacheFunc(IApiStringParser parser, Class<T> classType)
    {
        super(parser, classType);
    }

    @Override
    public ObservableSource<ResultCacheWrapper<T>> apply(final ResultCacheWrapper<String> resultBean) throws Exception
    {
        return Observable.just(resultBean)
                .map(new CacheDataGetterFunc<String>())
                .compose(getParser().parseRestfulDataObject(getClassType()))
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
