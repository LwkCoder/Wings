package com.lwkandroid.wings.widget.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * Created by LWK
 * TODO 真正弹出的Dialog
 */
public class RealDialog extends DialogFragment
{
    private static final String KEY_BUNDLE = "options";
    private DialogOptions mOptions;

    public static RealDialog create(DialogOptions options)
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_BUNDLE, options);
        RealDialog dialog = new RealDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mOptions = (DialogOptions) getArguments().getSerializable(KEY_BUNDLE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        setStyle(DialogFragment.STYLE_NO_TITLE, mOptions.getThemeStyle());
        Window window = getDialog().getWindow();
        if (window != null)
        {
            window.setGravity(mOptions.getLayoutGravity());
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setDimAmount(mOptions.getDarkWindowDegree());
            window.setWindowAnimations(mOptions.getAnimStyle());
        }

        getDialog().setCancelable(mOptions.isCancelable());
        getDialog().setCanceledOnTouchOutside(mOptions.isCanceledOnTouchOutside());
        getDialog().setOnCancelListener(mOptions.getCancelListener());
        getDialog().setOnShowListener(mOptions.getShowListener());
        getDialog().setOnDismissListener(mOptions.getDismissListener());
        mOptions.getContentView().initContentView(inflater);
        return mOptions.getContentView().getRealContentView();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null)
            window.setLayout(mOptions.getLayoutParams().width, mOptions.getLayoutParams().height);
        getDialog().setOnKeyListener(mOptions.getKeyListener());
    }

    public boolean isShowing()
    {
        return !isHidden() && !isRemoving() && getDialog() != null && getDialog().isShowing();
    }

    public void cancel()
    {
        if (getDialog() != null)
            getDialog().cancel();
    }
}
