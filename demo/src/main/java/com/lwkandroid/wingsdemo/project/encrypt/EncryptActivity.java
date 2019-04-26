package com.lwkandroid.wingsdemo.project.encrypt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lwkandroid.wings.utils.EncodeUtils;
import com.lwkandroid.wings.utils.EncryptUtils;
import com.lwkandroid.wings.utils.StringUtils;
import com.lwkandroid.wingsdemo.R;
import com.lwkandroid.wingsdemo.app.AppBaseActivity;

import androidx.annotation.Nullable;

/**
 * Created by LWK
 * View层
 */
public class EncryptActivity extends AppBaseActivity<EncryptPresenter> implements EncryptContract.IView
{
    private TextView mTextView;
    private EditText mEditText;
    private byte[] mAES_Key;
    private byte[] mRSA_PublicKey;
    private byte[] mRSA_PrivateKey;

    private final String AES_KEY_BASE64 = "k8kKp+IYv6y6sM3GO3MV4Q==";

    private final String RSA_PUB_KEY_BASE64 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCJIrSs20dsL5fJSDiBhTI+v89n\n" +
            "gfC11FzjiUgLVABNHmeXV3ChVafR4DIu0ZuyAOliWZaMZ81RxVcdZNgIW63kLYO3\n" +
            "Xwar5OAMNHnUjcfmgLglDpjxwk7xU1L26KEMi1SOtvR3l0wMt3Xw7csztAx2VGHX\n" +
            "9l+M6N+z6H5GXRemTQIDAQAB";

    private final String RSA_PRI_KEY_BASE64 = "MIICXQIBAAKBgQCJIrSs20dsL5fJSDiBhTI+v89ngfC11FzjiUgLVABNHmeXV3Ch\n" +
            "VafR4DIu0ZuyAOliWZaMZ81RxVcdZNgIW63kLYO3Xwar5OAMNHnUjcfmgLglDpjx\n" +
            "wk7xU1L26KEMi1SOtvR3l0wMt3Xw7csztAx2VGHX9l+M6N+z6H5GXRemTQIDAQAB\n" +
            "AoGAF9AC95EMEekhvj3gMf1jACpmZV7W4XwCtS/9ZporgzioXEs+r4UkIvl/PUoA\n" +
            "cyRdYkqULcVIjc5qlhwQt0LUKu3AFxDyFzvQZ8/fZJe8RhQAvAzA8Dvf0tmWaaTh\n" +
            "ynwj2afX4VfkHKRhwQNvJJK/eNf/yI/i4boGayEYXSsvl7ECQQCsXLsMTdHjngIb\n" +
            "OBCHo18jfLS9GvL7cKRTIdgO84v9nNwumpcqhVj9tSwZ6QiWhxvD1DQBqEjV7wUZ\n" +
            "M9uZoeqvAkEAy64H6FY6EDQCtr54jGCBmV39pbGIboEbkZM3+KT306veinniOs2e\n" +
            "Y37112OnKJZ1qUjKkNkQIjvh1zh1qVkNwwJBAJWk8NSu37Jx5bxCAb/xbFFAHR8t\n" +
            "vZXl5xUyBgZ9FqZ6wINJvkKxgWDMIdzhAI7IAKgVnY1u0MXEfjWyW09YT4sCQQDI\n" +
            "nlpbltMKMrCZn3LNkiEVF2dHVV60uaaV9RQkbRlN/PPuIa+bi/x+tAciaJ21dL3g\n" +
            "SDp+Ac/KaIFCo/IBJB5RAkAvVzCTo2YE2Wqzd71KG3rtW6wPu9neVgK0tL/H56qH\n" +
            "o/Xh/d2DNLlJLpiXHUlcPOQzBlGsRYCWJ87ginUhoT6e";

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
        mAES_Key = EncodeUtils.base64Decode(AES_KEY_BASE64);
        mRSA_PrivateKey = EncodeUtils.base64Decode(RSA_PRI_KEY_BASE64);
        mRSA_PublicKey = EncodeUtils.base64Decode(RSA_PUB_KEY_BASE64);
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
                byte[] aesEncryptBase64 = EncryptUtils.encryptAES2Base64(data, mAES_Key);
                String aesEncryptHexString = EncryptUtils.encryptAES2HexString(data, mAES_Key);
                builder
                        .append("AES密钥 Base64编码:\n")
                        .append(AES_KEY_BASE64)
                        .append("\n\nAES加密 Base64编码：\n")
                        .append(new String(aesEncryptBase64, EncryptUtils.UTF8))
                        .append("\n\nAES加密 HexString：\n")
                        .append(aesEncryptHexString)
                        .append("\n\nAES 解密Base64编码:\n")
                        .append(EncryptUtils.decryptBase64AES2String(aesEncryptBase64, mAES_Key))
                        .append("\n\nAES 解密HexString:\n")
                        .append(EncryptUtils.decryptHexStringAES2String(aesEncryptHexString, mAES_Key));
                mTextView.setText(builder.toString());
                break;
            case R.id.btn_encrypt_rsa:
                StringBuilder builder2 = new StringBuilder();
                byte[] rsaEncryptBase64 = EncryptUtils.encryptRSA2Base64(data, mRSA_PublicKey, true);
                String rsaEncryptHexString = EncryptUtils.encryptRSA2HexString(data, mRSA_PublicKey, true);

                builder2.append("RSA公钥 Base64编码：\n")
                        .append(RSA_PUB_KEY_BASE64)
                        .append("\n\nRSA私钥 Base64编码:\n")
                        .append(RSA_PRI_KEY_BASE64)
                        .append("\n\nRSA公钥加密 Base64编码:\n")
                        .append(new String(rsaEncryptBase64, EncryptUtils.UTF8))
                        .append("\n\nRSA公钥加密 HexString:\n")
                        .append(rsaEncryptHexString)
                        .append("\n\nRSA私钥解密 Base64编码:\n")
                        .append(EncryptUtils.decryptBase64RSA2String(rsaEncryptBase64, mRSA_PrivateKey, false))
                        .append("\n\nRSA私钥解密 HexString:\n")
                        .append(EncryptUtils.decryptHexStringRSA2String(rsaEncryptHexString, mRSA_PrivateKey, false));
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
