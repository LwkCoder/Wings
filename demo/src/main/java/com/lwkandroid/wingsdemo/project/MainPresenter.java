package com.lwkandroid.wingsdemo.project;

import com.lwkandroid.wings.log.KLog;
import com.lwkandroid.wings.net.bean.ApiException;
import com.lwkandroid.wings.net.observer.ApiBaseObserver;
import com.lwkandroid.wings.utils.StringUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by LWK
 * RxHttpDemoActivity Presenter层
 *
 * @author IED_WENKANG
 */
public class MainPresenter extends MainContract.Presenter
{

    @Override
    public MainContract.Model createModel()
    {
        return new MainModel();
    }

    @Override
    public void queryTestDataList()
    {
    }

    int count = 0;
    String token;

    @Override
    public void test()
    {
        Observable.create(new ObservableOnSubscribe<String>()
        {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception
            {
                if (count == 0)
                {
                    KLog.e("第一次请求开始，模拟第一次请求失败，非token相关的失败");
                    emitter.onError(new IllegalArgumentException("FUCK"));
                } else
                {
                    if (StringUtils.isEmpty(token))
                    {
                        KLog.e("模拟第二次请求失败，token失效");
                        emitter.onError(new NullPointerException("Token Unavailable"));
                    } else
                    {
                        KLog.e("第三次请求开始");
                        emitter.onNext("DataValue");
                        emitter.onComplete();
                    }
                }
            }
        })
                .compose(this.<String>applyIo2MainWithLifeCycle())
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>()
                {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception
                    {
                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>()
                        {
                            @Override
                            public ObservableSource<?> apply(Throwable throwable) throws Exception
                            {
                                KLog.e("模拟非token相关的自动重试");
                                if (throwable instanceof IllegalArgumentException)
                                {
                                    count++;
                                    return Observable.timer(500, TimeUnit.MILLISECONDS);
                                } else
                                {
                                    return Observable.error(throwable);
                                }
                            }
                        });
                    }
                })
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>()
                {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception
                    {
                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>()
                        {
                            @Override
                            public ObservableSource<?> apply(Throwable throwable) throws Exception
                            {
                                KLog.e("模拟自动刷新token的重试");
                                if (throwable instanceof NullPointerException)
                                {
                                    token = "NewToken";
                                    return Observable.timer(500, TimeUnit.MILLISECONDS);
                                } else
                                {
                                    return Observable.error(throwable);
                                }
                            }
                        });
                    }
                })
                .subscribe(new ApiBaseObserver<String>()
                {
                    @Override
                    public void subOnNext(String s)
                    {
                        KLog.e("收到：" + s);
                    }

                    @Override
                    public void subOnError(ApiException e)
                    {
                        KLog.e("错误：" + e.getDisplayMessage());
                    }
                });
    }

}
