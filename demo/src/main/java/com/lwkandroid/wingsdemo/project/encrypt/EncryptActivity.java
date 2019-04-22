package com.lwkandroid.wingsdemo.project.encrypt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lwkandroid.wings.utils.EncryptUtils;
import com.lwkandroid.wings.utils.StringUtils;
import com.lwkandroid.wingsdemo.R;
import com.lwkandroid.wingsdemo.app.AppBaseActivity;

import java.security.NoSuchAlgorithmException;

import androidx.annotation.Nullable;

/**
 * Created by LWK
 *  View层
 */
public class EncryptActivity extends AppBaseActivity<EncryptPresenter> implements EncryptContract.IView
{
    private TextView mTextView;
    private EditText mEditText;
    private byte[] mAES_Key;
    private byte[] mRSA_PublicKey;
    private byte[] mRSA_PrivateKey;

    @Override
    protected void getIntentData(Intent intent, boolean newIntent)
    {

    }

    @Override
    protected int getContentViewId()
    {
        return R.layout.activity_encrypt;
    }

    @Override
    protected void initUI(View contentView)
    {
        mTextView = find(R.id.tv_encrypt_result);
        mEditText = find(R.id.ed_encrypt_input);
        addClick(R.id.btn_encrypt_aes);
        addClick(R.id.btn_encrypt_rsa);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {
        mAES_Key = EncryptUtils.generateAESKey();
        try
        {
            Pair<byte[], byte[]> rsaPair = EncryptUtils.generateRSAKeys();
            mRSA_PublicKey = rsaPair.first;
            mRSA_PrivateKey = rsaPair.second;
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(int id, View view)
    {
        String content = getInput();
        if (StringUtils.isEmpty(content))
        {
            showShortToast("请输入内容");
            return;
        }

        byte[] data = content.getBytes(EncryptUtils.UTF8);
        switch (id)
        {
            case R.id.btn_encrypt_aes:
                StringBuilder builder = new StringBuilder();
                byte[] aesEncryptString = EncryptUtils.encryptAES(data, mAES_Key, EncryptUtils.AES_DEFAULT_TRANSFORMATION, EncryptUtils.AES_DEFAULT_IV);
                byte[] aesEncryptBase64 = EncryptUtils.encryptAES2Base64(data, mAES_Key);
                String aesEncryptHexString = EncryptUtils.encryptAES2HexString(data, mAES_Key);
                builder
                        .append("AES密钥:\n")
                        .append(new String(mAES_Key, EncryptUtils.UTF8))
                        .append("\n\nAES加密：\n")
                        .append(new String(aesEncryptString, EncryptUtils.UTF8))
                        .append("\n\nAES加密并Base64编码：\n")
                        .append(new String(aesEncryptBase64, EncryptUtils.UTF8))
                        .append("\n\nAES加密并转16进制：\n")
                        .append(aesEncryptHexString)
                        .append("\n\nAES反向解密:\n")
                        .append(EncryptUtils.decryptAES2String(aesEncryptString, mAES_Key))
                        .append("\n\nAES反向解密Base64编码:\n")
                        .append(EncryptUtils.decryptBase64AES2String(aesEncryptBase64, mAES_Key))
                        .append("\n\nAES反向解密16进制:\n")
                        .append(EncryptUtils.decryptHexStringAES2String(aesEncryptHexString, mAES_Key));
                mTextView.setText(builder.toString());
                break;
            case R.id.btn_encrypt_rsa:
                StringBuilder builder2 = new StringBuilder();
                byte[] rsaEncryptString = EncryptUtils.encryptRSA(data, mRSA_PrivateKey, false);
                byte[] rsaEncryptBase64 = EncryptUtils.encryptRSA2Base64(data, mRSA_PrivateKey, false);
                String rsaEncryptHexString = EncryptUtils.encryptRSA2HexString(data, mRSA_PrivateKey, false);

                builder2.append("RSA公钥：\n")
                        .append(new String(mRSA_PublicKey, EncryptUtils.UTF8))
                        .append("\n\nRSA私钥:\n")
                        .append(new String(mRSA_PrivateKey, EncryptUtils.UTF8))
                        .append("\n\nRSA私钥加密:\n")
                        .append(new String(rsaEncryptString, EncryptUtils.UTF8))
                        .append("\n\nRSA私钥加密并Base64编码:\n")
                        .append(new String(rsaEncryptBase64, EncryptUtils.UTF8))
                        .append("\n\nRSA私钥加密转16进制:\n")
                        .append(rsaEncryptHexString)
                        .append("\n\nRSA公钥反向解密:\n")
                        .append(EncryptUtils.decryptRSA2String(rsaEncryptString, mRSA_PublicKey, true))
                        .append("\n\nRSA公钥反向解密Base64编码:\n")
                        .append(EncryptUtils.decryptBase64RSA2String(rsaEncryptBase64, mRSA_PublicKey, true))
                        .append("\n\nRSA公钥反向解密16进制:\n")
                        .append(EncryptUtils.decryptHexStringRSA2String(rsaEncryptHexString, mRSA_PublicKey, true));
                mTextView.setText(builder2.toString());
                break;
            default:
                break;
        }
    }

    private String getInput()
    {
        return mEditText.getText().toString().trim();
    }

}
