package com.lwkandroid.wingsdemo.project.encrypt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lwkandroid.widget.comactionbar.ComActionBar;
import com.lwkandroid.wings.widget.pop.PopCreator;
import com.lwkandroid.wingsdemo.R;
import com.lwkandroid.wingsdemo.app.AppBaseActivity;

import androidx.annotation.Nullable;

/**
 * View层
 *
 * @author LWK
 */
public class EncryptActivity extends AppBaseActivity<EncryptPresenter> implements EncryptContract.IView, EncryptPop.OnItemClickListener
{
    private ComActionBar mActionBar;

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
        mActionBar = find(R.id.cab_encrypt);
        mActionBar.setRightClickListener01(this);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {
        onEncryptTypeChanged(EncryptPop.TYPE_AES);
    }

    @Override
    public void onClick(int id, View view)
    {
        switch (id)
        {
            case R.id.fl_comactionbar_right01:
                EncryptPop pop = new EncryptPop(this);
                PopCreator.create(pop)
                        .showAsDropDown(mActionBar.getRightAreaView01());
                break;
            default:
                break;
        }
    }

    @Override
    public void onEncryptTypeChanged(int type)
    {
        if (type == EncryptPop.TYPE_AES)
        {
            mActionBar.setTitle("AES");
            AESFragment fragment = new AESFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_encrypt_detail, fragment)
                    .commit();
        } else if (type == EncryptPop.TYPE_RSA)
        {
            mActionBar.setTitle("RSA");
            RSAFragment fragment = new RSAFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_encrypt_detail, fragment)
                    .commit();
        }
    }
}
