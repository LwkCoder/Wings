package com.lwkandroid.wings.rx.lifecycle;

import javax.annotation.Nonnull;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;

/**
 * Description:RX生命周期工具
 *
 * @author LWK
 * @date 2019/4/24
 */
public final class RxLifeCycle
{
    private RxLifeCycle()
    {
        throw new IllegalArgumentException("Can't instantiate this class !");
    }

    public static <T> RxLifecycleTransformer<T> bindUntilEvent(@Nonnull final Observable<Integer> lifecycle,
                                                               @Nonnull @RxLifeCycleEvent final Integer event)
    {
        return new RxLifecycleTransformer<T>(takeUntilEvent(lifecycle, event));
    }

    private static <R> Observable<R> takeUntilEvent(final Observable<R> lifecycle, final R event)
    {
        return lifecycle.filter(new Predicate<R>()
        {
            @Override
            public boolean test(R lifecycleEvent) throws Exception
            {
                return lifecycleEvent.equals(event);
            }
        });
    }
}
