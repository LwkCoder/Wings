package com.lwkandroid.wings.net.bean;

import com.lwkandroid.wings.net.constants.ApiCacheMode;
import com.lwkandroid.wings.net.https.HttpsUtils;
import com.lwkandroid.wings.net.parser.ApiStringParser;
import com.lwkandroid.wings.net.parser.IApiStringParser;
import com.lwkandroid.wings.net.retry.AutoRetryJudgeImpl;
import com.lwkandroid.wings.net.retry.IAutoRetry;
import com.lwkandroid.wings.net.utils.FormDataMap;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;

import androidx.annotation.NonNull;
import okhttp3.Interceptor;

/**
 * Created by LWK
 * TODO 配置请求参数的公共方法实现类
 */
class CommonOptionsImpl implements IRequestOptions.Common<CommonOptionsImpl>
{
    /*网络请求结果对象Type*/
    private Type mApiResultType;
    /*读取超时时间*/
    private long mReadTimeOut = -1;
    /*写入超时时间*/
    private long mWriteTimeOut = -1;
    /*连接超时时间*/
    private long mConnectTimeOut = -1;
    /*请求域名*/
    private String mBaseUrl;
    /*全局请求需要添加的拦截器*/
    private Map<String, Interceptor> mInterceptorMap;
    /*全局请求需要添加的网络拦截器*/
    private Map<String, Interceptor> mNetInterceptorMap;
    /*全局表单参数*/
    private FormDataMap mFormDataMap;
    /*全局Header*/
    private Map<String, String> mHeadersMap;
    /*全局String类型网络请求结果的解析器*/
    private IApiStringParser mStringParser;
    //缓存类型
    private @ApiCacheMode.Mode
    int mCacheMode = ApiCacheMode.NO_CACHE;
    //缓存时长
    private long mCacheTime = -1;
    //自动重试次数
    private int mAutoRetryCount = 0;
    //自动重试间隔,ms
    private int mAutoRetryDelay;
    //判断自动重试时机的对象
    private IAutoRetry mAutoRetry;
    /*Https证书*/
    protected HttpsUtils.SSLParams mSslParams;
    /*Https全局访问规则*/
    protected HostnameVerifier mHostnameVerifier;

    @Override
    public CommonOptionsImpl setApiResultStringParser(IApiStringParser parser)
    {
        mStringParser = parser;
        return this;
    }

    @Override
    public IApiStringParser getApiStringParser()
    {
        if (mStringParser == null)
        {
            mStringParser = new ApiStringParser();
        }
        return mStringParser;
    }

    @Override
    public CommonOptionsImpl setApiResultType(Type type)
    {
        this.mApiResultType = type;
        return this;
    }

    @Override
    public Type getApiResultType()
    {
        return mApiResultType;
    }

    @Override
    public CommonOptionsImpl setReadTimeOut(long readTimeOut)
    {
        this.mReadTimeOut = readTimeOut;
        return this;
    }

    @Override
    public long getReadTimeOut()
    {
        return mReadTimeOut;
    }

    @Override
    public CommonOptionsImpl setWriteTimeOut(long writeTimeOut)
    {
        this.mWriteTimeOut = writeTimeOut;
        return this;
    }

    @Override
    public long getWriteTimeOut()
    {
        return mWriteTimeOut;
    }

    @Override
    public CommonOptionsImpl setConnectTimeOut(long connectTimeOut)
    {
        this.mConnectTimeOut = connectTimeOut;
        return this;
    }

    @Override
    public long getConnectTimeOut()
    {
        return mConnectTimeOut;
    }

    @Override
    public CommonOptionsImpl setBaseUrl(String baseUrl)
    {
        this.mBaseUrl = baseUrl;
        return this;
    }

    @Override
    public String getBaseUrl()
    {
        return mBaseUrl;
    }

    @Override
    public Map<String, Interceptor> getInterceptorMap()
    {
        if (mInterceptorMap == null)
        {
            mInterceptorMap = new HashMap<>();
        }
        return mInterceptorMap;
    }

    @Override
    public CommonOptionsImpl setInterceptorMap(Map<String, Interceptor> interceptorMap)
    {
        this.mInterceptorMap = interceptorMap;
        return this;
    }

    @Override
    public CommonOptionsImpl addInterceptorMap(Map<String, Interceptor> interceptorMap)
    {
        getInterceptorMap().putAll(interceptorMap);
        return this;
    }

    @Override
    public CommonOptionsImpl addInterceptor(@NonNull String tag, @NonNull Interceptor interceptor)
    {
        getInterceptorMap().put(tag, interceptor);
        return this;
    }

    @Override
    public Map<String, Interceptor> getNetInterceptorMap()
    {
        if (mNetInterceptorMap == null)
        {
            mNetInterceptorMap = new HashMap<>();
        }
        return mNetInterceptorMap;
    }

    @Override
    public CommonOptionsImpl setNetInterceptorMap(Map<String, Interceptor> netInterceptorMap)
    {
        this.mNetInterceptorMap = netInterceptorMap;
        return this;
    }

    @Override
    public CommonOptionsImpl addNetInterceptorMap(Map<String, Interceptor> interceptorMap)
    {
        getNetInterceptorMap().putAll(interceptorMap);
        return this;
    }

