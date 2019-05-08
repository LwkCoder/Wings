package com.lwkandroid.wingsdemo.project.ycelement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hw.ycshareelement.YcShareElement;
import com.hw.ycshareelement.transition.IShareElements;
import com.hw.ycshareelement.transition.ShareElementInfo;
import com.lwkandroid.widget.ninegridview.NineGridBean;
import com.lwkandroid.wingsdemo.R;
import com.lwkandroid.wingsdemo.app.AppBaseActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * Description:View层
 *
 * @author
 * @date
 */
public class ImageDetailActivity extends AppBaseActivity<ImageDetailPresenter> implements ImageDetailContract.IView, IShareElements
{
    public static final String INTENT_KEY_DATA = "images";
    public static final String INTENT_KEY_INDEX = "index";

    List<NineGridBean> mDataList = new ArrayList<>();
    int mStartIndex;
    ViewPager mViewPager;
    ImageDetailAdapter mAdapter;

    @Override
    protected void setBarColor()
    {
        super.setBarColor();
        YcShareElement.setEnterTransitions(this, this, true);
    }

    @Override
    protected void getIntentData(Intent intent, boolean newIntent)
    {
        mStartIndex = intent.getIntExtra(INTENT_KEY_INDEX, 0);
        mDataList = intent.getParcelableArrayListExtra(INTENT_KEY_DATA);
    }

    @Override
    protected int getContentViewId()
    {
        return R.layout.activity_image_detail;
    }

    @Override
    protected void initUI(View contentView)
    {
        mViewPager = find(R.id.vp_image_detail);
        mAdapter = new ImageDetailAdapter(this, mDataList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mStartIndex);
        YcShareElement.postStartTransition(this);

        addClick(R.id.btn_image_detail_back, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finishAfterTransition();
            }
        });
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
            default:
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

    @Override
    public void finishAfterTransition()
    {
        YcShareElement.finishAfterTransition(this, this);
        super.finishAfterTransition();
    }

    @Override
    public ShareElementInfo[] getShareElements()
    {
        ImageView imageView = mAdapter.getItemHolder(mViewPager.getCurrentItem()).findView(R.id.img_adapter_image_detail);
        return new ShareElementInfo[]{
                new ShareElementInfo(imageView, mAdapter.getItemData(mViewPager.getCurrentItem()))};
    }
}
