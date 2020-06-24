package com.lwkandroid.demo.rxhttp;

import com.lwkandroid.lib.common.mvp.MvpBasePresenterImpl;
import com.lwkandroid.lib.core.net.bean.ApiException;
import com.lwkandroid.lib.core.net.observer.ApiObserver;
import com.lwkandroid.lib.core.utils.common.ToastUtils;

/**
 * Description:Presenter层
 *
 * @author
 * @date
 */
class RxHttpPresenter extends MvpBasePresenterImpl<RxHttpContract.IView, RxHttpContract.IModel>
        implements RxHttpContract.IPresenter<RxHttpContract.IView, RxHttpContract.IModel>
{
    public RxHttpPresenter(RxHttpContract.IView viewImpl, RxHttpContract.IModel modelImpl)
    {
        super(viewImpl, modelImpl);
    }

    @Override
    public void test01()
    {
        getModelImpl().test01()
                .compose(applyIo2MainUntilOnDestroy())
                .subscribe(new ApiObserver<String>()
                {
                    @Override
                    public void onAccept(String s)
                    {
                        ToastUtils.showShort("请求成功:" + s);
                    }

                    @Override
                    public void onError(ApiException e)
                    {
                        ToastUtils.showShort("请求失败：" + e.getDisplayMessage());
                    }
                });
    }

    @Override
    public void test02()
    {
        getModelImpl().test02()
                .compose(applyIo2MainUntilOnDestroy())
                .subscribe(new ApiObserver<String>()
                {
                    @Override
                    public void onAccept(String s)
                    {
                        ToastUtils.showShort("请求成功:" + s);
                    }

                    @Override
                    public void onError(ApiException e)
                    {
                        ToastUtils.showShort("请求失败：" + e.getDisplayMessage());
                    }
                });
    }
}