    @Override
    public CommonOptionsImpl addNetInterceptor(@NonNull String tag, @NonNull Interceptor interceptor)
    {
        getNetInterceptorMap().put(tag, interceptor);
        return this;
    }

    @Override
    public FormDataMap getFormDataMap()
    {
        if (mFormDataMap == null)
        {
            mFormDataMap = new FormDataMap();
        }
        return mFormDataMap;
    }

    @Override
    public CommonOptionsImpl setFormDataMap(FormDataMap formDataMap)
    {
        this.mFormDataMap = formDataMap;
        return this;
    }

    @Override
    public CommonOptionsImpl addFormDataMap(Map<String, String> formDataMap)
    {
        getFormDataMap().putAll(formDataMap);
        return this;
    }

    @Override
    public CommonOptionsImpl addFormData(@NonNull String key, byte value)
    {
        getFormDataMap().put(key, value);
        return this;
    }

    @Override
    public CommonOptionsImpl addFormData(@NonNull String key, int value)
    {
        getFormDataMap().put(key, value);
        return this;
    }

    @Override
    public CommonOptionsImpl addFormData(@NonNull String key, float value)
    {
        getFormDataMap().put(key, value);
        return this;
    }

    @Override
    public CommonOptionsImpl addFormData(@NonNull String key, short value)
    {
        getFormDataMap().put(key, value);
        return this;
    }

    @Override
    public CommonOptionsImpl addFormData(@NonNull String key, long value)
    {
        getFormDataMap().put(key, value);
        return this;
    }

    @Override
    public CommonOptionsImpl addFormData(@NonNull String key, double value)
    {
        getFormDataMap().put(key, value);
        return this;
    }

    @Override
    public CommonOptionsImpl addFormData(@NonNull String key, boolean value)
    {
        getFormDataMap().put(key, value);
        return this;
    }

    @Override
    public CommonOptionsImpl addFormData(@NonNull String key, String value)
    {
        getFormDataMap().put(key, value);
        return this;
    }

    @Override
    public CommonOptionsImpl addFormData(@NonNull String key, Object value)
    {
        getFormDataMap().put(key, value);
        return this;
    }

    @Override
    public Map<String, String> getHeadersMap()
    {
        if (mHeadersMap == null)
        {
            mHeadersMap = new HashMap<>();
        }
        return mHeadersMap;
    }

    @Override
    public CommonOptionsImpl setHeadersMap(Map<String, String> headersMap)
    {
        this.mHeadersMap = headersMap;
        return this;
    }

    @Override
    public CommonOptionsImpl addHeadersMap(Map<String, String> headersMap)
    {
        getHeadersMap().putAll(headersMap);
        return this;
    }

    @Override
    public CommonOptionsImpl addHeader(@NonNull String tag, String value)
    {
        getHeadersMap().put(tag, value);
        return this;
    }

    @Override
    public CommonOptionsImpl setCacheMode(int mode)
    {
        this.mCacheMode = mode;
        return this;
    }

    @Override
    public int getCacheMode()
    {
        return mCacheMode;
    }

    @Override
    public CommonOptionsImpl setCacheTime(long cacheTime)
    {
        this.mCacheTime = cacheTime;
        return this;
    }

    @Override
    public long getCacheTime()
    {
        return mCacheTime;
    }

    @Override
    public CommonOptionsImpl setAutoRetryJudge(IAutoRetry autoRetry)
    {
        this.mAutoRetry = autoRetry;
        return this;
    }

    @Override
    public IAutoRetry getAutoRetryJudge()
    {
        if (mAutoRetry == null)
        {
            mAutoRetry = new AutoRetryJudgeImpl();
        }
        return mAutoRetry;
    }

    @Override
    public CommonOptionsImpl setAutoRetryCount(int count)
    {
        this.mAutoRetryCount = count;
        return this;
    }

    @Override
    public int getAutoRetryCount()
    {
        return mAutoRetryCount;
    }

    @Override
    public CommonOptionsImpl setAutoRetryDelay(int delay)
    {
        this.mAutoRetryDelay = delay;
        return this;
    }

    @Override
    public int getAutoRetryDelay()
    {
        return mAutoRetryDelay;
    }

    @Override
    public CommonOptionsImpl setHttpsCertificates(InputStream... certificates)
    {
        this.mSslParams = HttpsUtils.getSslSocketFactory(null, null, certificates);
        return this;
    }

    @Override
    public CommonOptionsImpl setHttpsCertificates(InputStream bksFile, String password, InputStream... certificates)
    {
        this.mSslParams = HttpsUtils.getSslSocketFactory(bksFile, password, certificates);
        return this;
    }

    @Override
    public CommonOptionsImpl setHttpsSSLParams(HttpsUtils.SSLParams params)
    {
        this.mSslParams = params;
        return this;
    }

    @Override
    public HttpsUtils.SSLParams getHttpsSSLParams()
    {
        return mSslParams;
    }

    @Override
    public CommonOptionsImpl setHostnameVerifier(HostnameVerifier verifier)
    {
        this.mHostnameVerifier = verifier;
        return this;
    }

    @Override
    public HostnameVerifier getHostnameVerifier()
    {
        return mHostnameVerifier;
    }
}
