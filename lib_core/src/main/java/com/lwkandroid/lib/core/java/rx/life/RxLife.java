package com.lwkandroid.lib.core.java.rx.life;

import androidx.lifecycle.LifecycleOwner;

/**
 * Created by LWK
 * TODO 绑定Lifecycle的工具类
 * 2020/2/12
 */
public final class RxLife
{
    public static RxLifeProvider with(LifecycleOwner owner)
    {
        return new RxLifeProvider(owner);
    }
}
