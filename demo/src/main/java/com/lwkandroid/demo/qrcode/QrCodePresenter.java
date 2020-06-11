package com.lwkandroid.demo.qrcode;

import android.graphics.Bitmap;

import com.lwkandroid.lib.common.mvp.MvpBasePresenterImpl;
import com.lwkandroid.lib.common.rx.ApiDialogObserver;
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
                .subscribe(new ApiDialogObserver<Bitmap>()
                {
                    @Override
                    public void subOnNext(Bitmap bitmap)
                    {
                        getViewImpl().showQrCodeBitmap(bitmap);
                    }

                    @Override
                    public void subOnError(ApiException e)
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
                .subscribe(new ApiDialogObserver<String>()
                {
                    @Override
                    public void subOnNext(String s)
                    {
                        getViewImpl().showQrCodeContent(s);
                    }

                    @Override
                    public void subOnError(ApiException e)
                    {
                        ToastUtils.showShort(e.toString());
                    }
                });
    }
}
