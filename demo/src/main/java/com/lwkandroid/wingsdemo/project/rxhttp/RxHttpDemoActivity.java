package com.lwkandroid.wingsdemo.project.rxhttp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lwkandroid.imagepicker.ImagePicker;
import com.lwkandroid.imagepicker.data.ImageBean;
import com.lwkandroid.imagepicker.data.ImagePickType;
import com.lwkandroid.wings.log.KLog;
import com.lwkandroid.wings.net.RxHttp;
import com.lwkandroid.wings.net.bean.ApiException;
import com.lwkandroid.wings.net.bean.ProgressInfo;
import com.lwkandroid.wings.permission.PermissionDialogUtils;
import com.lwkandroid.wings.utils.AppUtils;
import com.lwkandroid.wings.utils.ResourceUtils;
import com.lwkandroid.wingsdemo.R;
import com.lwkandroid.wingsdemo.app.AppBaseActivity;
import com.lwkandroid.wingsdemo.bean.NonRestFulResult;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * 网络请求框架使用示例
 * 数据来源：https://github.com/jokermonn/-Api/blob/master/Neihan.md#recommend
 *
 * @author LWK
 */
public class RxHttpDemoActivity extends AppBaseActivity<RxHttpDemoPresenter> implements RxHttpDemoContract.View
{
    private TextView mTextView;
    private ImageView mImageView;

    @Override
    protected void getIntentData(Intent intent, boolean newIntent)
    {

    }

    @Override
    public int getContentViewId()
    {
        return R.layout.activity_rxhttp_demo;
    }

    @Override
    protected void initUI(View contentView)
    {
        mImageView = find(R.id.img_rxhttp_demo);
        mTextView = find(R.id.tv_rxhttp_demo);

        addClick(R.id.btn_rxhttp_demo01, v -> getPresenter().requestData());
        addClick(R.id.btn_rxhttp_demo02, v -> getPresenter().requestCustomGet());
        addClick(R.id.btn_rxhttp_download, v -> AndPermission.with(RxHttpDemoActivity.this)
                .runtime()
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                .rationale(PermissionDialogUtils.showRuntimeRationaleDialog())
                .onGranted(data -> getPresenter().requestFileData())
                .onDenied(data -> {
                    PermissionDialogUtils.showSettingIfNeverAskDialog(RxHttpDemoActivity.this, data, 100);
                    showShortToast("不给权限我咋下载啊大兄弟");
                }).start());
        addClick(R.id.btn_rxhttp_demo03, v -> getPresenter().requestNonRestFul());
        addClick(R.id.btn_rxhttp_bitmap, v -> getPresenter().requestBitmapData());
        addClick(R.id.btn_rxhttp_upload, v -> new ImagePicker().pickType(ImagePickType.MULTI)
                .maxNum(9)
                .start(RxHttpDemoActivity.this, 105));
        addClick(R.id.btn_rxhttp_demo04, v -> getPresenter().requestCustomPost());
        addClick(R.id.btn_rxhttp_https, v -> getPresenter().requestHttps());

        //添加自签名证书
        RxHttp.getGlobalOptions().setHttpsCertificates(ResourceUtils.getRaw(R.raw.client),
                "hqxkj168++", ResourceUtils.getRaw(R.raw.server));
        RxHttp.getGlobalOptions().setHostnameVerifier((hostname, sslSession) -> {
            KLog.e("HostName--->" + hostname);
            return true;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 105 && data != null)
        {
            List<ImageBean> imageBeans = data.getParcelableArrayListExtra(ImagePicker.INTENT_RESULT_DATA);
            List<File> files = new ArrayList<>();
            for (ImageBean bean : imageBeans)
            {
                files.add(new File(bean.getImagePath()));
            }
            getPresenter().uploadImages(files);
        }
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {
        getPresenter().requestDataWithAutoRefreshAccessToken();
    }

    @Override
    public void onClick(int id, View v)
    {
    }


    @Override
    public void setHttpResult(String result)
    {
        if (result == null)
        {
            mTextView.setText("");
        } else
        {
            mTextView.setText(result);
        }
    }

    @Override
    public void showDownloadResult(final File file)
    {
        mTextView.setText("下载完成：" + file.getAbsolutePath());
        AppUtils.installApk(file.getAbsolutePath());
    }

    @Override
    public void setDownLoadEnable(boolean enable)
    {
        find(R.id.btn_rxhttp_download).setEnabled(enable);
    }

    @Override
    public void showDownloadProgress(ProgressInfo info)
    {
        mTextView.setText("下载进度：" + info.getPercent() + "%");
    }

    @Override
    public void showUploadProgress(ProgressInfo info)
    {
        mTextView.setText("上传进度:" + info.getPercent());
    }

    @Override
    public void showNonRestFulResult(NonRestFulResult result)
    {
        if (result == null)
        {
            mTextView.setText(null);
        } else
        {
            mTextView.setText(result.toString());
        }
    }

    @Override
    public void showImageBitmap(Bitmap bitmap)
    {
        mImageView.setImageBitmap(bitmap);
    }

    @Override
    public void showHttpError(ApiException e)
    {
        mTextView.setText("请求错误：" + e.toString());
    }

}
