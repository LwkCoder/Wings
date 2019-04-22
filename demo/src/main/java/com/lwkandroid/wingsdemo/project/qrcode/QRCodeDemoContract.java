package com.lwkandroid.wingsdemo.project.qrcode;

import android.graphics.Bitmap;

import com.lwkandroid.wingsdemo.app.AppBaseModel;
import com.lwkandroid.wingsdemo.app.AppBasePresenter;
import com.lwkandroid.wingsdemo.app.IAppBaseView;

import io.reactivex.Observable;

/**
 * Created by LWK
 *
 */

public interface QRCodeDemoContract
{
    interface View extends IAppBaseView
    {
        void startToScanCode();

        void setQRCodeLabel(String label);

        void setQRCodeContent(String content);

        void setQRCodeContent(Bitmap bitmap);
    }

    abstract class Model extends AppBaseModel
    {
        abstract Observable<String> parseImageQRCode(String imagePath);

        abstract Observable<Bitmap> createQRCode(String content);
    }

    abstract class Presenter extends AppBasePresenter<View, Model>
    {
        abstract void parseImageQRCode(String imagePath);

        abstract void createQRCode(String content);
    }
}
