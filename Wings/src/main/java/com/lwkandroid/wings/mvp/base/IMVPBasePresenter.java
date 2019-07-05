package com.lwkandroid.wings.mvp.base;

import com.lwkandroid.wings.rx.lifecycle.IRxLifeCycleOperator;

/**
 * MVP模版中Presenter接口
 *
 * @author LWK
 */
public interface IMVPBasePresenter<V extends IMVPBaseView, M> extends IRxLifeCycleOperator<V>
{
    void attachWithView(V view);

    M createModel();

    V getViewImpl();

    M getModelImpl();

    void onDestroyPresenter();
}
