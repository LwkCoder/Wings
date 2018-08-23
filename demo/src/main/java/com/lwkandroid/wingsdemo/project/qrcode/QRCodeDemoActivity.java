package com.lwkandroid.wingsdemo.project.qrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lwkandroid.imagepicker.ImagePicker;
import com.lwkandroid.imagepicker.data.ImageBean;
import com.lwkandroid.imagepicker.data.ImagePickType;
import com.lwkandroid.wings.qrcode.QRCodeOptions;
import com.lwkandroid.wings.qrcode.QRCodeUtils;
import com.lwkandroid.wings.utils.ClipboardUtils;
import com.lwkandroid.wings.utils.StringUtils;
import com.lwkandroid.wingsdemo.R;
import com.lwkandroid.wingsdemo.app.AppBaseActivity;

import java.util.List;

/**
 * Created by LWK
 * TODO 二维码demo
 */

public class QRCodeDemoActivity extends AppBaseActivity<QRCodeDemoPresenter> implements
        QRCodeDemoContract.View
{
    private final int REQUEST_CODE_SCAN = 100;
    private final int REQUEST_CODE_PICKER = 101;
    private TextView mTvLabel;
    private EditText mEdInput;
    private ImageView mImgResult;

    @Override
    protected void getIntentData(Intent intent, boolean newIntent)
    {

    }

    @Override
    protected int getContentViewId()
    {
        return R.layout.activity_qrcode_demo;
    }

    @Override
    protected void initUI(View contentView)
    {
        mTvLabel = find(R.id.tv_qrcode_demo_label);
        mEdInput = find(R.id.ed_qrcode_demo_input);
        mImgResult = find(R.id.img_qrcode_demo_result);

        addClick(R.id.btn_qrcode_demo_scan, R.id.btn_qrcode_demo_parse,
                R.id.btn_qrcode_demo_copy, R.id.btn_qrcode_demo_create);
    }

    @Override
    protected void initData(Bundle savedInstanceState)
    {

    }

    @Override
    public void startToScanCode()
    {
        QRCodeOptions options =new QRCodeOptions.Builder()
                .setShowAlbum(false)
                .setHintText("扫码啊啊啊啊啊啊")
                .build();
        QRCodeUtils.startScanQRCode(this, REQUEST_CODE_SCAN,options);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN)
        {
            String content = QRCodeUtils.parseScanResult(resultCode, data);
            setQRCodeLabel("扫描结果：");
            setQRCodeContent(content);
        } else if (requestCode == REQUEST_CODE_PICKER && resultCode == RESULT_OK && data != null)
        {
            List<ImageBean> list = data.getParcelableArrayListExtra(ImagePicker.INTENT_RESULT_DATA);
            String imagePath = list.get(0).getImagePath();
            getPresenter().parseImageQRCode(imagePath);
        }
    }

    @Override
    public void setQRCodeLabel(String label)
    {
        mTvLabel.setText(label);
    }

    @Override
    public void setQRCodeContent(String content)
    {
        mEdInput.setText(content);
    }

    @Override
    public void setQRCodeContent(Bitmap bitmap)
    {
        mImgResult.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(int id, View view)
    {
        switch (id)
        {
            case R.id.btn_qrcode_demo_scan:
                startToScanCode();
                break;
            case R.id.btn_qrcode_demo_parse:
                new ImagePicker()
                        .pickType(ImagePickType.SINGLE)
                        .start(this, REQUEST_CODE_PICKER);
                break;
            case R.id.btn_qrcode_demo_copy:
                String content = mEdInput.getText().toString().trim();
                ClipboardUtils.copyText(content);
                showShortToast("已复制");
                break;
            case R.id.btn_qrcode_demo_create:
                String qrContent = mEdInput.getText().toString().trim();
                if (StringUtils.isEmpty(qrContent))
                {
                    showShortToast("请输入二维码内容");
                    mEdInput.requestFocus();
                    return;
                }
                getPresenter().createQRCode(qrContent);
                break;
        }
    }
}
