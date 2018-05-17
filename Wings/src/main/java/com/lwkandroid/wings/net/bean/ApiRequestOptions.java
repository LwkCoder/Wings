package com.lwkandroid.wings.net.bean;

import android.support.annotation.NonNull;

import com.lwkandroid.wings.net.constants.ApiRequestType;
import com.lwkandroid.wings.net.utils.FormDataMap;
import com.lwkandroid.wings.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.RequestBody;

/**
 * Created by LWK
 * TODO 单次请求的配置
 */
public abstract class ApiRequestOptions<T extends ApiRequestOptions>
{
    /*请求类型*/
    @ApiRequestType.Type
    private int mRequestType;
    /*读取超时时间*/
    private long mReadTimeOut = -1;
    /*写入超时时间*/
    private long mWriteTimeOut = -1;
    /*连接超时时间*/
    private long mConnectTimeOut = -1;
    /*请求域名*/
    private String mBaseUrl;
    /*请求链接*/
    private String mUrl;
    /*单次请求需要添加的拦截器*/
    private Map<String, Interceptor> mInterceptorMap;
    /*单次请求需要添加的网络拦截器*/
    private Map<String, Interceptor> mNetInterceptorMap;
    /*单次请求的表单参数*/
    private FormDataMap mFormDatasMap;
    /*单次请求的Header*/
    private Map<String, String> mHeadersMap;
    /*单次请求需要排除的全局拦截器TAG集合*/
    private List<String> mRemoveInterceptorList;
    /*单次请求需要排除的全局网络拦截器TAG集合*/
    private List<String> mRemoveNetInterceptorList;
    /*单次请求需要排除的全局表单参数Key集合*/
    private List<String> mRemoveFormDatasList;
    /*单次请求需要排除的全局Header参数Key集合*/
    private List<String> mRemoveHeaderList;
    /*单次请求是否去除所有全局拦截器*/
    private boolean mIsRemoveAllGlobalInterceptors = false;
    /*单次请求是否去除所有网络拦截器*/
    private boolean mIsRemoveAllGlobalNetInterceptors = false;
    /*单次请求是否去除所有全局参数*/
    private boolean mIsRemoveAllGlobalFormDatas = false;
    /*单次请求是否去除所有全局Header*/
    private boolean mIsRemoveAllGlobalHeaders = false;
    /*单次请求的ObjectBody*/
    private Object mObjectReqeustBody;
    /*单次请求的RequestBody*/
    private RequestBody mOkHttp3RequestBody;
    /*单次请求的JsonBody*/
    private String mJsonRequestBody;

    /**
     * 获取请求类型
     */
    public int getRequestType()
    {
        return mRequestType;
    }

    /**
     * 设置请求类型
     */
    public T setRequestType(@ApiRequestType.Type int type)
    {
        this.mRequestType = type;
        return (T) this;
    }

    /**
     * 获取读取超时时间
     */
    public long getReadTimeOut()
    {
        return mReadTimeOut;
    }

    /**
     * 设置读取超时时间
     */
    public T setReadTimeOut(long readTimeOut)
    {
        this.mReadTimeOut = readTimeOut;
        return (T) this;
    }

    /**
     * 获取写入超时时间
     */
    public long getWriteTimeOut()
    {
        return mWriteTimeOut;
    }

    /**
     * 设置写入超时时间
     */
    public T setWriteTimeOut(long writeTimeOut)
    {
        this.mWriteTimeOut = writeTimeOut;
        return (T) this;
    }

    /**
     * 获取连接超时时间
     */
    public long getConnectTimeOut()
    {
        return mConnectTimeOut;
    }

    /**
     * 设置连接超时时间
     */
    public T setConnectTimeOut(long connectTimeOut)
    {
        this.mConnectTimeOut = connectTimeOut;
        return (T) this;
    }

    /**
     * 获取请求域名
     */
    public String getBaseUrl()
    {
        return mBaseUrl;
    }

