package com.lwkandroid.wings.widget.dialog;

import android.content.Context;

import com.lwkandroid.wings.utils.Utils;

import java.lang.ref.WeakReference;

import androidx.fragment.app.FragmentActivity;

/**
 * Created by LWK
 *  Dialog工具类
 */
public class DialogCreator implements IDialogOperator
{
    private static final String TAG = "DialogCreator";
    private DialogOptions mOptions;
    private WeakReference<Context> mContextReference;
    private RealDialog mRealDialog;
    private DialogBaseContentView mContentView;

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
        init(activity, options);
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

    /**
     * 初始化
     */
    private void init(Context context, DialogOptions options)
    {
        this.mContextReference = new WeakReference<>(context != null ? context : Utils.getContext());
        this.mOptions = options != null ? options : new DialogOptions();

        mContentView = mOptions.getContentView();
        if (mContentView == null)
        {
            throw new IllegalArgumentException("You have to set a NonNull DialogBaseContentView object!!!");
        }
        mOptions.getContentView().attachToCreator(context, mOptions, this);
        mRealDialog = RealDialog.create(mOptions);
    }
}
