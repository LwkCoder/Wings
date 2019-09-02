package com.lwkandroid.wings.net.cache.func;

import com.lwkandroid.wings.net.bean.ResultCacheWrapper;
import com.lwkandroid.wings.net.parser.IApiStringParser;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

/**
 * 转换缓存包装体内String数据为某一对象数据
 *
 * @author LWK
 */
public class StringCacheToObjectDataCacheFunc<T> extends AbsDataCacheFunction<T>
{
    public StringCacheToObjectDataCacheFunc(IApiStringParser parser, Class<T> classType)
    {
        super(parser, classType);
    }

    @Override
    public ObservableSource<ResultCacheWrapper<T>> apply(final ResultCacheWrapper<String> resultBean) throws Exception
    {
        return Observable.just(resultBean)
                .map(new CacheDataGetterFunc<String>())
                .compose(getParser().parseCustomDataObject(getClassType()))
                .map(t -> new ResultCacheWrapper<>(resultBean.isCache(), t));
    }
}
