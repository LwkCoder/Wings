package com.lwkandroid.wings.net.observer.dialog;

import android.content.DialogInterface;

import com.lwkandroid.wings.net.observer.ApiBaseObserver;
import com.lwkandroid.wings.utils.ResourceUtils;
import com.lwkandroid.wings.widget.dialog.DialogCreator;

import org.reactivestreams.Subscription;

import java.lang.ref.WeakReference;

import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentActivity;
import io.reactivex.disposables.Disposable;

/**
 * Description:带有Dialog的Observer
 *
 * @author LWK
 * @date 2019/6/10
 */
public abstract class ApiLoadingObserver<T> extends ApiBaseObserver<T> implements DialogInterface.OnCancelListener
{
    private WeakReference<FragmentActivity> mActReference;
    private String mMessage;
    private boolean mDialogCancelable = true;
    private boolean mCancelRequestAfterCancelDialog = true;
    private DialogCreator mDialogCreator;
    private Disposable mDisposable;
    private Subscription mSubscription;

    public ApiLoadingObserver(FragmentActivity activity)
    {
        mActReference = new WeakReference<>(activity);
    }

    public ApiLoadingObserver<T> setMessage(String message)
    {
        this.mMessage = message;
        return this;
    }

    public ApiLoadingObserver<T> setMessage(@StringRes int resId)
    {
        this.mMessage = ResourceUtils.getString(resId);
        return this;
    }

    public ApiLoadingObserver<T> setDialogCancelable(boolean b)
    {
        this.mDialogCancelable = b;
        return this;
    }

    public ApiLoadingObserver<T> setCancelRequestAfterCancelDialog(boolean b)
    {
        this.mCancelRequestAfterCancelDialog = b;
        return this;
    }

    @Override
    public void onSubscribe(Disposable d)
    {
        super.onSubscribe(d);
        mDisposable = d;
        showDialog();
    }

    @Override
    public void onSubscribe(Subscription s)
    {
        super.onSubscribe(s);
        mSubscription = s;
        showDialog();
    }

    @Override
    public void onError(Throwable e)
    {
        super.onError(e);
        dismissDialog();
    }

    @Override
    public void onComplete()
    {
        super.onComplete();
        dismissDialog();
    }

    private void showDialog()
    {
        if (mActReference != null && mActReference.get() != null)
        {
            mDialogCreator = DialogCreator.create(new ApiLoadingDialogContent(mMessage))
                    .setCancelable(mDialogCancelable)
                    .setCanceledOnTouchOutside(mDialogCancelable)
                    .setOnCancelListener(this)
                    .show(mActReference.get());
        }
    }

    private void dismissDialog()
    {
        if (mDialogCreator != null)
        {
            mDialogCreator.dismiss();
        }
    }

    @Override
    public void onCancel(DialogInterface dialog)
    {
        if (mCancelRequestAfterCancelDialog)
        {
            if (mDisposable != null && !mDisposable.isDisposed())
            {
                mDisposable.dispose();
            }
            if (mSubscription != null)
            {
                mSubscription.cancel();
            }
        }
    }
}
