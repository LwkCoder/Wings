package com.lwkandroid.wings.net.constants;

/**
 * Created by LWK
 * 这个类定义了所有请求结果的结果码
 */

public class ApiExceptionCode
{
    /**
     * 未知错误
     */
    public static final int UNKNOWN = 100000000;

    /**
     * 解析错误
     */
    public static final int PARSE_ERROR = 100000001;

    /**
     * 连接错误
     */
    public static final int CONNECT_ERROR = 100000002;

    /**
     * 证书出错
     */
    public static final int SSL_ERROR = 100000003;

    /**
     * 连接超时
     */
    public static final int TIMEOUT_ERROR = 100000004;

    /**
     * 类转换错误
     */
    public static final int CAST_ERROR = 100000005;
    /**
     * 未知主机错误
     */
    public static final int UNKNOWNHOST_ERROR = 100000006;
    /**
     * 空指针错误
     */
    public static final int NULLPOINTER_EXCEPTION = 100000007;
    /**
     * IO错误
     */
    public static final int IO_EXCEPTION = 100000008;
    /**
     * 空返回
     */
    public static final int RESPONSE_EMPTY = 100000009;
    /**
     * 缓存为空
     */
    public static final int CACHE_EMPTY = 100000010;
}
