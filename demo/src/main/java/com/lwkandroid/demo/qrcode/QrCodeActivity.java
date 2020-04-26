package com.lwkandroid.demo.qrcode;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding3.widget.RxTextView;
import com.lwkandroid.demo.R;
import com.lwkandroid.imagepicker.ImagePicker;
import com.lwkandroid.imagepicker.data.ImageBean;
import com.lwkandroid.imagepicker.data.ImagePickType;
import com.lwkandroid.lib.common.mvp.MvpBaseActivity;
import com.lwkandroid.lib.common.qrcode.QRCodeScanHelper;
import com.lwkandroid.lib.common.widgets.dialog.DialogBuilder;
import com.lwkandroid.lib.common.widgets.dialog.IDialogUiController;
import com.lwkandroid.lib.common.widgets.dialog.WingsDialog;
import com.lwkandroid.lib.common.widgets.view.REditText;
import com.lwkandroid.lib.common.widgets.view.RImageView;
import com.lwkandroid.lib.common.widgets.view.RTextView;
import com.lwkandroid.lib.core.annotation.ClickViews;
import com.lwkandroid.lib.core.annotation.FindView;
import com.lwkandroid.lib.core.annotation.ViewInjector;
import com.lwkandroid.lib.core.callback.WingsSupplier;
import com.lwkandroid.lib.core.utils.ClipboardUtils;
import com.lwkandroid.lib.core.utils.StringUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

/**
 * Description:View层
 *
 * @author
 * @date
 */
public class QrCodeActivity extends MvpBaseActivity<QrCodePresenter> implements QrCodeContract.IView<QrCodePresenter>
{
    @FindView(R.id.ed_qrcode)
    private REditText mEdContent;
    @FindView(R.id.btn_qrcode_copy)
    private RTextView mBtnCopy;

    @Override
    protected QrCodePresenter createPresenter()
    {
        return new QrCodePresenter(this, new QrCodeModel());
    }


    @Override
    protected void getIntentData(Intent intent, boolean newIntent)
    {

    }

    @Override
    protected int getContentViewId()
    {
        return R.layout.activity_qr_code;
    }

    @Override
    protected void initUI(View contentView)
    {
        ViewInjector.with(this);
        RxTextView.textChanges(mEdContent)
                .map(charSequence -> StringUtils.isTrimNotEmpty(charSequence.toString()))
                .subscribe(aBoolean -> mBtnCopy.setEnabled(aBoolean));
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {

    }

    @Override
    @ClickViews(values = {R.id.btn_qrcode_clear,
            R.id.btn_qrcode_copy, R.id.btn_qrcode01,
            R.id.btn_qrcode02, R.id.btn_qrcode03})
    public void onClick(int id, View view)
    {
        switch (id)
        {
            case R.id.btn_qrcode_clear:
                mEdContent.setText(null);
                break;
            case R.id.btn_qrcode_copy:
                ClipboardUtils.copyText(mEdContent.getText().toString());
                break;
            case R.id.btn_qrcode01:
                getPresenter().createQrCodeBitmap(mEdContent.getText().toString(), 250);
                break;
            case R.id.btn_qrcode02:
                QRCodeScanHelper.startScanQRCode(this, 100);
                break;
            case R.id.btn_qrcode03:
                new ImagePicker()
                        .pickType(ImagePickType.SINGLE)
                        .needCamera(false)
                        .start(this, 101);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100)
        {
            mEdContent.setText(QRCodeScanHelper.parseScanResult(resultCode, data));
        } else if (requestCode == 101)
        {
            if (resultCode == RESULT_OK)
            {
                List<ImageBean> list = data.getParcelableArrayListExtra(ImagePicker.INTENT_RESULT_DATA);
                getPresenter().parseQrCodePicture(list.get(0).getImagePath());
            }
        }
    }

    @Override
    public void showQrCodeBitmap(Bitmap bitmap)
    {
        DialogBuilder.with(new QrCodeImageController(() -> bitmap))
                .build()
                .show(this);
    }

    @Override
    public void showQrCodeContent(String content)
    {
        mEdContent.setText(content);
    }

    private static class QrCodeImageController implements IDialogUiController
    {

        @FindView(R.id.img_qrcode_content)
        private RImageView mImgContent;
        private WingsSupplier<Bitmap> mSupplier;

        public QrCodeImageController(WingsSupplier<Bitmap> s)
        {
            this.mSupplier = s;
        }

        @Override
        public int getLayoutId()
        {
            return R.layout.dialog_qrcode_content;
        }

        @Override
        public void onCreateView(ViewGroup parentView, WingsDialog dialog)
        {
            ViewInjector.with(this, parentView);
            mImgContent.setImageBitmap(mSupplier.get());
        }

        @Override
        public void onShow(DialogInterface dialog)
        {

        }

        @Override
        public void onDismiss(DialogInterface dialog)
        {

        }

        @Override
        public void onCancel(DialogInterface dialog)
        {

        }

        @Override
        public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event)
        {

        }
    }

}
