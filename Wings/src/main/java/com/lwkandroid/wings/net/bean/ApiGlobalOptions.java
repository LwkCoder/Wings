package com.lwkandroid.wings.net.bean;

import android.support.annotation.NonNull;

import com.lwkandroid.wings.net.constants.ApiConstants;
import com.lwkandroid.wings.net.cookie.ICookieJar;
import com.lwkandroid.wings.net.https.HttpsUtils;
import com.lwkandroid.wings.net.parser.ApiStringParser;
import com.lwkandroid.wings.net.parser.IApiStringParser;
import com.lwkandroid.wings.net.utils.FormDataMap;
import com.lwkandroid.wings.utils.StringUtils;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;

import okhttp3.Interceptor;

/**
 * Created by LWK
 * TODO 全局请求参数
 */

public class ApiGlobalOptions
{
    /*网络请求结果对象Type*/
    private Type mApiResultType;
    /*网络请求正常的状态码*/
    private int mResultOkCode = ApiConstants.RESULT_OK_CODE;
    /*读取超时时间*/
    private long mReadTimeOut = ApiConstants.READ_TIMEOUT;
    /*写入超时时间*/
    private long mWriteTimeOut = ApiConstants.WRITE_TIMEOUT;
    /*连接超时时间*/
    private long mConnectTimeOut = ApiConstants.CONNECT_TIMEOUT;
    /*请求域名*/
    private String mBaseUrl;
    /*全局请求需要添加的拦截器*/
    private Map<String, Interceptor> mInterceptorMap;
    /*全局请求需要添加的网络拦截器*/
    private Map<String, Interceptor> mNetInterceptorMap;
    /*全局表单参数*/
    private FormDataMap mFormDatasMap;
    /*全局Header*/
    private Map<String, String> mHeadersMap;
    /*全局String类型网络请求结果的解析器*/
    private IApiStringParser mStringParser = new ApiStringParser();
    /*Cookie管理类*/
    private ICookieJar mCookieJar;
    /*Https证书*/
    protected HttpsUtils.SSLParams mSslParams;
    /*Https全局访问规则*/
    protected HostnameVerifier mHostnameVerifier;

    public ApiGlobalOptions setApiResultType(Type type)
    {
        this.mApiResultType = type;
        return this;
    }

    public Type getApiResultType()
    {
        return mApiResultType;
    }

    public ApiGlobalOptions setApiResultOkCode(int code)
    {
        this.mResultOkCode = code;
        return this;
    }

    public int getApiResultOkCode()
    {
        return mResultOkCode;
    }

    public long getReadTimeOut()
    {
        return mReadTimeOut;
    }

    public ApiGlobalOptions setReadTimeOut(long readTimeOut)
    {
        this.mReadTimeOut = readTimeOut;
        return this;
    }

    public long getWriteTimeOut()
    {
        return mWriteTimeOut;
    }

    public ApiGlobalOptions setWriteTimeOut(long writeTimeOut)
    {
        this.mWriteTimeOut = writeTimeOut;
        return this;
    }

    public long getConnectTimeOut()
    {
        return mConnectTimeOut;
    }

    public ApiGlobalOptions setConnectTimeOut(long connectTimeOut)
    {
        this.mConnectTimeOut = connectTimeOut;
        return this;
    }

    public String getBaseUrl()
    {
        return mBaseUrl;
    }

    public ApiGlobalOptions setBaseUrl(String baseUrl)
    {
        this.mBaseUrl = baseUrl;
        return this;
    }

    public Map<String, Interceptor> getInterceptorMap()
    {
        return mInterceptorMap;
    }

    public ApiGlobalOptions setInterceptorMap(Map<String, Interceptor> interceptorMap)
    {
        this.mInterceptorMap = interceptorMap;
        return this;
    }

    public ApiGlobalOptions addInterceptorsMap(Map<String, Interceptor> interceptorMap)
    {
        if (mInterceptorMap == null)
            mInterceptorMap = new HashMap<>();
        if (interceptorMap != null && interceptorMap.size() > 0)
            mInterceptorMap.putAll(interceptorMap);
        return this;
    }

    public ApiGlobalOptions addInterceptor(@NonNull String tag, @NonNull Interceptor interceptor)
    {
        if (mInterceptorMap == null)
            mInterceptorMap = new HashMap<>();
        if (StringUtils.isNotEmpty(tag) && interceptor != null)
            mInterceptorMap.put(tag, interceptor);
        return this;
    }

    public ApiGlobalOptions removeInterceptor(String tag)
    {
        if (mInterceptorMap != null)
            mInterceptorMap.remove(tag);
        return this;
    }

