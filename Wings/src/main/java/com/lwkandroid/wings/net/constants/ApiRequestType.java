package com.lwkandroid.wings.net.constants;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by LWK
 *  请求类型
 */

public class ApiRequestType
{
    public static final int GET = 0x00000001;
    public static final int POST = 0x00000002;
    public static final int PUT = 0x00000003;
    public static final int DELETE = 0x00000004;
    public static final int PATCH = 0x00000005;
    public static final int UPLOAD = 0x00000006;
    public static final int DOWNLOAD = 0x00000007;

    @IntDef({GET, POST, PUT, DELETE, PATCH, UPLOAD, DOWNLOAD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type
    {

    }
}
