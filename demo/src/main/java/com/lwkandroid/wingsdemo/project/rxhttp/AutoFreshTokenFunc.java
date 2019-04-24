package com.lwkandroid.wingsdemo.project.rxhttp;

import com.lwkandroid.wings.log.KLog;
import com.lwkandroid.wings.net.bean.ApiException;
import com.lwkandroid.wingsdemo.bean.AccessTokenBean;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Description: 自动刷新AccessToken的方法
 *
 * @author LWK
 * @date 2019/4/24
 */
public class AutoFreshTokenFunc implements Function<Observable<Throwable>, ObservableSource<?>>
{
    @Override
    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception
    {
        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>()
        {
            @Override
            public ObservableSource<?> apply(Throwable throwable) throws Exception
            {
                ApiException exception = ApiException.handleThrowable(throwable);
                if (exception.getCode() == 1000)
                {
                    //错误码1000代表access_token无效/过期
                    return refreshAccessToken("PrivateToken")
                            .doOnNext(new Consumer<AccessTokenBean>()
                            {
                                @Override
                                public void accept(AccessTokenBean tokenBean) throws Exception
                                {
                                    //刷新token后保存到持久层
                                    AccessTokenDao.get().update(tokenBean);
                                    KLog.e("自动刷新AccessToken成功，保存到持久层完毕");
                                }
                            });
                } else
                {
                    return Observable.error(exception);
                }
            }
        });
    }

    //模拟刷新AccessToken的方法
    private Observable<AccessTokenBean> refreshAccessToken(String privateToken)
    {
        return Observable.create(new ObservableOnSubscribe<AccessTokenBean>()
        {
            @Override
            public void subscribe(ObservableEmitter<AccessTokenBean> emitter) throws Exception
            {
                AccessTokenBean tokenBean = new AccessTokenBean();
                tokenBean.setAccess_token("AccessTokenValue");
                tokenBean.setExpire_time(System.currentTimeMillis());
                emitter.onNext(tokenBean);
                emitter.onComplete();
            }
        });
    }
}
