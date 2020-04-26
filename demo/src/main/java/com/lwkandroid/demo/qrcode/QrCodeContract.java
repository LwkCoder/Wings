package com.lwkandroid.demo.qrcode;

import android.graphics.Bitmap;

import com.lwkandroid.lib.common.mvp.IMvpBaseModel;
import com.lwkandroid.lib.common.mvp.IMvpBasePresenter;
import com.lwkandroid.lib.common.mvp.IMvpBaseView;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;

/**
 * Description:契约层
 *
 * @author
 * @date
 */
interface QrCodeContract
{
    interface IView<P extends LifecycleObserver> extends IMvpBaseView<P>
    {
        void showQrCodeBitmap(Bitmap bitmap);

        void showQrCodeContent(String content);
    }

    interface IModel extends IMvpBaseModel
    {
        Observable<Bitmap> createQrCodeBitmap(String content, int size);

        Observable<String> parseQrCodePicture(String filePath);
    }

    interface IPresenter<V extends LifecycleOwner, M> extends IMvpBasePresenter<V, M>
    {
        void createQrCodeBitmap(String content, int size);

        void parseQrCodePicture(String filePath);
    }
}
