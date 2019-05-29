package com.lwkandroid.wingsdemo.project.encrypt;

import com.lwkandroid.wings.utils.encode.EncodeUtils;
import com.lwkandroid.wings.utils.encrypt.EncryptUtils;

/**
 * Description:
 *
 * @author LWK
 * @date 2019/5/28
 */
public class RSAFragment extends AbsEncryptFragment
{
    private final String RSA_PUB_KEY_BASE64 =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC1sPqetUeEn9gII3nuYw/1SltsKzohTgnQQdxRbh/BRQ2qMAVAq12fRmpz+G4rx3drUJMersTxva+fH/6GqMjGoFQ6mQirqNCHycQvotByi+rUdLDFQh1Q1pX2UHr0DBg8ZeDELEMYiwIVfIPxtWWk7KKIyPGYji7jHo8Pewk0IwIDAQAB";

    private final String RSA_PRI_KEY_BASE64 =
            "MIICXQIBAAKBgQC1sPqetUeEn9gII3nuYw/1SltsKzohTgnQQdxRbh/BRQ2qMAVAq12fRmpz+G4rx3drUJMersTxva+fH/6GqMjGoFQ6mQirqNCHycQvotByi+rUdLDFQh1Q1pX2UHr0DBg8ZeDELEMYiwIVfIPxtWWk7KKIyPGYji7jHo8Pewk0IwIDAQABAoGBAJG9UXrm3GQ0t1vn4RFoIs/WAjlk/y7h4xtLCQfEdvJXipt+A2iZTPLKz+8QUocJa/AH5MrS06U5S1svlcG8N7dG2cGsCqRUuHcbeFDUo5DSnsB1UEc0IjNJs7W/0UzPjIfGJ6aPiiNSc8wFmZZgYgjOBCcHzuA2Mjtp98Auc4yZAkEA9V1qBr6v6s6hE1DlKD1DYak2OW0PGQ95D00xOnwe+ZECBBD4vYFTtr5tSBBaeslKDh53BG3+d2PxPSonoB1QJwJBAL2RCddWEsANwVAiTgsw8FgHkJ//VRMAby5Cxx+w4iYD9hb0wfv8tqZ7mVDl2wk6pYXY0rB+rHAkOX/dtvdi/aUCQA9DkW37/NuhoRmfhbH8Ja30pd/qre7ELKbMCYz23cJux+5S0/aODhQnQosiIU7UKNw5/vNymEr7F63TaoWmbS8CQDh0v5eQHNlxv0bcUJ3on0u4RQxBgNa95F7EqIT5qwBjYaEIl9UyODtwyAJd0lP3UWCBaI4Dy9tCDpXUNC+NHbkCQQDnccXIr+A+McthL+IpfwBvLsbP+Brq01Z27/g/D56vugLrPi5vYh6iBQ6ttqozdyShV9uR3/4T4oWV7U5VfGt4";

    private final byte[] mRSA_PublicKey = EncodeUtils.base64().decodeString(RSA_PUB_KEY_BASE64);
    private final byte[] mRSA_PrivateKey = EncodeUtils.base64().decodeString(RSA_PRI_KEY_BASE64);


    @Override
    void doEncryptToBase64(String inputData)
    {
        updateResult(EncryptUtils.rsa().encryptToBase64String(inputData, mRSA_PublicKey, true));
        mTvResult.setText("公钥 Base64\n" + RSA_PUB_KEY_BASE64 + "\n"
                + "\n私钥 Base64\n" + RSA_PRI_KEY_BASE64 + "\n"
                + "\nTransformation\n" + EncryptUtils.RSA_DEFAULT_TRANSFORMATION + "\n"
                + "\n公钥加密结果（Base64编码）\n"
                + getResult());
    }

    @Override
    void doEncryptToHexString(String inputData)
    {
        updateResult(EncryptUtils.rsa().encryptToHexString(inputData, mRSA_PublicKey, true));
        mTvResult.setText("公钥 Base64\n" + RSA_PUB_KEY_BASE64 + "\n"
                + "\n私钥 Base64\n" + RSA_PRI_KEY_BASE64 + "\n"
                + "\nTransformation\n" + EncryptUtils.RSA_DEFAULT_TRANSFORMATION + "\n"
                + "\n公钥加密结果（Base64编码）\n"
                + getResult());
    }

    @Override
    void doDecryptFromBase64(String inputData)
    {
        updateResult(EncryptUtils.rsa().decryptBase64StringToString(inputData, mRSA_PrivateKey, false));
        mTvResult.setText("公钥 Base64\n" + RSA_PUB_KEY_BASE64 + "\n"
                + "\n私钥 Base64\n" + RSA_PRI_KEY_BASE64 + "\n"
                + "\nTransformation\n" + EncryptUtils.RSA_DEFAULT_TRANSFORMATION + "\n"
                + "\n私钥解密结果（Base64编码）\n"
                + getResult());
    }

    @Override
    void doDecryptFromHexString(String inputData)
    {
        updateResult(EncryptUtils.rsa().decryptHexStringToString(inputData, mRSA_PrivateKey, false));
        mTvResult.setText("公钥 Base64\n" + RSA_PUB_KEY_BASE64 + "\n"
                + "\n私钥 Base64\n" + RSA_PRI_KEY_BASE64 + "\n"
                + "\nTransformation\n" + EncryptUtils.RSA_DEFAULT_TRANSFORMATION + "\n"
                + "\n私钥解密结果（Base64编码）\n"
                + getResult());
    }
}
