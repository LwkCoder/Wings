package com.lwkandroid.demo.rxhttp;

import android.app.Activity;
import android.content.Intent;

import com.lwkandroid.demo.rxhttp.bean.AccessTokenResultBean;
import com.lwkandroid.demo.rxhttp.login.LoginActivity;
import com.lwkandroid.lib.common.widgets.dialog.DialogBuilder;
import com.lwkandroid.lib.common.widgets.ui.CommonDialogController;
import com.lwkandroid.lib.core.app.ActivityLifecycleHelper;
import com.lwkandroid.lib.core.log.KLog;
import com.lwkandroid.lib.core.net.bean.ApiException;
import com.lwkandroid.lib.core.net.constants.ApiExceptionCode;
import com.lwkandroid.lib.core.net.exception.ApiErrorConsumer;
import com.lwkandroid.lib.core.net.exception.IRetryCondition;
import com.lwkandroid.lib.core.net.observer.ApiObserver;
import com.lwkandroid.lib.core.utils.common.ToastUtils;

import androidx.fragment.app.FragmentActivity;
import io.reactivex.Single;

/**
 * Description:异常捕获的配置
 *
 * @author LWK
 * @date 2020/4/7
 */
public final class AppErrorConfig
{
    private AppErrorConfig()
    {
    }

    public static final ApiErrorConsumer ERROR_CONSUMER = new ApiErrorConsumer()
    {
        @Override
        public void onAcceptedException(ApiException e)
        {
            switch (e.getCode())
            {
                case AppErrorCode.USER_NOT_LOGIN:
                    CommonDialogController content = new CommonDialogController()
                            .setTitle("登录失效")
                            .setContent("身份验证过期，需要重新登录以验证身份")
                            .setPositive("去登录")
                            .setNegative("退出");
                    DialogBuilder.with(content)
                            .setCanceledOnTouchOutside(false)
                            .setCancelable(false)
                            .build()
                            .addOnChildClickListener(content.getPositiveButtonId(), (viewId, view, contentView, dialog) -> {
                                dialog.dismiss();
                                Activity tActivity = ActivityLifecycleHelper.get().getTopActivity();
                                Intent intent = new Intent(tActivity, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                tActivity.startActivity(intent);
                            })
                            .addOnChildClickListener(content.getNegativeButtonId(), (viewId, view, contentView, dialog) -> {
                                dialog.dismiss();
                                ToastUtils.showShort("点击了退出APP");
                            })
                            .show((FragmentActivity) ActivityLifecycleHelper.get().getTopActivity());
                    break;
                case AppErrorCode.ACCESS_TOKEN_EXPIRE:
                    //这个不用提示，下面的自动重试会处理
                    break;
                default:
                    ToastUtils.showShort("请求失败：" + e.getCode() + " " + e.getDisplayMessage());
            }
        }
    };

    public static final IRetryCondition RETRY_CONDITION = throwable -> {
        ApiException e = ApiException.handleThrowable(throwable);
        switch (e.getCode())
        {
            //网络连接超时、连接错误需要自动重试
            case ApiExceptionCode.CONNECT_ERROR:
            case ApiExceptionCode.TIMEOUT_ERROR:
                return Single.just(true);
            //身份验证过期后需要自动刷新动态密钥后重试
            case AppErrorCode.ACCESS_TOKEN_EXPIRE:
                return Single.create(emitter -> AppTokenModel.getInstance().requestRefreshAccessToken()
                        .subscribe(new ApiObserver<AccessTokenResultBean>()
                        {
                            @Override
                            public void onAccept(AccessTokenResultBean resultBean)
                            {
                                KLog.i("自动刷新动态密钥完成：" + resultBean.toString());
                                emitter.onSuccess(true);
                            }

                            @Override
                            public void onError(ApiException e1)
                            {
                                emitter.onSuccess(false);
                            }
                        }));
            default:
                return Single.just(false);
        }
    };
}
