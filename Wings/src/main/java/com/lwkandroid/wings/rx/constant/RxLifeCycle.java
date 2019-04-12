package com.lwkandroid.wings.rx.constant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * Activity/Fragment生命周期标记位
 */
public class RxLifeCycle
{
    public static final int ON_ATTACH = 0x00000001;
    public static final int ON_CREATE = 0x00000002;
    public static final int ON_CREATE_VIEW = 0x00000003;
    public static final int ON_ACTIVITY_CREATED = 0x00000004;
    public static final int ON_START = 0x00000005;
    public static final int ON_RESUME = 0x00000006;
    public static final int ON_PAUSE = 0x00000007;
    public static final int ON_STOP = 0x00000008;
    public static final int ON_DESTROY_VIEW = 0x00000009;
    public static final int ON_DESTROY = 0x00000010;
    public static final int ON_DETACH = 0x00000011;

    @IntDef({ON_ATTACH, ON_CREATE, ON_CREATE_VIEW, ON_ACTIVITY_CREATED, ON_START, ON_RESUME, ON_PAUSE,
            ON_STOP, ON_DESTROY_VIEW, ON_DESTROY, ON_DETACH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Event
    {

    }
}
