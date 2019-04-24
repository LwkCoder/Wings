package com.lwkandroid.wings.rx.lifecycle;

import io.reactivex.subjects.PublishSubject;

/**
 * Description:RX生命周期发布者接口
 *
 * @author LWK
 * @date 2019/4/24
 */
public interface IRxLifeCyclePublisher
{
    PublishSubject<Integer> getLifeCycleSubject();

    void publishLifeCycleEvent(@RxLifeCycleEvent Integer lifeCycleEvent);
}
