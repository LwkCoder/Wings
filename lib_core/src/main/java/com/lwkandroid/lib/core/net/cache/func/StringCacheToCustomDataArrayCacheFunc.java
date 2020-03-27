package com.lwkandroid.lib.core.net.cache.func;


import com.lwkandroid.lib.core.net.bean.ResultCacheWrapper;
import com.lwkandroid.lib.core.net.parser.IApiStringParser;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

/**
 * 转换缓存包装体内String数据为某一对象集合数据
 *
 * @author LWK
 */
public final class StringCacheToCustomDataArrayCacheFunc<T> extends AbsDataArrayCacheFunction<T>
{
    public StringCacheToCustomDataArrayCacheFunc(IApiStringParser parser, Class<T> classType)
    {
        super(parser, classType);
    }

    @Override
    public ObservableSource<ResultCacheWrapper<T[]>> apply(ResultCacheWrapper<String> resultBean) throws Exception
    {
        return Observable.just(resultBean)
                .map(new CacheDataGetterFunc<>())
                .compose(getParser().parseCustomDataArray(getClassType()))
                .map(ts -> new ResultCacheWrapper<>(resultBean.isCache(), ts));
    }
}
