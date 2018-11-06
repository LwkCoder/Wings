package com.lwkandroid.wings.widget.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private static final String KEY_LAYOUT = "layout";
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
        if (savedInstanceState != null)
            mOptions = (DialogOptions) savedInstanceState.getSerializable(KEY_LAYOUT);

        setStyle(DialogFragment.STYLE_NO_TITLE, mOptions.getThemeStyle());
        getDialog().setCancelable(mOptions.isCancelable());
        getDialog().setCanceledOnTouchOutside(mOptions.isCanceledOnTouchOutside());
        getDialog().setOnCancelListener(mOptions.getCancelListener());
        getDialog().setOnShowListener(mOptions.getShowListener());
        getDialog().setOnDismissListener(mOptions.getDismissListener());
        mOptions.getContentView().initContentView(inflater);
        return mOptions.getContentView().getRealContentView();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_LAYOUT, mOptions);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        getDialog().setOnKeyListener(mOptions.getKeyListener());
    }

    public boolean isShowing()
    {
        return !isHidden() && !isRemoving() && getDialog() != null && getDialog().isShowing();
    }
}
