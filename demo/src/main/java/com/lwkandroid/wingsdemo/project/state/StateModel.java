package com.lwkandroid.wingsdemo.project.state;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Description:Model层
 *
 * @author
 * @date
 */
class StateModel extends StateContract.Model
{
    @Override
    Observable<String> requestEmpty()
    {
        return Observable.timer(3000, TimeUnit.MILLISECONDS)
                .flatMap(new Function<Long, ObservableSource<String>>()
                {
                    @Override
                    public ObservableSource<String> apply(Long aLong) throws Exception
                    {
                        return Observable.just("NoData");
                    }
                });
    }

    @Override
    Observable<String> requestError()
    {
        return Observable.timer(3000, TimeUnit.MILLISECONDS)
                .flatMap(new Function<Long, ObservableSource<String>>()
                {
                    @Override
                    public ObservableSource<String> apply(Long aLong) throws Exception
                    {
                        return Observable.error(new IllegalArgumentException("Error"));
                    }
                });
    }

    @Override
    Observable<String> requestContent()
    {
        return Observable.timer(3000, TimeUnit.MILLISECONDS)
                .map(new Function<Long, String>()
                {
                    @Override
                    public String apply(Long aLong) throws Exception
                    {
                        return "Content";
                    }
                });
    }
}
