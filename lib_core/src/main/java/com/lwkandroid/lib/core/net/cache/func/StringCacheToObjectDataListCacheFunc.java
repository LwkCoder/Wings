package com.lwkandroid.lib.core.net.cache.func;


import com.lwkandroid.lib.core.net.bean.ResultCacheWrapper;
import com.lwkandroid.lib.core.net.parser.IApiStringParser;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

/**
 * 转换缓存包装体内String数据为某一对象集合数据
 *
 * @author LWK
 */
public class StringCacheToObjectDataListCacheFunc<T> extends AbsDataListCacheFunction<T>
{
    public StringCacheToObjectDataListCacheFunc(IApiStringParser parser, Class<T> classType)
    {
        super(parser, classType);
    }

    @Override
    public ObservableSource<ResultCacheWrapper<List<T>>> apply(final ResultCacheWrapper<String> resultBean) throws Exception
    {
        return Observable.just(resultBean)
                .map(new CacheDataGetterFunc<String>())
                .compose(getParser().parseCustomDataList(getClassType()))
                .map(ts -> new ResultCacheWrapper<>(resultBean.isCache(), ts));
    }
}
