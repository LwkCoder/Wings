package com.sources.javacode.net;

import com.lwkandroid.lib.common.widgets.dialog.DialogCreator;
import com.lwkandroid.lib.common.widgets.ui.CommonDialogContent;
import com.lwkandroid.lib.core.app.ActivityLifecycleHelper;
import com.lwkandroid.lib.core.net.bean.ApiException;
import com.lwkandroid.lib.core.net.constants.ApiExceptionCode;
import com.lwkandroid.lib.core.net.exception.ApiErrorConsumer;
import com.lwkandroid.lib.core.net.exception.IRetryCondition;
import com.lwkandroid.lib.core.utils.ToastUtils;

import androidx.fragment.app.FragmentActivity;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;

/**
 * Description:全局网络请求异常处理配置
 *
 * @author LWK
 * @date 2020/4/3
 */
public class AppErrorHandlerConfig
{
    public static final ApiErrorConsumer ERROR_CONSUMER = new ApiErrorConsumer()
    {
        @Override
        public void onAcceptApiException(ApiException e)
        {
            switch (e.getCode())
            {
                case AppErrorCode.TOKEN_EXPIRE:
                    CommonDialogContent content = new CommonDialogContent()
                            .setTitle("登录失效")
                            .setContent("身份验证过期，需要重新登录以验证身份")
                            .setPositive("去登录")
                            .setNegative("退出");
                    DialogCreator.create(content)
                            .setCanceledOnTouchOutside(false)
                            .setCancelable(false)
                            .addOnChildClickListener(content.getPositiveButtonId(), (viewId, view, contentView, creator) -> {
                                creator.dismiss();
                                ToastUtils.showShort("去登录界面");
                            })
                            .addOnChildClickListener(content.getNegativeButtonId(), (viewId, view, contentView, creator) -> {
                                creator.dismiss();
                                ToastUtils.showShort("退出APP");
                            })
                            .show((FragmentActivity) ActivityLifecycleHelper.get().getTopActivity());
                    break;
                default:
                    ToastUtils.showShort("ErrorCode=" + e.getCode() + "\n" + "message=" + e.getDisplayMessage());
                    break;
            }
        }
    };

    public static final IRetryCondition RETRY_CONDITION = throwable -> {
        ApiException exception = ApiException.handleThrowable(throwable);

        if (exception.getCode() == ApiExceptionCode.CONNECT_ERROR ||
                exception.getCode() == ApiExceptionCode.TIMEOUT_ERROR)
        {
            return Single.create((SingleOnSubscribe<Boolean>) emitter -> {
                CommonDialogContent content = new CommonDialogContent()
                        .setTitle("请求失败")
                        .setContent("无法连接接口，是否重试？")
                        .setPositive("重试")
                        .setNegative("取消");
                DialogCreator.create(content)
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(false)
                        .addOnChildClickListener(content.getPositiveButtonId(), (viewId, view, contentView, creator) -> {
                            creator.dismiss();
                            emitter.onSuccess(true);
                        })
                        .addOnChildClickListener(content.getNegativeButtonId(), (viewId, view, contentView, creator) -> {
                            creator.dismiss();
                            emitter.onSuccess(false);
                        })
                        .show((FragmentActivity) ActivityLifecycleHelper.get().getTopActivity());
            });
        } else
        {
            return Single.just(false);
        }
    };
}
