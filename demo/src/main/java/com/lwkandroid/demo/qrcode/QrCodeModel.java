package com.lwkandroid.demo.qrcode;

import android.graphics.Bitmap;

import com.lwkandroid.lib.common.mvp.MvpBaseModelImpl;
import com.lwkandroid.lib.core.qrcode.QRCodeHelper;

import io.reactivex.rxjava3.core.Observable;


/**
 * Description:Model层
 *
 * @author
 * @date
 */
class QrCodeModel extends MvpBaseModelImpl implements QrCodeContract.IModel
{
    @Override
    public Observable<Bitmap> createQrCodeBitmap(String content, int size)
    {
        return QRCodeHelper.encodeQRCodeByRxJava(content, size);
    }

    @Override
    public Observable<String> parseQrCodePicture(String filePath)
    {
        return QRCodeHelper.decodeQRCodeByRxJava(filePath);
    }
}
