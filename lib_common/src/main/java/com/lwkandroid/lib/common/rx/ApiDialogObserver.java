package com.lwkandroid.lib.common.rx;

import android.content.DialogInterface;

import com.lwkandroid.lib.common.widgets.dialog.DialogBuilder;
import com.lwkandroid.lib.common.widgets.dialog.WingsDialog;
import com.lwkandroid.lib.common.widgets.ui.LoadingDialogController;
import com.lwkandroid.lib.core.app.ActivityLifecycleHelper;
import com.lwkandroid.lib.core.net.observer.ApiBaseObserver;
import com.lwkandroid.lib.core.utils.ResourceUtils;

import org.reactivestreams.Subscription;

import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentActivity;
import io.reactivex.disposables.Disposable;

/**
 * Description:带Dialog的Observer
 *
 * @author LWK
 * @date 2020/3/12
 */
public abstract class ApiDialogObserver<T> extends ApiBaseObserver<T> implements DialogInterface.OnDismissListener
{
    private Disposable mDisposable;
    private Subscription mSubscription;
    private WingsDialog mDialog;
    private boolean mCancelable = true;
    private boolean mCanceledOnTouchOutside = false;
    private String mMessage;
    private float mDarkWindowDegree = 0.0f;

    public ApiDialogObserver<T> setCancelable(boolean cancelable)
    {
        this.mCancelable = cancelable;
        return this;
    }

    public ApiDialogObserver<T> setCanceledOnTouchOutside(boolean canceledOnTouchOutside)
    {
        this.mCanceledOnTouchOutside = canceledOnTouchOutside;
        return this;
    }

    public ApiDialogObserver<T> setMessage(String message)
    {
        this.mMessage = message;
        return this;
    }

    public ApiDialogObserver<T> setMessage(@StringRes int resId)
    {
        return setMessage(ResourceUtils.getString(resId));
    }

    public ApiDialogObserver<T> setDarkWindowDegree(float degree)
    {
        this.mDarkWindowDegree = degree;
        return this;
    }

    @Override
    public void onSubscribe(Disposable d)
    {
        super.onSubscribe(d);
        this.mDisposable = d;
        showDialog();
    }

    @Override
    public void onSubscribe(Subscription s)
    {
        super.onSubscribe(s);
        this.mSubscription = s;
        showDialog();
    }

    @Override
    public void onError(Throwable e)
    {
        dismissDialog();
        super.onError(e);
    }

    @Override
    public void onComplete()
    {
        dismissDialog();
        super.onComplete();
    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {
        release();
    }

    private void showDialog()
    {
        dismissDialog();

        mDialog = DialogBuilder.with(new LoadingDialogController(mMessage))
                .setCancelable(mCancelable)
                .setCanceledOnTouchOutside(mCanceledOnTouchOutside)
                .setDarkWindowDegree(mDarkWindowDegree)
                .setFocusable(true)
                .build()
                .setDialogDismissListener(this)
                .show((FragmentActivity) ActivityLifecycleHelper.get().getTopActivity());
    }

    private void dismissDialog()
    {
        if (mDialog != null)
        {
            mDialog.dismiss();
        }
        mDialog = null;
    }

    private void release()
    {
        if (mDisposable != null && !mDisposable.isDisposed())
        {
            mDisposable.dispose();
            mDisposable = null;
        }
        if (mSubscription != null)
        {
            mSubscription.cancel();
            mSubscription = null;
        }
    }
}