    /**
     * 设置请求域名
     */
    public T setBaseUrl(String baseUrl)
    {
        this.mBaseUrl = baseUrl;
        return (T) this;
    }

    /**
     * 获取请求地址【不含请求域名】
     */
    public String getUrl()
    {
        return mUrl;
    }

    /**
     * 设置请求地址【不含请求域名】
     */
    public T setUrl(String url)
    {
        this.mUrl = url;
        return (T) this;
    }

    /**
     * 获取该次请求添加的拦截器
     */
    public Map<String, Interceptor> getInterceptorMap()
    {
        return mInterceptorMap;
    }

    /**
     * 设置该次请求添加的拦截器
     */
    public T setInterceptorMap(Map<String, Interceptor> interceptorMap)
    {
        this.mInterceptorMap = interceptorMap;
        return (T) this;
    }

    /**
     * 添加该次请求的拦截器
     */
    public T addInterceptorMap(Map<String, Interceptor> interceptorMap)
    {
        if (mInterceptorMap == null)
            mInterceptorMap = new HashMap<>();
        if (interceptorMap != null && interceptorMap.size() > 0)
            mInterceptorMap.putAll(interceptorMap);
        return (T) this;
    }

    /**
     * 添加该次请求的拦截器
     */
    public T addInterceptor(@NonNull String tag, @NonNull Interceptor interceptor)
    {
        if (mInterceptorMap == null)
            mInterceptorMap = new HashMap<>();
        if (StringUtils.isNotEmpty(tag) && interceptor != null)
            mInterceptorMap.put(tag, interceptor);
        return (T) this;
    }

    /**
     * 获取该次请求添加的网络拦截器
     */
    public Map<String, Interceptor> getNetInterceptorMap()
    {
        return mNetInterceptorMap;
    }

    /**
     * 设置该次请求添加的网络拦截器
     */
    public T setNetInterceptorMap(Map<String, Interceptor> netInterceptorMap)
    {
        this.mNetInterceptorMap = netInterceptorMap;
        return (T) this;
    }

    /**
     * 添加该次请求的网络拦截器
     */
    public T addNetInterceptorMap(Map<String, Interceptor> interceptorMap)
    {
        if (mNetInterceptorMap == null)
            mNetInterceptorMap = new HashMap<>();
        mNetInterceptorMap.putAll(interceptorMap);
        return (T) this;
    }

    /**
     * 添加该次请求的网络拦截器
     */
    public T addNetInterceptor(@NonNull String tag, @NonNull Interceptor interceptor)
    {
        if (mNetInterceptorMap == null)
            mNetInterceptorMap = new HashMap<>();
        if (StringUtils.isNotEmpty(tag) && interceptor != null)
            mNetInterceptorMap.put(tag, interceptor);
        return (T) this;
    }

    /**
     * 获取该次请求添加的表单文本数据
     */
    public Map<String, String> getFormDatasMap()
    {
        return mFormDatasMap;
    }

    /**
     * 设置该次请求添加的表单文本数据
     */
    public T setFormDatasMap(FormDataMap formDatasMap)
    {
        this.mFormDatasMap = formDatasMap;
        return (T) this;
    }

    /**
     * 添加该次请求的表单文本数据
     */
    public T addFormDatasMap(Map<String, String> formDatasMap)
    {
        if (mFormDatasMap == null)
            mFormDatasMap = new FormDataMap();
        mFormDatasMap.putAll(formDatasMap);
        return (T) this;
    }

    /**
     * 添加该次请求的表单文本数据
     */
    public T addFormData(@NonNull String key, String value)
    {
        if (mFormDatasMap == null)
            mFormDatasMap = new FormDataMap();
        if (StringUtils.isNotEmpty(key))
            mFormDatasMap.addParam(key, value);
        return (T) this;
    }

    /**
     * 获取该次请求添加的Header
     */
    public Map<String, String> getHeadersMap()
    {
        return mHeadersMap;
    }

