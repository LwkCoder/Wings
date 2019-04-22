package com.lwkandroid.wings.net.bean;

import com.lwkandroid.wings.BuildConfig;
import com.lwkandroid.wings.net.cache.opeartor.IDiskCacheOperator;
import com.lwkandroid.wings.net.constants.ApiCacheMode;
import com.lwkandroid.wings.net.constants.ApiConstants;
import com.lwkandroid.wings.net.cookie.CookieManager;
import com.lwkandroid.wings.net.cookie.ICookieJar;
import com.lwkandroid.wings.net.error.ApiExceptionMsgImpl;
import com.lwkandroid.wings.net.error.IApiExceptionMsg;
import com.lwkandroid.wings.net.https.HttpsUtils;
import com.lwkandroid.wings.net.parser.ApiStringParser;
import com.lwkandroid.wings.net.parser.IApiStringParser;
import com.lwkandroid.wings.net.retry.AutoRetryJudgeImpl;
import com.lwkandroid.wings.net.retry.IAutoRetry;
import com.lwkandroid.wings.net.utils.FormDataMap;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;

import androidx.annotation.NonNull;
import okhttp3.Interceptor;

/**
 * Created by LWK
 *  网络请求全局配置对象
 */
public class ApiGlobalOptions implements IRequestOptions.Common<ApiGlobalOptions>, IRequestOptions.Global<ApiGlobalOptions>
{
    private IRequestOptions.Common mCommonImpl = new CommonOptionsImpl();
    /*网络请求正常的状态码*/
    private int mResultOkCode;
    /*Cookie管理类*/
    private ICookieJar mCookieJar;
    //App版本
    private int mCacheVersion = -1;
    //缓存路径
    private String mCachePath;
    //硬盘缓存大小
    private long mDiskMaxSize = -1;
    //缓存转换器
    private IDiskCacheOperator mCacheOperator = null;
    //设置错误描述的对象
    private IApiExceptionMsg mApiExceptionMsg;

    public ApiGlobalOptions()
    {
        setConnectTimeOut(ApiConstants.CONNECT_TIMEOUT);
        setWriteTimeOut(ApiConstants.WRITE_TIMEOUT);
        setReadTimeOut(ApiConstants.READ_TIMEOUT);
        setApiResultType(ApiResult.class);
        setApiResultOkCode(ApiConstants.RESULT_OK_CODE);
        setApiResultStringParser(new ApiStringParser());
        setApiExceptionMsg(new ApiExceptionMsgImpl());
        setAutoRetryJudge(new AutoRetryJudgeImpl());
        setAutoRetryDelay(1000);
        setCacheMode(ApiCacheMode.NO_CACHE);
        setCacheVersion(BuildConfig.VERSION_CODE);
        setCookieManager(new CookieManager());
    }

    @Override
    public ApiGlobalOptions setApiResultStringParser(IApiStringParser parser)
    {
        mCommonImpl.setApiResultStringParser(parser);
        return this;
    }

    @Override
    public IApiStringParser getApiStringParser()
    {
        return mCommonImpl.getApiStringParser();
    }

    @Override
    public ApiGlobalOptions setApiResultType(Type type)
    {
        mCommonImpl.setApiResultType(type);
        return this;
    }

    @Override
    public Type getApiResultType()
    {
        return mCommonImpl.getApiResultType();
    }

    @Override
    public ApiGlobalOptions setReadTimeOut(long readTimeOut)
    {
        mCommonImpl.setReadTimeOut(readTimeOut);
        return this;
    }

    @Override
    public long getReadTimeOut()
    {
        return mCommonImpl.getReadTimeOut();
    }

    @Override
    public ApiGlobalOptions setWriteTimeOut(long writeTimeOut)
    {
        mCommonImpl.setWriteTimeOut(writeTimeOut);
        return this;
    }

    @Override
    public long getWriteTimeOut()
    {
        return mCommonImpl.getWriteTimeOut();
    }

    @Override
    public ApiGlobalOptions setConnectTimeOut(long connectTimeOut)
    {
        mCommonImpl.setConnectTimeOut(connectTimeOut);
        return this;
    }

    @Override
    public long getConnectTimeOut()
    {
        return mCommonImpl.getConnectTimeOut();
    }

    @Override
    public ApiGlobalOptions setBaseUrl(String baseUrl)
    {
        mCommonImpl.setBaseUrl(baseUrl);
        return this;
    }

