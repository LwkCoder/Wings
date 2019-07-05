package com.lwkandroid.wingsdemo.project.qrcode;

import android.graphics.Bitmap;

import com.lwkandroid.wings.qrcode.QRCodeUtils;

import io.reactivex.Observable;

/**
 * @author LWK
 */
public class QRCodeDemoModel extends QRCodeDemoContract.Model
{
    @Override
    Observable<String> parseImageQRCode(String imagePath)
    {
        return QRCodeUtils.decodeQRCodeByRxJava(imagePath);
    }

    @Override
    Observable<Bitmap> createQRCode(String content)
    {
        return QRCodeUtils.encodeQRCodeByRxJava(content, 300);
    }
}
