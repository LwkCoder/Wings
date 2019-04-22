package com.lwkandroid.wingsdemo.project.rxhttp;

import com.lwkandroid.wings.net.parser.IApiStringParser;
import com.lwkandroid.wings.utils.json.IJsonStrategy;
import com.lwkandroid.wings.utils.json.JsonUtils;
import com.lwkandroid.wingsdemo.bean.NonRestFulResult;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by LWK
 *  请求结果为NonRestFulResult的解析器
 */

public class NonRestFulStringResultParser implements IApiStringParser
{
    protected static final IJsonStrategy JSON_PARSER = JsonUtils.get();

    @Override
    public <T> ObservableTransformer<String, T> parseAsObject(final Class<T> clazz)
    {
        return new ObservableTransformer<String, T>()
        {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<String> upstream)
            {
                return upstream
                        .map(new Function<String, T>()
                        {
                            @Override
                            public T apply(@NonNull String s) throws Exception
                            {
                                NonRestFulResult result = JSON_PARSER.parseJsonObject(s, NonRestFulResult.class);
                                return (T) result;
                            }
                        });
            }
        };
    }

    @Override
    public <T> ObservableTransformer<String, List<T>> parseAsList(Class<T> clazz)
    {
        return null;
    }
}