    /**
     * 设置该次请求添加的Header
     */
    public T setHeadersMap(Map<String, String> headersMap)
    {
        this.mHeadersMap = headersMap;
        return (T) this;
    }

    /**
     * 添加该次请求的Header
     */
    public T addHeadersMap(Map<String, String> headersMap)
    {
        if (mHeadersMap == null)
            mHeadersMap = new FormDataMap();
        mHeadersMap.putAll(headersMap);
        return (T) this;
    }

    /**
     * 添加该次请求的Header
     */
    public T addHeader(@NonNull String tag, String value)
    {
        if (mHeadersMap == null)
            mHeadersMap = new FormDataMap();
        if (StringUtils.isNotEmpty(tag))
            mHeadersMap.put(tag, value);
        return (T) this;
    }

    /**
     * 获取该次请求需要排除的拦截器
     */
    public List<String> getRemoveInterceptorList()
    {
        return mRemoveInterceptorList;
    }

    /**
     * 设置该次请求需要排除的拦截器
     */
    public T setRemoveInterceptorList(List<String> removeInterceptorList)
    {
        this.mRemoveInterceptorList = removeInterceptorList;
        return (T) this;
    }

    /**
     * 添加该次请求需要排除的拦截器
     */
    public T addRemoveInterceptorList(List<String> tagList)
    {
        if (mRemoveInterceptorList == null)
            mRemoveInterceptorList = new ArrayList<>();
        if (tagList != null && tagList.size() > 0)
            mRemoveInterceptorList.addAll(tagList);
        return (T) this;
    }

    /**
     * 添加该次请求需要排除的拦截器
     */
    public T addRemoveInterceptor(String tag)
    {
        if (mRemoveInterceptorList == null)
            mRemoveInterceptorList = new ArrayList<>();
        mRemoveInterceptorList.add(tag);
        return (T) this;
    }

    /**
     * 获取该次请求需要排除的网络拦截器
     */
    public List<String> getRemoveNetInterceptorList()
    {
        return mRemoveNetInterceptorList;
    }

    /**
     * 设置该次请求需要排除的网络拦截器
     */
    public T setRemoveNetInterceptorList(List<String> removeNetInterceptorList)
    {
        this.mRemoveNetInterceptorList = removeNetInterceptorList;
        return (T) this;
    }

    /**
     * 添加该次请求需要排除的网络拦截器
     */
    public T addRemoveNetInterceptorList(List<String> tagList)
    {
        if (mRemoveNetInterceptorList == null)
            mRemoveNetInterceptorList = new ArrayList<>();
        if (tagList != null && tagList.size() > 0)
            mRemoveNetInterceptorList.addAll(tagList);
        return (T) this;
    }

    /**
     * 添加该次请求需要排除的网络拦截器
     */
    public T addRemoveNetInterceptor(String tag)
    {
        if (mRemoveNetInterceptorList == null)
            mRemoveNetInterceptorList = new ArrayList<>();
        mRemoveNetInterceptorList.add(tag);
        return (T) this;
    }

    /**
     * 获取该次请求需要排除的表单文本数据
     */
    public List<String> getRemoveFormDatasList()
    {
        return mRemoveFormDatasList;
    }

    /**
     * 设置该次请求需要排除的表单文本数据
     */
    public T setRemoveFormDatasList(List<String> removeFormDatasList)
    {
        this.mRemoveFormDatasList = removeFormDatasList;
        return (T) this;
    }

    /**
     * 添加该次请求需要排除的表单文本数据
     */
    public T addRemoveFormDatasList(List<String> keyList)
    {
        if (mRemoveFormDatasList == null)
            mRemoveFormDatasList = new ArrayList<>();
        if (keyList != null & keyList.size() > 0)
            mRemoveFormDatasList.addAll(keyList);
        return (T) this;
    }

