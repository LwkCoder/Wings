package com.lwkandroid.wings.net.bean;

import com.lwkandroid.wings.net.cache.opeartor.IDiskCacheOperator;
import com.lwkandroid.wings.net.constants.ApiCacheMode;
import com.lwkandroid.wings.net.constants.ApiRequestType;
import com.lwkandroid.wings.net.cookie.ICookieJar;
import com.lwkandroid.wings.net.exception.IApiExceptionMsgConverter;
import com.lwkandroid.wings.net.https.HttpsUtils;
import com.lwkandroid.wings.net.parser.IApiStringParser;
import com.lwkandroid.wings.net.retry.IAutoRetry;
import com.lwkandroid.wings.net.utils.FormDataMap;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;

import androidx.annotation.NonNull;
import okhttp3.Cookie;
import okhttp3.Interceptor;
import okhttp3.RequestBody;

/**
 * Created by LWK
 * 请求参数的接口
 */
public interface IApiRequestOptions
{
    /**
     * 公共方法
     */
    interface Common<T>
    {
        T setApiStringParser(IApiStringParser parser);

        IApiStringParser getApiStringParser();

        T setReadTimeOut(long readTimeOut);

        long getReadTimeOut();

        T setWriteTimeOut(long writeTimeOut);

        long getWriteTimeOut();

        T setConnectTimeOut(long connectTimeOut);

        long getConnectTimeOut();

        T setBaseUrl(String baseUrl);

        String getBaseUrl();

        Map<String, Interceptor> getInterceptorMap();

        T setInterceptorMap(Map<String, Interceptor> interceptorMap);

        T addInterceptorMap(Map<String, Interceptor> interceptorMap);

        T addInterceptor(@NonNull String tag, @NonNull Interceptor interceptor);

        Map<String, Interceptor> getNetInterceptorMap();

        T setNetInterceptorMap(Map<String, Interceptor> netInterceptorMap);

        T addNetInterceptorMap(Map<String, Interceptor> interceptorMap);

        T addNetInterceptor(@NonNull String tag, @NonNull Interceptor interceptor);

        FormDataMap getFormDataMap();

        T setFormDataMap(FormDataMap formDataMap);

        T addFormData(@NonNull String key, byte value);

        T addFormData(@NonNull String key, int value);

        T addFormData(@NonNull String key, float value);

        T addFormData(@NonNull String key, short value);

        T addFormData(@NonNull String key, long value);

        T addFormData(@NonNull String key, double value);

        T addFormData(@NonNull String key, boolean value);

        T addFormData(@NonNull String key, String value);

        T addFormData(@NonNull String key, Object value);

        Map<String, String> getHeadersMap();

        T setHeadersMap(Map<String, String> headersMap);

        T addHeadersMap(Map<String, String> headersMap);

        T addHeader(@NonNull String tag, String value);

        T setCacheMode(@ApiCacheMode.Mode int mode);

        int getCacheMode();

        T setCacheTime(long cacheTime);

        long getCacheTime();

        T setAutoRetryJudge(IAutoRetry autoRetry);

        IAutoRetry getAutoRetryJudge();

        T setAutoRetryCount(int count);

        int getAutoRetryCount();

        T setAutoRetryDelay(int delay);

        int getAutoRetryDelay();

        T setHttpsCertificates(InputStream... certificates);

        T setHttpsCertificates(InputStream bksFile, String password, InputStream... certificates);

        T setHttpsSSLParams(HttpsUtils.SSLParams params);

        HttpsUtils.SSLParams getHttpsSSLParams();

        T setHostnameVerifier(HostnameVerifier verifier);

        HostnameVerifier getHostnameVerifier();
    }

    /**
     * 全局参数的方法
     */
    interface Global<T> extends Common<T>
    {
        T setApiRestfulResultType(Type type);

        Type getApiRestfulResultType();

        T setApiRestfulResultOkCode(int code);

        int getApiRestfulResultOkCode();

        T removeFormData(String key);

        T clearFormData();

        T removeHeader(String key);

        T clearHeaders();

        T addDynamicHeader(String tag, IApiDynamicHeader callBack);

        T removeDynamicHeader(String tag);

        T clearDynamicHeader();

        Map<String, IApiDynamicHeader> getDynamicHeaderMap();

        T setCookieManager(ICookieJar cookieJarImpl);

        ICookieJar getCookieManager();

        T setCacheVersion(int version);

        int getCacheVersion();

        T setCachePath(String path);

        String getCachePath();

        T setCacheDiskMaxSize(long maxSize);

        long getCacheDiskMaxSize();

        T setCacheOperator(IDiskCacheOperator operator);

        IDiskCacheOperator getCacheOperator();

        T setApiExceptionMsg(IApiExceptionMsgConverter apiExceptionMsg);

        IApiExceptionMsgConverter getApiExceptionMsg();

        T addDynamicFormData(String key, IApiDynamicFormData dataCallBack);

        T removeDynamicFormData(String key);

        T clearDynamicFormData();

        Map<String, IApiDynamicFormData> getDynamicFormDataMap();
    }

    /**
     * 单次请求独有的方法
     */
    interface Custom<T> extends Common<T>
    {
        T setRequestType(@ApiRequestType.Type int type);

        @ApiRequestType.Type
        int getRequestType();

        T setSubUrl(String url);

        String getSubUrl();

        Set<String> getIgnoreInterceptors();

        T setIgnoreInterceptors(Set<String> tags);

        T addIgnoreInterceptors(Set<String> tags);

        T addIgnoreInterceptors(String tag);

        Set<String> getIgnoreNetInterceptors();

        T setIgnoreNetInterceptors(Set<String> tags);

        T addIgnoreNetInterceptors(Set<String> tagList);

        T addIgnoreNetInterceptor(String tag);

        Set<String> getIgnoreFormDataSet();

        T setIgnoreFormDataList(Set<String> keys);

        T addIgnoreFormData(Set<String> keys);

        T addIgnoreFormData(String key);

        Set<String> getIgnoreHeaders();

        T setIgnoreHeaderList(Set<String> tags);

        T addIgnoreHeaderList(Set<String> tags);

        T addIgnoreHeader(String tag);

        T ignoreAllGlobalInterceptors();

        boolean isIgnoreAllGlobalInterceptors();

        T ignoreAllGlobalNetInterceptors();

        boolean isIgnoreAllGlobalNetInterceptors();

        T ignoreAllGlobalFormData();

        boolean isIgnoreAllGlobalFormData();

        T ignoreAllGlobalHeaders();

        boolean isIgnoreAllGlobalHeaders();

        T setObjectRequestBody(Object body);

        Object getObjectRequestBody();

        T setOkHttp3RequestBody(RequestBody body);

        RequestBody getOkHttp3RequestBody();

        T setJsonRequestBody(String body);

        String getJsonRequestBody();

        List<Cookie> getCookieList();

        T addCookie(String name, String value);

        T addCookie(Cookie cookie);

        T addCookies(List<Cookie> cookieList);

        T removeCookie(Cookie cookie);

        T clearAllCookies();

        T setCacheKey(String key);

        String getCacheKey();
    }
}