    @Override
    public String getBaseUrl()
    {
        return mCommonImpl.getBaseUrl();
    }

    @Override
    public Map<String, Interceptor> getInterceptorMap()
    {
        return mCommonImpl.getInterceptorMap();
    }

    @Override
    public ApiGlobalOptions setInterceptorMap(Map<String, Interceptor> interceptorMap)
    {
        mCommonImpl.setInterceptorMap(interceptorMap);
        return this;
    }

    @Override
    public ApiGlobalOptions addInterceptorMap(Map<String, Interceptor> interceptorMap)
    {
        mCommonImpl.addInterceptorMap(interceptorMap);
        return this;
    }

    @Override
    public ApiGlobalOptions addInterceptor(@NonNull String tag, @NonNull Interceptor interceptor)
    {
        mCommonImpl.addInterceptor(tag, interceptor);
        return this;
    }

    @Override
    public Map<String, Interceptor> getNetInterceptorMap()
    {
        return mCommonImpl.getNetInterceptorMap();
    }

    @Override
    public ApiGlobalOptions setNetInterceptorMap(Map<String, Interceptor> netInterceptorMap)
    {
        mCommonImpl.setNetInterceptorMap(netInterceptorMap);
        return this;
    }

    @Override
    public ApiGlobalOptions addNetInterceptorMap(Map<String, Interceptor> interceptorMap)
    {
        mCommonImpl.addNetInterceptorMap(interceptorMap);
        return this;
    }

    @Override
    public ApiGlobalOptions addNetInterceptor(@NonNull String tag, @NonNull Interceptor interceptor)
    {
        mCommonImpl.addNetInterceptor(tag, interceptor);
        return this;
    }

    @Override
    public FormDataMap getFormDataMap()
    {
        return mCommonImpl.getFormDataMap();
    }

    @Override
    public ApiGlobalOptions setFormDataMap(FormDataMap formDataMap)
    {
        mCommonImpl.setFormDataMap(formDataMap);
        return this;
    }

    @Override
    public ApiGlobalOptions addFormData(@NonNull String key, byte value)
    {
        mCommonImpl.addFormData(key, value);
        return this;
    }

    @Override
    public ApiGlobalOptions addFormData(@NonNull String key, int value)
    {
        mCommonImpl.addFormData(key, value);
        return this;
    }

    @Override
    public ApiGlobalOptions addFormData(@NonNull String key, float value)
    {
        mCommonImpl.addFormData(key, value);
        return this;
    }

    @Override
    public ApiGlobalOptions addFormData(@NonNull String key, short value)
    {
        mCommonImpl.addFormData(key, value);
        return this;
    }

    @Override
    public ApiGlobalOptions addFormData(@NonNull String key, long value)
    {
        mCommonImpl.addFormData(key, value);
        return this;
    }

    @Override
    public ApiGlobalOptions addFormData(@NonNull String key, double value)
    {
        mCommonImpl.addFormData(key, value);
        return this;
    }

    @Override
    public ApiGlobalOptions addFormData(@NonNull String key, boolean value)
    {
        mCommonImpl.addFormData(key, value);
        return this;
    }

    @Override
    public ApiGlobalOptions addFormData(@NonNull String key, String value)
    {
        mCommonImpl.addFormData(key, value);
        return this;
    }

    @Override
    public ApiGlobalOptions addFormData(@NonNull String key, Object value)
    {
        mCommonImpl.addFormData(key, value);
        return this;
    }

    @Override
    public Map<String, String> getHeadersMap()
    {
        return mCommonImpl.getHeadersMap();
    }

    @Override
    public ApiGlobalOptions setHeadersMap(Map<String, String> headersMap)
    {
        mCommonImpl.setHeadersMap(headersMap);
        return this;
    }

    @Override
    public ApiGlobalOptions addHeadersMap(Map<String, String> headersMap)
    {
        mCommonImpl.addHeadersMap(headersMap);
        return this;
    }

    @Override
    public ApiGlobalOptions addHeader(@NonNull String tag, String value)
    {
        mCommonImpl.addHeader(tag, value);
        return this;
    }

    @Override
    public ApiGlobalOptions setCacheMode(@ApiCacheMode.Mode int mode)
    {
        mCommonImpl.setCacheMode(mode);
        return this;
    }

    @Override
    public int getCacheMode()
    {
        return mCommonImpl.getCacheMode();
    }

    @Override
    public ApiGlobalOptions setCacheTime(long cacheTime)
    {
        mCommonImpl.setCacheTime(cacheTime);
        return this;
    }

