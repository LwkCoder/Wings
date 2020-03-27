package com.lwkandroid.lib.core.net.cache.func;

import com.lwkandroid.lib.core.net.bean.ResultCacheWrapper;
import com.lwkandroid.lib.core.net.parser.IApiStringParser;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

/**
 * 转换缓存包装体内String数据为Restful风格中对象数组数据
 *
 * @author LWK
 */
public final class StringCacheToRestfulDataArrayCacheFunc<T> extends AbsDataArrayCacheFunction<T>
{
    public StringCacheToRestfulDataArrayCacheFunc(IApiStringParser parser, Class<T> classType)
    {
        super(parser, classType);
    }

    @Override
    public ObservableSource<ResultCacheWrapper<T[]>> apply(final ResultCacheWrapper<String> resultBean) throws Exception
    {
        return Observable.just(resultBean)
                .map(new CacheDataGetterFunc<>())
                .compose(getParser().parseRestfulDataArray(getClassType()))
                .map(ts -> new ResultCacheWrapper<>(resultBean.isCache(), ts));
    }
}
