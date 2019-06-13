package com.lwkandroid.wingsdemo.project.state;

import com.lwkandroid.wings.log.KLog;
import com.lwkandroid.wings.net.bean.ApiException;
import com.lwkandroid.wings.net.observer.ApiBaseObserver;

import io.reactivex.disposables.Disposable;

/**
 * Description:Presenter层
 *
 * @author
 * @date
 */
class StatePresenter extends StateContract.Presenter
{
    @Override
    public StateContract.Model createModel()
    {
        return new StateModel();
    }

    @Override
    void requestEmpty()
    {
        getModelImpl().requestEmpty()
                .compose(this.<String>applyIo2MainUntilViewDestroy())
                .subscribe(new ApiBaseObserver<String>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                        super.onSubscribe(d);
                        getViewImpl().getStateLayout().switchToLoadingState();
                    }

                    @Override
                    public void subOnNext(String s)
                    {
                        getViewImpl().getStateLayout().switchToEmptyState();
                    }

                    @Override
                    public void subOnError(ApiException e)
                    {
                        KLog.e("Empty Error");
                    }

                });
    }

    @Override
    void requestError()
    {
        getModelImpl().requestError()
                .compose(this.<String>applyIo2MainUntilViewDestroy())
                .subscribe(new ApiBaseObserver<String>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                        super.onSubscribe(d);
                        getViewImpl().getStateLayout().switchToLoadingState();
                    }

                    @Override
                    public void subOnNext(String s)
                    {

                    }

                    @Override
                    public void subOnError(ApiException e)
                    {
                        getViewImpl().getStateLayout().switchToNetErrorState();
                    }
                });
    }

    @Override
    void requestContent()
    {
        getModelImpl().requestContent()
                .compose(this.<String>applyIo2MainUntilViewDestroy())
                .subscribe(new ApiBaseObserver<String>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                        super.onSubscribe(d);
                        getViewImpl().getStateLayout().switchToLoadingState();
                    }

                    @Override
                    public void subOnNext(String s)
                    {
                        getViewImpl().getStateLayout().switchToContentState();
                    }

                    @Override
                    public void subOnError(ApiException e)
                    {

                    }
                });
    }
}
