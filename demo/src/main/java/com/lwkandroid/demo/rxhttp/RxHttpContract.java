package com.lwkandroid.demo.rxhttp;

import com.lwkandroid.lib.common.mvp.IMvpBaseModel;
import com.lwkandroid.lib.common.mvp.IMvpBasePresenter;
import com.lwkandroid.lib.common.mvp.IMvpBaseView;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;

/**
 * Description:契约层
 *
 * @author
 * @date
 */
interface RxHttpContract
{
    interface IView<P extends LifecycleObserver> extends IMvpBaseView<P>
    {

    }

    interface IModel extends IMvpBaseModel
    {
        Observable<String> test01();

        Observable<String> test02();
    }

    interface IPresenter<V extends LifecycleOwner, M> extends IMvpBasePresenter<V, M>
    {
        void test01();

        void test02();
    }
}
