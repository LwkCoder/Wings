package com.lwkandroid.wingsdemo.project.encrypt;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lwkandroid.wings.utils.ClipboardUtils;
import com.lwkandroid.wings.utils.StringUtils;
import com.lwkandroid.wingsdemo.R;
import com.lwkandroid.wingsdemo.app.AppBaseFragment;

import androidx.annotation.Nullable;

/**
 * Description:
 *
 * @author LWK
 * @date 2019/5/28
 */
public abstract class AbsEncryptFragment extends AppBaseFragment implements RadioGroup.OnCheckedChangeListener
{
    EditText mEdInput;
    RadioGroup mRgOpType;
    LinearLayout mLayoutEncrypt;
    RadioGroup mRgEncryptCodec;
    LinearLayout mLayoutDecrypt;
    RadioGroup mRgDecryptCodec;
    TextView mTvResult;
    String mResultData;

    @Override
    protected int getContentViewId()
    {
        return R.layout.fragment_encrypt;
    }

    @Override
    protected void getArgumentsData(Bundle bundle, Bundle savedInstanceState)
    {

    }

    @Override
    protected void initUI(View contentView)
    {
        mEdInput = (EditText) find(R.id.ed_data_input);
        mRgOpType = (RadioGroup) find(R.id.rg_op_type);
        mLayoutEncrypt = (LinearLayout) find(R.id.ll_encrypt_container);
        mRgEncryptCodec = (RadioGroup) find(R.id.rg_encrypt_codec);
        mLayoutDecrypt = (LinearLayout) find(R.id.ll_decrypt_container);
        mRgDecryptCodec = (RadioGroup) find(R.id.rg_decrypt_codec);
        mTvResult = (TextView) find(R.id.tv_result);

        mRgOpType.setOnCheckedChangeListener(this);
        find(R.id.btn_encrypt).setOnClickListener(this);
        find(R.id.btn_decrypt).setOnClickListener(this);
        find(R.id.btn_copy).setOnClickListener(this);

        RadioButton rbEncrypt = (RadioButton) find(R.id.rb_encrypt);
        rbEncrypt.setChecked(true);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {

    }

    @Override
    public void onClick(int id, View view)
    {
        switch (id)
        {
            case R.id.btn_copy:
                ClipboardUtils.copyText(getResult());
                showShortToast("已复制");
                break;
            case R.id.btn_encrypt:
                if (mRgEncryptCodec.getCheckedRadioButtonId() == R.id.rb_encrypt_base64)
                {
                    String input = mEdInput.getText().toString().trim();
                    if (StringUtils.isEmpty(input))
                    {
                        showShortToast("请输入数据");
                        return;
                    }
                    doEncryptToBase64(input);
                } else
                {
                    String input = mEdInput.getText().toString().trim();
                    if (StringUtils.isEmpty(input))
                    {
                        showShortToast("请输入数据");
                        return;
                    }
                    doEncryptToHexString(input);
                }
                break;
            case R.id.btn_decrypt:

                if (mRgDecryptCodec.getCheckedRadioButtonId() == R.id.rb_decrypt_base64)
                {
                    String input = mEdInput.getText().toString().trim();
                    if (StringUtils.isEmpty(input))
                    {
                        showShortToast("请输入数据");
                        return;
                    }
                    doDecryptFromBase64(input);
                } else
                {
                    String input = mEdInput.getText().toString().trim();
                    if (StringUtils.isEmpty(input))
                    {
                        showShortToast("请输入数据");
                        return;
                    }
                    doDecryptFromHexString(input);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId)
    {
        switch (group.getId())
        {
            case R.id.rg_op_type:
                if (checkedId == R.id.rb_encrypt)
                {
                    mLayoutEncrypt.setVisibility(View.VISIBLE);
                    mLayoutDecrypt.setVisibility(View.GONE);
                    mEdInput.setText(null);
                    mEdInput.setHint("请输入待加密明文");
                    mTvResult.setText(null);
                } else if (checkedId == R.id.rb_decrypt)
                {
                    mLayoutEncrypt.setVisibility(View.GONE);
                    mLayoutDecrypt.setVisibility(View.VISIBLE);
                    mEdInput.setText(null);
                    mEdInput.setHint("请输入待解密密文");
                    mTvResult.setText(null);
                }
                break;
            default:
                break;
        }
    }

    public void updateResult(String result)
    {
        mResultData = result;
    }

    public String getResult()
    {
        return mResultData;
    }

    abstract void doEncryptToBase64(String inputData);

    abstract void doEncryptToHexString(String inputData);

    abstract void doDecryptFromBase64(String inputData);

    abstract void doDecryptFromHexString(String inputData);
}