    /**
     * 添加该次请求需要排除的表单文本数据
     */
    public T addRemoveFormData(String key)
    {
        if (mRemoveFormDatasList == null)
            mRemoveFormDatasList = new ArrayList<>();
        mRemoveFormDatasList.add(key);
        return (T) this;
    }

    /**
     * 获取该次请求需要排除的Header
     */
    public List<String> getRemoveHeaderList()
    {
        return mRemoveHeaderList;
    }

    /**
     * 设置该次请求需要排除的Header
     */
    public T setRemoveHeaderList(List<String> removeHeaderList)
    {
        this.mRemoveHeaderList = removeHeaderList;
        return (T) this;
    }

    /**
     * 添加该次请求需要排除的Header
     */
    public T addRemoveHeaderList(List<String> tagList)
    {
        if (mRemoveHeaderList == null)
            mRemoveHeaderList = new ArrayList<>();
        if (tagList != null & tagList.size() > 0)
            mRemoveHeaderList.addAll(tagList);
        return (T) this;
    }

    /**
     * 添加该次请求需要排除的Header
     */
    public T addRemoveHeader(String tag)
    {
        if (mRemoveHeaderList == null)
            mRemoveHeaderList = new ArrayList<>();
        mRemoveHeaderList.add(tag);
        return (T) this;
    }

    /**
     * 该次请求排除所有全局拦截器
     */
    public T removeAllGlobalInterceptors()
    {
        mIsRemoveAllGlobalInterceptors = true;
        return (T) this;
    }

    /**
     * 该次请求排除所有全局网络拦截器
     */
    public T removeAllGlobalNetInterceptors()
    {
        mIsRemoveAllGlobalNetInterceptors = true;
        return (T) this;
    }

    /**
     * 该次请求排除所有全局表单文本数据
     */
    public T removeAllGlobalFormDatas()
    {
        mIsRemoveAllGlobalFormDatas = true;
        return (T) this;
    }

    /**
     * 该次请求排除所有全局Header
     */
    public T removeAllGlobalHeaders()
    {
        mIsRemoveAllGlobalHeaders = true;
        return (T) this;
    }

    /**
     * 设置该次请求体为Object类型的RequestBody
     */
    public T setObjectRequestBody(Object body)
    {
        this.mObjectReqeustBody = body;
        return (T) this;
    }

    /**
     * 获取该次请求的Object类型的RequestBody
     */
    public Object getObjectRequestBody()
    {
        return mObjectReqeustBody;
    }

    /**
     * 设置该次请求体为OkHttp3.RequestBody类型的RequestBody
     */
    public T setOkHttp3RequestBody(RequestBody body)
    {
        this.mOkHttp3RequestBody = body;
        return (T) this;
    }

    /**
     * 获取该次请求的OkHttp3类型的RequestBody
     */
    public RequestBody getOkHttp3RequestBody()
    {
        return mOkHttp3RequestBody;
    }

    /**
     * 设置该次请求体为JsonObject类型的RequestBody
     */
    public T setJsonReqeustBody(String body)
    {
        this.mJsonRequestBody = body;
        return (T) this;
    }

    /**
     * 获取该次请求的JsonObject类型的RequestBody
     */
    public String getJsonRequestBody()
    {
        return mJsonRequestBody;
    }

    /**
     * 该次请求是否排除所有全局拦截器
     */
    public boolean isRemoveAllGlobalInterceptors()
    {
        return mIsRemoveAllGlobalInterceptors;
    }

    /**
     * 该次请求是否排除所有全局网络拦截器
     */
    public boolean isRemoveAllGlobalNetInterceptors()
    {
        return mIsRemoveAllGlobalNetInterceptors;
    }

    /**
     * 该次请求是否排除所有全局表单文本数据
     */
    public boolean isRemoveAllGlobalFormDatas()
    {
        return mIsRemoveAllGlobalFormDatas;
    }

    /**
     * 该次请求是否排除所有全局Header
     */
    public boolean isRemoveAllGlobalHeaders()
    {
        return mIsRemoveAllGlobalHeaders;
    }
}
