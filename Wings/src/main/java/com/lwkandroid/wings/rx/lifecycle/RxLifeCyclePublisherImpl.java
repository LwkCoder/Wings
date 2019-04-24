package com.lwkandroid.wings.rx.lifecycle;

import io.reactivex.subjects.PublishSubject;

/**
 * Description: RX生命周期发布者接口实现类
 *
 * @author LWK
 * @date 2019/4/24
 */
public class RxLifeCyclePublisherImpl implements IRxLifeCyclePublisher
{
    private PublishSubject<Integer> mPublishSubject = PublishSubject.create();

    @Override
    public PublishSubject<Integer> getLifeCycleSubject()
    {
        return mPublishSubject;
    }

    @Override
    public void publishLifeCycleEvent(Integer lifeCycleEvent)
    {
        getLifeCycleSubject().onNext(lifeCycleEvent);
    }
}
