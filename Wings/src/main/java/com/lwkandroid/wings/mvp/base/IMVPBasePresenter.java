package com.lwkandroid.wings.mvp.base;

import com.lwkandroid.wings.rx.lifecycle.IRxLifeCycleOperator;

/**
 * Created by LWK
 * MVP模版中Presenter接口
 */

public interface IMVPBasePresenter<V extends IMVPBaseView, M> extends IRxLifeCycleOperator<V>
{
    void attachWithView(V view);

    M createModel();

    V getViewImpl();

    M getModelImpl();

    void onDestroyPresenter();
}
