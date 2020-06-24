package com.lwkandroid.demo.qrcode;

import android.graphics.Bitmap;

import com.lwkandroid.lib.common.mvp.MvpBasePresenterImpl;
import com.lwkandroid.lib.common.rx.ApiLoadingObserver;
import com.lwkandroid.lib.core.net.bean.ApiException;
import com.lwkandroid.lib.core.utils.common.ToastUtils;

/**
 * Description:Presenter层
 *
 * @author
 * @date
 */
class QrCodePresenter extends MvpBasePresenterImpl<QrCodeContract.IView, QrCodeContract.IModel>
        implements QrCodeContract.IPresenter<QrCodeContract.IView, QrCodeContract.IModel>
{
    public QrCodePresenter(QrCodeContract.IView viewImpl, QrCodeContract.IModel modelImpl)
    {
        super(viewImpl, modelImpl);
    }

    @Override
    public void createQrCodeBitmap(String content, int size)
    {
        getModelImpl().createQrCodeBitmap(content, size)
                .compose(applyIo2MainUntilOnDestroy())
                .subscribe(new ApiLoadingObserver<Bitmap>()
                {
                    @Override
                    public void onAccept(Bitmap bitmap)
                    {
                        getViewImpl().showQrCodeBitmap(bitmap);
                    }

                    @Override
                    public void onError(ApiException e)
                    {
                        ToastUtils.showShort(e.toString());
                    }
                });
    }

    @Override
    public void parseQrCodePicture(String filePath)
    {
        getModelImpl().parseQrCodePicture(filePath)
                .compose(applyIo2MainUntilOnDestroy())
                .subscribe(new ApiLoadingObserver<String>()
                {
                    @Override
                    public void onAccept(String s)
                    {
                        getViewImpl().showQrCodeContent(s);
                    }

                    @Override
                    public void onError(ApiException e)
                    {
                        ToastUtils.showShort(e.toString());
                    }
                });
    }
}
