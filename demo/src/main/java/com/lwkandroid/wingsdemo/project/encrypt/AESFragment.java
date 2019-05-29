package com.lwkandroid.wingsdemo.project.encrypt;

import com.lwkandroid.wings.utils.encode.EncodeUtils;
import com.lwkandroid.wings.utils.encrypt.EncryptUtils;

/**
 * Description:
 *
 * @author LWK
 * @date 2019/5/28
 */
public class AESFragment extends AbsEncryptFragment
{
    private final String KEY = "aes_key_20190528";
    private final byte[] KEY_BYTES = KEY.getBytes();
    private final String KEY_BASE64 = EncodeUtils.base64().encode2String(KEY);
    private final String KEY_HEX = EncodeUtils.hex().encode(KEY);

    @Override
    void doEncryptToBase64(String inputData)
    {
        updateResult(EncryptUtils.aes().encryptToBase64String(inputData, KEY_BYTES));
        mTvResult.setText("Key 明文\n" + KEY + "\n"
                + "\nKey Base64\n" + KEY_BASE64 + "\n"
                + "\nKey 16进制\n" + KEY_HEX + "\n"
                + "\nTransformation\n" + EncryptUtils.AES_DEFAULT_TRANSFORMATION + "\n"
                + "\nIV\n" + EncryptUtils.AES_DEFAULT_IV + "\n"
                + "\n加密结果（Base64编码）\n"
                + getResult());
    }

    @Override
    void doEncryptToHexString(String inputData)
    {
        updateResult(EncryptUtils.aes().encryptToHexString(inputData, KEY_BYTES));
        mTvResult.setText("Key 明文\n" + KEY + "\n"
                + "\nKey Base64\n" + KEY_BASE64 + "\n"
                + "\nKey 16进制\n" + KEY_HEX + "\n"
                + "\nTransformation\n" + EncryptUtils.AES_DEFAULT_TRANSFORMATION + "\n"
                + "\nIV\n" + EncryptUtils.AES_DEFAULT_IV + "\n"
                + "\n加密结果（16进制编码）\n"
                + getResult());
    }

    @Override
    void doDecryptFromBase64(String inputData)
    {
        updateResult(EncryptUtils.aes().decryptBase64StringToString(inputData, KEY_BYTES));
        mTvResult.setText("Key 明文\n" + KEY + "\n"
                + "\nKey Base64\n" + KEY_BASE64 + "\n"
                + "\nKey 16进制\n" + KEY_HEX + "\n"
                + "\nTransformation\n" + EncryptUtils.AES_DEFAULT_TRANSFORMATION + "\n"
                + "\nIV\n" + EncryptUtils.AES_DEFAULT_IV + "\n"
                + "\n解密结果\n"
                + getResult());
    }

    @Override
    void doDecryptFromHexString(String inputData)
    {
        updateResult(EncryptUtils.aes().decryptHexStringToString(inputData, KEY_BYTES));
        mTvResult.setText("Key 明文\n" + KEY + "\n"
                + "\nKey Base64\n" + KEY_BASE64 + "\n"
                + "\nKey 16进制\n" + KEY_HEX + "\n"
                + "\nTransformation\n" + EncryptUtils.AES_DEFAULT_TRANSFORMATION + "\n"
                + "\nIV\n" + EncryptUtils.AES_DEFAULT_IV + "\n"
                + "\n解密结果\n"
                + getResult());
    }
}
