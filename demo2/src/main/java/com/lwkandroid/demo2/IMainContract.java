package com.lwkandroid.demo2;

import com.lwkandroid.lib.common.mvp.IMvpBaseModel;
import com.lwkandroid.lib.common.mvp.IMvpBasePresenter;
import com.lwkandroid.lib.common.mvp.IMvpBaseView;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * Description:
 *
 * @author 20180004
 * @date 2020/2/25
 */
public interface IMainContract
{
    interface IView<P extends LifecycleObserver> extends IMvpBaseView<P>
    {
        void testRefresh();
    }

    interface IModel extends IMvpBaseModel
    {
        String getTestData();
    }

    interface IPresenter<V extends LifecycleOwner, M> extends IMvpBasePresenter<V, M>
    {
        void doTest();
    }
}
