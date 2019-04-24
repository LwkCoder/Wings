package com.lwkandroid.wings.rx.lifecycle;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * Description: 所有生命周期事件
 *
 * @author LWK
 * @date 2019/4/24
 */
@IntDef({RxLifeCycleConstants.ON_ATTACH,
        RxLifeCycleConstants.ON_CREATE,
        RxLifeCycleConstants.ON_CREATE_VIEW,
        RxLifeCycleConstants.ON_ACTIVITY_CREATED,
        RxLifeCycleConstants.ON_START,
        RxLifeCycleConstants.ON_RESUME,
        RxLifeCycleConstants.ON_PAUSE,
        RxLifeCycleConstants.ON_STOP,
        RxLifeCycleConstants.ON_DESTROY_VIEW,
        RxLifeCycleConstants.ON_DESTROY,
        RxLifeCycleConstants.ON_DETACH})
@Retention(RetentionPolicy.SOURCE)
public @interface RxLifeCycleEvent
{
}
