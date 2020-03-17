package com.sources.javacode.project.splash;

import com.lwkandroid.lib.common.mvp.MvpBasePresenterImpl;
import com.lwkandroid.lib.core.net.bean.ApiException;
import com.lwkandroid.lib.core.net.observer.ApiBaseObserver;
import com.lwkandroid.lib.core.utils.ToastUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * Description:Presenter层
 *
 * @author
 * @date
 */
class SplashPresenter extends MvpBasePresenterImpl<SplashContract.IView, SplashContract.IModel>
        implements SplashContract.IPresenter<SplashContract.IView, SplashContract.IModel>
{
    public SplashPresenter(SplashContract.IView viewImpl, SplashContract.IModel modelImpl)
    {
        super(viewImpl, modelImpl);
    }

    @Override
    public void waitForLaunch()
    {
        Observable.timer(1500, TimeUnit.MILLISECONDS)
                .compose(applyComputation2MainUntilOnDestroy())
                .subscribe(new ApiBaseObserver<Long>()
                {
                    @Override
                    public void subOnNext(Long aLong)
                    {
                        getViewImpl().toHome();
                    }

                    @Override
                    public void subOnError(ApiException e)
                    {
                        ToastUtils.showShort(e.getDisplayMessage());
                    }
                });
    }
}
