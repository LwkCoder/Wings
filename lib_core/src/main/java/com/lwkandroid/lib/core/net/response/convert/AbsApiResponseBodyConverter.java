package com.lwkandroid.lib.core.net.response.convert;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import okhttp3.ResponseBody;

/**
 * Description:ResponseBody转换类
 *
 * @author LWK
 * @date 2019/5/13
 */
abstract class AbsApiResponseBodyConverter<T> implements ObservableTransformer<ResponseBody, T>
{
    @Override
    public ObservableSource<T> apply(Observable<ResponseBody> upstream)
    {
        return upstream.map(this::convert);
    }

    public abstract T convert(ResponseBody response) throws Exception;
}
