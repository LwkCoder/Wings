package com.lwkandroid.wings.widget.dialog;

import android.content.Context;

import com.lwkandroid.wings.Wings;

import java.lang.ref.WeakReference;

import androidx.fragment.app.FragmentActivity;

/**
 * Dialog工具类
 *
 * @author LWK
 */
public class DialogCreator implements IDialogOperator
{
    private static final String TAG = "DialogCreator";
    private WeakReference<Context> mContextReference;
    private RealDialog mRealDialog;

    public static DialogOptions create(DialogBaseContentView contentView)
    {
        return new DialogOptions().setContentView(contentView);
    }

    DialogCreator()
    {
    }

    protected Context getContext()
    {
        return mContextReference != null ? mContextReference.get() : null;
    }

    @Override
    public DialogCreator show(FragmentActivity activity, DialogOptions options)
    {
        this.mContextReference = new WeakReference<>(activity != null ? activity : Wings.getContext());
        if (options == null)
        {
            options = new DialogOptions();
        }
        DialogBaseContentView contentView = options.getContentView();
        if (contentView == null)
        {
            throw new IllegalArgumentException("You have to set a NonNull DialogBaseContentView object!!!");
        }
        options.getContentView().attachToCreator(activity, options, this);
        mRealDialog = RealDialog.create(options);
        if (mRealDialog != null)
        {
            mRealDialog.show(activity.getSupportFragmentManager(), TAG);
        }
        return this;
    }

    @Override
    public boolean isShowing()
    {
        return mRealDialog != null && mRealDialog.isShowing();
    }

    @Override
    public void dismiss()
    {
        if (isShowing())
        {
            mRealDialog.dismiss();
        }
        mRealDialog = null;
    }

    @Override
    public void cancel()
    {
        if (mRealDialog != null)
        {
            mRealDialog.cancel();
        }
    }
}
