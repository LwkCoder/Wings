package com.lwkandroid.wingsdemo.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import com.lwkandroid.widget.comactionbar.ComActionBar;
import com.lwkandroid.wings.log.KLog;
import com.lwkandroid.wings.utils.EncryptUtils;
import com.lwkandroid.wingsdemo.R;
import com.lwkandroid.wingsdemo.app.AppBaseActivity;
import com.lwkandroid.wingsdemo.project.dialog.DialogDemoActivity;
import com.lwkandroid.wingsdemo.project.image.ImageLoaderDemoActivity;
import com.lwkandroid.wingsdemo.project.list.ListDemoActivity;
import com.lwkandroid.wingsdemo.project.pop.PopDemoActivity;
import com.lwkandroid.wingsdemo.project.qrcode.QRCodeDemoActivity;
import com.lwkandroid.wingsdemo.project.rxhttp.RxHttpDemoActivity;
import com.lwkandroid.wingsdemo.project.test.TestActivity;

import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;

import androidx.annotation.Nullable;

/**
 * RxHttpDemoActivity
 */
public class MainActivity extends AppBaseActivity<MainPresenter> implements MainContract.View
{
    private ComActionBar mActionBar;

    @Override
    public int getContentViewId()
    {
        return R.layout.activity_main;
    }

    @Override
    protected void initUI(View contentView)
    {
        mActionBar = find(R.id.comactionbar);
        addClick(R.id.btn_main_imageloader, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, ImageLoaderDemoActivity.class));
            }
        });
        addClick(R.id.btn_main_rxhttp, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, RxHttpDemoActivity.class));
            }
        });
        addClick(R.id.btn_main_pop, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, PopDemoActivity.class));
            }
        });
        addClick(R.id.btn_main_qrcode, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, QRCodeDemoActivity.class));
            }
        });
        addClick(R.id.btn_main_list, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, ListDemoActivity.class));
            }
        });
        addClick(R.id.btn_main_test, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, TestActivity.class));
            }
        });
        addClick(R.id.btn_main_dialog, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, DialogDemoActivity.class));
            }
        });
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {
        Charset UTF8 = Charset.forName("UTF-8");
        byte[] data = "Lwk505515031!@$".getBytes(UTF8);
        KLog.e("加密数据=" + data);
        byte[] aesKey = EncryptUtils.generateAESKey();
        KLog.e("AES密钥=" + aesKey);

        byte[] aesEncryptString = EncryptUtils.encryptAES(data, aesKey, EncryptUtils.AES_DEFAULT_TRANSFORMATION, EncryptUtils.AES_DEFAULT_IV);
        KLog.e("AES加密，String结果=" + new String(aesEncryptString, UTF8));
        byte[] aesEncryptBase64 = EncryptUtils.encryptAES2Base64(data, aesKey);
        KLog.e("AES加密，Base64结果=" + new String(aesEncryptBase64, UTF8));
        String aesEncryptHexString = EncryptUtils.encryptAES2HexString(data, aesKey);
        KLog.e("AES加密，HexString结果=" + aesEncryptHexString);

        String aesDecryptHexString = EncryptUtils.decryptHexStringAES(aesEncryptHexString, aesKey);
        KLog.e("AES解密，HexString结果=" + aesDecryptHexString);
        String aesDecryptBase64 = EncryptUtils.decryptBase64AES(aesEncryptBase64, aesKey);
        KLog.e("AES解密，Base64结果=" + aesDecryptBase64);
        String aesDecryptString = EncryptUtils.decryptStringAES(aesEncryptString, aesKey);
        KLog.e("AES解密，String结果=" + aesDecryptString);


        try
        {
            Pair<byte[], byte[]> rsaPair = EncryptUtils.generateRSAKeys();
            byte[] rsaEncryptString = EncryptUtils.encryptRSA(data, rsaPair.second, false);
            KLog.e("RSA私钥加密，String结果=" + new String(rsaEncryptString, UTF8));
            byte[] rsaEncryptBase64String = EncryptUtils.encryptRSA2Base64(data, rsaPair.second, false);
            KLog.e("RSA私钥加密，Base64结果=" + new String(rsaEncryptBase64String, UTF8));
            String rsaEncryptHexString = EncryptUtils.encryptRSA2HexString(data, rsaPair.second, false);
            KLog.e("RSA私钥加密，Base64结果=" + rsaEncryptHexString);
            byte[] rsaDecryptHexString = EncryptUtils.decryptHexStringRSA(rsaEncryptHexString, rsaPair.first, true);
            KLog.e("RSA公钥解密，HexString结果=" + new String(rsaDecryptHexString, UTF8));
            byte[] rsaDecryptBase64 = EncryptUtils.decryptBase64RSA(rsaEncryptBase64String, rsaPair.first, true);
            KLog.e("RSA公钥解密，Base64结果=" + new String(rsaDecryptBase64, UTF8));
            byte[] rsaDecryptString = EncryptUtils.decryptStringRSA(rsaEncryptString, rsaPair.first, true);
            KLog.e("RSA公钥解密，String结果=" + new String(rsaDecryptString, UTF8));

        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(int id, View v)
    {
    }

    @Override
    protected void getIntentData(Intent intent, boolean newIntent)
    {

    }

}
