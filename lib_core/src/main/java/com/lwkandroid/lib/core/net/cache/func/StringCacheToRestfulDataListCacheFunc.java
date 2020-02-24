package com.lwkandroid.lib.core.net.cache.func;

import com.lwkandroid.lib.core.net.bean.ResultCacheWrapper;
import com.lwkandroid.lib.core.net.parser.IApiStringParser;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * 转换缓存包装体内String数据为Restful风格中对象集合数据
 *
 * @author LWK
 */
public class StringCacheToRestfulDataListCacheFunc<T> implements Function<ResultCacheWrapper<String>,
        ObservableSource<ResultCacheWrapper<List<T>>>>
{
    private IApiStringParser mParser;
    private Class<T> mClass;

    public StringCacheToRestfulDataListCacheFunc(IApiStringParser parser, Class<T> clazz)
    {
        this.mParser = parser;
        this.mClass = clazz;
    }

    @Override
    public ObservableSource<ResultCacheWrapper<List<T>>> apply(final ResultCacheWrapper<String> resultBean) throws Exception
    {
        return Observable.just(resultBean)
                .map(new CacheDataGetterFunc<String>())
                .compose(mParser.parseRestfulDataList(mClass))
                .map(ts -> new ResultCacheWrapper<>(resultBean.isCache(), ts));
    }
}
