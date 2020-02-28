package com.lwkandroid.demo2;

import com.lwkandroid.lib.common.mvp.MvpBasePresenterImpl;
import com.lwkandroid.lib.core.log.KLog;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Description:
 *
 * @author 20180004
 * @date 2020/2/25
 */
public class MainPresenter extends MvpBasePresenterImpl<IMainContract.IView, IMainContract.IModel>
        implements IMainContract.IPresenter<IMainContract.IView, IMainContract.IModel>
{
    public MainPresenter(IMainContract.IView viewImpl, IMainContract.IModel modelImpl)
    {
        super(viewImpl, modelImpl);
    }

    @Override
    public void doTest()
    {
        //...
        getModelImpl().getTestData();
        getViewImpl().testRefresh();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void testCreate()
    {
        KLog.e("Presenter创建");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void testDestory()
    {
        KLog.e("Presenter销毁");
    }
}
