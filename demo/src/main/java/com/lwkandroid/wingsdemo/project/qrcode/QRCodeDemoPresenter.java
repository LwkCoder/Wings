package com.lwkandroid.wingsdemo.project.qrcode;

import android.graphics.Bitmap;

import io.reactivex.functions.Consumer;

/**
 * Created by LWK
 *
 */

public class QRCodeDemoPresenter extends QRCodeDemoContract.Presenter
{
    @Override
    void parseImageQRCode(String imagePath)
    {
        getModelImpl().parseImageQRCode(imagePath)
                .compose(this.<String>applyIo2MainWithLifeCycle())
                .subscribe(new Consumer<String>()
                {
                    @Override
                    public void accept(String s) throws Exception
                    {
                        getViewImpl().setQRCodeLabel("二维码图片识别结果：");
                        getViewImpl().setQRCodeContent(s);
                    }
                }, new Consumer<Throwable>()
                {
                    @Override
                    public void accept(Throwable throwable) throws Exception
                    {
                        getViewImpl().setQRCodeLabel("无法识别二维码图片：");
                        getViewImpl().setQRCodeContent(throwable.toString());
                    }
                });
    }

    @Override
    void createQRCode(String content)
    {
        getModelImpl().createQRCode(content)
                .compose(this.<Bitmap>applyIo2MainWithLifeCycle())
                .subscribe(new Consumer<Bitmap>()
                {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception
                    {
                        getViewImpl().setQRCodeContent(bitmap);
                    }
                }, new Consumer<Throwable>()
                {
                    @Override
                    public void accept(Throwable throwable) throws Exception
                    {
                        getViewImpl().showLongToast("无法创建二维码图片：" + throwable.toString());
                    }
                });
    }

    @Override
    public QRCodeDemoContract.Model createModel()
    {
        return new QRCodeDemoModel();
    }
}
