package com.lwkandroid.demo.rxhttp;

import com.lwkandroid.demo.rxhttp.bean.AccessTokenResultBean;
import com.lwkandroid.lib.core.net.RxHttp;
import com.lwkandroid.lib.core.rx.scheduler.RxSchedulers;
import com.lwkandroid.lib.core.utils.common.StringUtils;

import io.reactivex.Observable;

/**
 * Description:身份验证相关的操作
 *
 * @author LWK
 * @date 2020/4/7
 */
public final class AppTokenModel
{
    private AppTokenModel()
    {
    }

    private static final class HOLDER
    {
        public static final AppTokenModel INSTANCE = new AppTokenModel();
    }

    public static AppTokenModel getInstance()
    {
        return HOLDER.INSTANCE;
    }

    /**
     * 私钥，一般是用户登录后返回，可以用于刷新动态密钥
     */
    private String mPrivateToken;

    /**
     * 动态密钥，一般是登录后访问其他接口时需要
     */
    private String mAccessToken;

    public String getAccessToken()
    {
        return StringUtils.isTrimEmpty(mAccessToken) ? "" : mAccessToken;
    }

    public String getPrivateToken()
    {
        return StringUtils.isTrimEmpty(mPrivateToken) ? "" : mPrivateToken;
    }

    public void setPrivateToken(String token)
    {
        this.mPrivateToken = token;
    }

    public void setAccessToken(String token)
    {
        this.mAccessToken = token;
    }


    public Observable<AccessTokenResultBean> requestRefreshAccessToken()
    {
        return RxHttp.POST(AppUrl.REFRESH_TOKEN)
                .addFormData("pri_token", getPrivateToken())
                .parseRestfulObject(AccessTokenResultBean.class)
                .doOnNext(accessTokenResultBean -> setAccessToken(accessTokenResultBean.getAccess_token()))
                .compose(RxSchedulers.applyIo2Main());
    }
}