    public Map<String, Interceptor> getNetInterceptorMap()
    {
        return mNetInterceptorMap;
    }

    public ApiGlobalOptions setNetInterceptorMap(Map<String, Interceptor> netInterceptorMap)
    {
        this.mNetInterceptorMap = netInterceptorMap;
        return this;
    }

    public ApiGlobalOptions addNetInterceptorsMap(Map<String, Interceptor> interceptorMap)
    {
        if (mNetInterceptorMap == null)
            mNetInterceptorMap = new HashMap<>();
        if (interceptorMap != null && interceptorMap.size() > 0)
            mNetInterceptorMap.putAll(interceptorMap);
        return this;
    }

    public ApiGlobalOptions addNetInterceptor(@NonNull String tag, @NonNull Interceptor interceptor)
    {
        if (mNetInterceptorMap == null)
            mNetInterceptorMap = new HashMap<>();
        if (StringUtils.isNotEmpty(tag) && interceptor != null)
            mNetInterceptorMap.put(tag, interceptor);
        return this;
    }

    public ApiGlobalOptions removeNetInterceptor(String tag)
    {
        if (mNetInterceptorMap != null)
            mNetInterceptorMap.remove(tag);
        return this;
    }

    public Map<String, String> getFormDatasMap()
    {
        return mFormDatasMap;
    }

    public ApiGlobalOptions setFormDatasMap(FormDataMap formDatasMap)
    {
        this.mFormDatasMap = formDatasMap;
        return this;
    }

    public ApiGlobalOptions addFormDatasMap(Map<String, String> formDatasMap)
    {
        if (mFormDatasMap == null)
            mFormDatasMap = new FormDataMap();
        if (formDatasMap != null && formDatasMap.size() > 0)
            mFormDatasMap.putAll(formDatasMap);
        return this;
    }

    public ApiGlobalOptions addFormData(@NonNull String key, String value)
    {
        if (mFormDatasMap == null)
            mFormDatasMap = new FormDataMap();
        if (StringUtils.isNotEmpty(key))
            mFormDatasMap.addParam(key, value);
        return this;
    }

    public ApiGlobalOptions removeFormData(String key)
    {
        if (mFormDatasMap != null)
            mFormDatasMap.remove(key);
        return this;
    }

    public Map<String, String> getHeadersMap()
    {
        return mHeadersMap;
    }

    public ApiGlobalOptions setHeadersMap(Map<String, String> headersMap)
    {
        this.mHeadersMap = headersMap;
        return this;
    }

    public ApiGlobalOptions addHeadersMap(Map<String, String> headersMap)
    {
        if (mHeadersMap == null)
            mHeadersMap = new FormDataMap();
        if (headersMap != null && headersMap.size() > 0)
            mHeadersMap.putAll(headersMap);
        return this;
    }

    public ApiGlobalOptions addHeader(@NonNull String key, String value)
    {
        if (mHeadersMap == null)
            mHeadersMap = new FormDataMap();
        if (StringUtils.isNotEmpty(key))
            mHeadersMap.put(key, value);
        return this;
    }

    public ApiGlobalOptions removeHeader(String key)
    {
        if (mHeadersMap != null)
            mHeadersMap.remove(key);
        return this;
    }

    public void setApiStringParser(IApiStringParser parser)
    {
        this.mStringParser = parser;
    }

    public IApiStringParser getApiStringParser()
    {
        return mStringParser;
    }

    public ApiGlobalOptions setCookieManager(ICookieJar cookieJarImpl)
    {
        this.mCookieJar = cookieJarImpl;
        return this;
    }

    public ICookieJar getCookieManager()
    {
        return mCookieJar;
    }

    /**
     * https的自签名证书
     */
    public ApiGlobalOptions setHttpsCertificates(InputStream... certificates)
    {
        this.mSslParams = HttpsUtils.getSslSocketFactory(null, null, certificates);
        return this;
    }

    /**
     * https的双向认证证书
     */
    public ApiGlobalOptions setHttpsCertificates(InputStream bksFile, String password, InputStream... certificates)
    {
        this.mSslParams = HttpsUtils.getSslSocketFactory(bksFile, password, certificates);
        return this;
    }

    public HttpsUtils.SSLParams getSslParams()
    {
        return mSslParams;
    }

    /**
     * 设置全局请求访问规则
     *
     * @param verifier
     * @return
     */
    public ApiGlobalOptions setHostnameVerifier(HostnameVerifier verifier)
    {
        this.mHostnameVerifier = verifier;
        return this;
    }

    public HostnameVerifier getHostnameVerifier()
    {
        return mHostnameVerifier;
    }
}
