package com.lwkandroid.wings.qrcode;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;

import com.lwkandroid.imagepicker.ImagePicker;
import com.lwkandroid.imagepicker.data.ImageBean;
import com.lwkandroid.imagepicker.data.ImagePickType;
import com.lwkandroid.rtpermission.RTPermission;
import com.lwkandroid.rtpermission.listener.OnPermissionResultListener;
import com.lwkandroid.widget.comactionbar.ComActionBar;
import com.lwkandroid.wings.R;
import com.lwkandroid.wings.app.BaseActivity;
import com.lwkandroid.wings.utils.BarUtils;
import com.socks.library.KLog;

import java.util.List;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 二维码扫描界面
 */
public class QRCodeScanActivity extends BaseActivity
        implements QRCodeView.Delegate, OnPermissionResultListener
{
    public static final String KEY_RESULT = "qrcode_result";
    private static final int REQUEST_CODE_IMAGEPICKER = 100;
    private ZXingView mZXingView;
    private ImageView mImgLight;
    private Vibrator mVibrator;
    private boolean mIsDecoding = false;
    private boolean mIsLightOn = false;
    private Disposable mDisposable;
    private ProgressDialog mProgressDialog;

    /**
     * 跳转该界面的公共方法
     */
    public static void start(Activity activity, int requestCode)
    {
        Intent intent = new Intent(activity, QRCodeScanActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void beforeOnCreate(Bundle savedInstanceState)
    {
        super.beforeOnCreate(savedInstanceState);
        BarUtils.setStatusBarTransparent(this);
    }

    @Override
    public int getContentViewId()
    {
        return R.layout.activity_qrcode_scan;
    }

    @Override
    protected void initUI(View contentView)
    {
        ComActionBar actionBar = find(R.id.cab_qrcode);
        BarUtils.compatPaddingWithStatusBar(actionBar);
        actionBar.setRightClickListener01(this);

        mImgLight = find(R.id.img_qrcode_light);
        addClick(mImgLight);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {
        new RTPermission.Builder()
                .permissions(Manifest.permission.CAMERA)
                .start(this, this);
    }

    @Override
    public void onAllGranted(String[] allPermissions)
    {
        ViewStub viewStub = find(R.id.vs_qrcode);
        viewStub.inflate();
        mZXingView = find(R.id.zxv_qrcode);
        mZXingView.setDelegate(this);
        mZXingView.startSpotAndShowRect();
    }

    @Override
    public void onDeined(String[] dinedPermissions)
    {
        showLongToast(R.string.qrcodescan_error);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (!mIsDecoding && mZXingView != null)
            mZXingView.startSpotAndShowRect();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        lightOff();
        if (mZXingView != null)
        {
            mZXingView.stopCamera();
            mZXingView.stopSpotAndHiddenRect();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        dismissDialog();
        if (mZXingView != null)
            mZXingView.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed())
            mDisposable.dispose();
        if (mVibrator != null)
            mVibrator.cancel();
        mVibrator = null;
    }

    @Override
    public void onScanQRCodeSuccess(String result)
    {
        KLog.i("QRCodeScanActivity.onScanQRCodeSuccess:" + result);
        startVibrate();
        Intent intent = new Intent();
        intent.putExtra(KEY_RESULT, result);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onScanQRCodeOpenCameraError()
    {
        KLog.e("QRCodeScanActivity.onScanQRCodeOpenCameraError!!!");
        showLongToast(R.string.qrcodescan_error);
    }

    @Override
    protected void onClick(int id, View v)
    {
        if (id == R.id.fl_comactionbar_right01)
        {
            mIsDecoding = true;
            new ImagePicker()
                    .pickType(ImagePickType.SINGLE)
                    .needCamera(false)
                    .start(this, REQUEST_CODE_IMAGEPICKER);
        } else if (id == R.id.img_qrcode_light)
        {
            swithLight();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGEPICKER)
        {
            if (resultCode == RESULT_OK && data != null)
            {
                List<ImageBean> imageList = data.getParcelableArrayListExtra(ImagePicker.INTENT_RESULT_DATA);
                String picPath = imageList.get(0).getImagePath();
                QRCodeUtils.decodeQRCodeByRxJava(picPath)
                        .doOnSubscribe(new Consumer<Disposable>()
                        {
                            @Override
                            public void accept(Disposable disposable) throws Exception
                            {
                                showDialog();
                            }
                        })
                        .subscribe(new Observer<String>()
                        {
                            @Override
                            public void onSubscribe(Disposable d)
                            {
                                mDisposable = d;
                            }

                            @Override
                            public void onNext(String s)
                            {
                                onScanQRCodeSuccess(s);
                            }

                            @Override
                            public void onError(Throwable e)
                            {
                                dismissDialog();
                                showLongToast(R.string.qrcodescan_decode_error);
                                if (mZXingView != null)
                                    mZXingView.startSpotAndShowRect();
                                mIsDecoding = false;
                            }

                            @Override
                            public void onComplete()
                            {
                                dismissDialog();
                            }
                        });
            } else
            {
                if (mZXingView != null)
                    mZXingView.startSpotAndShowRect();
                mIsDecoding = false;
            }
        }
    }

    //震动
    private void startVibrate()
    {
        if (mVibrator == null)
            mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (mVibrator.hasVibrator())
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                mVibrator.vibrate(VibrationEffect.createOneShot(200, 200));
            else
                mVibrator.vibrate(200);
        }
    }

    //显示Dialog
    private void showDialog()
    {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.qrcode_dialog_message));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    //消除Dialog
    private void dismissDialog()
    {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
        mProgressDialog = null;
    }

    //切换闪光灯
    private void swithLight()
    {
        if (mIsLightOn)
            lightOff();
        else
            lightOn();
    }

    //关闭闪光灯
    private void lightOff()
    {
        mImgLight.setImageResource(R.drawable.qrcode_light_off);
        if (mZXingView != null)
        {
            mZXingView.closeFlashlight();
            mIsLightOn = false;
        }
    }

    //打开闪光灯
    private void lightOn()
    {
        mImgLight.setImageResource(R.drawable.qrcode_light_on);
        if (mZXingView != null)
        {
            mZXingView.openFlashlight();
            mIsLightOn = true;
        }
    }
}
