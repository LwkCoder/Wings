package com.lwkandroid.wingsdemo.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.lwkandroid.wings.log.KLog;
import com.lwkandroid.wings.widget.AdvancedWebView;
import com.lwkandroid.wingsdemo.R;

/**
 * 带有WebView的Fragment
 *
 * @author LWK
 */
public abstract class AppWebBaseFragment<P extends AppBasePresenter> extends AppBaseFragment<P>
        implements AdvancedWebView.Listener
{
    private static final String TAG = "AppWebBaseFragment";
    protected AdvancedWebView mWebView;
    protected String mCurrentUrl;

    @Override
    protected void initUI(View contentView)
    {
        //先初始化WebView
        mWebView = find(R.id.advanced_webview);
        if (mWebView != null)
        {
            mWebView.setListener(this, this);
            mWebView.setGeolocationEnabled(false);
            mWebView.setMixedContentAllowed(true);
            mWebView.setCookiesEnabled(true);
            mWebView.setThirdPartyCookiesEnabled(true);
            //允许远程调试
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            {
                AdvancedWebView.setWebContentsDebuggingEnabled(true);
            }
            WebSettings settings = mWebView.getSettings();
            settings.setAppCacheEnabled(true);
            settings.setAppCachePath(getActivity().getExternalCacheDir().getAbsolutePath());
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
            mWebView.setWebChromeClient(new WebChromeClient()
            {
                @Override
                public void onProgressChanged(WebView view, int newProgress)
                {
                    super.onProgressChanged(view, newProgress);
                    KLog.i(TAG, "WebChromeClient.onProgressChanged: progress=" + newProgress);
                    this.onProgressChanged(view, newProgress);
                }

                @Override
                public void onReceivedTitle(WebView view, String title)
                {
                    super.onReceivedTitle(view, title);
                    KLog.i(TAG, "WebChromeClient.onReceivedTitle: title=" + title);
                    this.onReceivedTitle(view, title);
                }
            });
        }

        _initUI(contentView);
    }

    /**
     * 子类实现该方法，初始化其他UI
     */
    public abstract void _initUI(View contentView);

    @Override
    public void onPageStarted(String url, Bitmap favicon)
    {
        KLog.i(TAG, "onPageStarted: url=" + url);
    }

    @Override
    public void onPageFinished(String url)
    {
        KLog.i(TAG, "onPageFinished: url=" + url);
        mCurrentUrl = url;
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl)
    {
        KLog.e(TAG, "onPageError: url=" + failingUrl + "\n"
                + "errCode=" + errorCode + "\n"
                + "desc=" + description);
    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent)
    {
        //网址内请求下载文件会回调这里
        KLog.i(TAG, "onDownloadRequested: url=" + url + "\n"
                + "suggestedFilename=" + suggestedFilename + "\n"
                + "mimeType=" + mimeType + "\n"
                + "contentLength=" + contentLength + "\n"
                + "contentDisposition=" + contentDisposition + "\n"
                + "userAgent=" + userAgent);
    }

    @Override
    public void onExternalPageRequest(String url)
    {
        KLog.i(TAG, "onExternalPageRequest: url=" + url);
    }

    @Override
    public void onResume()
    {
        mWebView.onResume();
        super.onResume();
    }

    @Override
    public void onPause()
    {
        mWebView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy()
    {
        mWebView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
    }
}