    @Override
    public long getCacheTime()
    {
        return mCommonImpl.getCacheTime();
    }

    @Override
    public ApiGlobalOptions setAutoRetryJudge(IAutoRetry autoRetry)
    {
        mCommonImpl.setAutoRetryJudge(autoRetry);
        return this;
    }

    @Override
    public IAutoRetry getAutoRetryJudge()
    {
        return mCommonImpl.getAutoRetryJudge();
    }

    @Override
    public ApiGlobalOptions setAutoRetryCount(int count)
    {
        mCommonImpl.setAutoRetryCount(count);
        return this;
    }

    @Override
    public int getAutoRetryCount()
    {
        return mCommonImpl.getAutoRetryCount();
    }

    @Override
    public ApiGlobalOptions setAutoRetryDelay(int delay)
    {
        mCommonImpl.setAutoRetryDelay(delay);
        return this;
    }

    @Override
    public int getAutoRetryDelay()
    {
        return mCommonImpl.getAutoRetryDelay();
    }

    @Override
    public ApiGlobalOptions setHttpsCertificates(InputStream... certificates)
    {
        mCommonImpl.setHttpsCertificates(certificates);
        return this;
    }

    @Override
    public ApiGlobalOptions setHttpsCertificates(InputStream bksFile, String password, InputStream... certificates)
    {
        mCommonImpl.setHttpsCertificates(bksFile, password, certificates);
        return this;
    }

    @Override
    public ApiGlobalOptions setHttpsSSLParams(HttpsUtils.SSLParams params)
    {
        mCommonImpl.setHttpsSSLParams(params);
        return this;
    }

    @Override
    public HttpsUtils.SSLParams getHttpsSSLParams()
    {
        return mCommonImpl.getHttpsSSLParams();
    }

    @Override
    public ApiGlobalOptions setHostnameVerifier(HostnameVerifier verifier)
    {
        mCommonImpl.setHostnameVerifier(verifier);
        return this;
    }

    @Override
    public HostnameVerifier getHostnameVerifier()
    {
        return mCommonImpl.getHostnameVerifier();
    }

    @Override
    public ApiGlobalOptions setApiResultOkCode(int code)
    {
        this.mResultOkCode = code;
        return this;
    }

    @Override
    public int getApiResultOkCode()
    {
        return mResultOkCode;
    }

    @Override
    public ApiGlobalOptions removeFormData(String key)
    {
        getFormDataMap().remove(key);
        return this;
    }

    @Override
    public ApiGlobalOptions clearFormData()
    {
        getFormDataMap().clear();
        return this;
    }

    @Override
    public ApiGlobalOptions removeHeader(String key)
    {
        getHeadersMap().remove(key);
        return this;
    }

    @Override
    public ApiGlobalOptions clearHeaders()
    {
        getHeadersMap().clear();
        return this;
    }

    @Override
    public ApiGlobalOptions setCookieManager(ICookieJar cookieJarImpl)
    {
        this.mCookieJar = cookieJarImpl;
        return this;
    }

    @Override
    public ICookieJar getCookieManager()
    {
        return mCookieJar;
    }

    @Override
    public ApiGlobalOptions setCacheVersion(int version)
    {
        this.mCacheVersion = version;
        return this;
    }

    @Override
    public int getCacheVersion()
    {
        return mCacheVersion;
    }

    @Override
    public ApiGlobalOptions setCachePath(String path)
    {
        this.mCachePath = path;
        return this;
    }

    @Override
    public String getCachePath()
    {
        return mCachePath;
    }

    @Override
    public ApiGlobalOptions setCacheDiskMaxSize(long maxSize)
    {
        this.mDiskMaxSize = maxSize;
        return this;
    }

    @Override
    public long getCacheDiskMaxSize()
    {
        return mDiskMaxSize;
    }

    @Override
    public ApiGlobalOptions setCacheOperator(IDiskCacheOperator operator)
    {
        this.mCacheOperator = operator;
        return this;
    }

    @Override
    public IDiskCacheOperator getCacheOperator()
    {
        return mCacheOperator;
    }

    @Override
    public ApiGlobalOptions setApiExceptionMsg(IApiExceptionMsg apiExceptionMsg)
    {
        this.mApiExceptionMsg = apiExceptionMsg;
        return this;
    }

    @Override
    public IApiExceptionMsg getApiExceptionMsg()
    {
        return mApiExceptionMsg;
    }
}
