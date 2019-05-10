package com.lwkandroid.wingsdemo.project.element;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.lwkandroid.widget.ninegridview.NineGridBean;
import com.lwkandroid.wings.utils.BarUtils;
import com.lwkandroid.wings.utils.ResourceUtils;
import com.lwkandroid.wingsdemo.R;
import com.lwkandroid.wingsdemo.app.AppBaseActivity;
import com.lwkandroid.wingsdemo.utils.DragCloseHelper;
import com.lwkandroid.wingsdemo.utils.element.YcShareElement;
import com.lwkandroid.wingsdemo.utils.element.transition.IShareElements;
import com.lwkandroid.wingsdemo.utils.element.transition.ShareElementInfo;
import com.lwkandroid.wingsdemo.widget.CustomViewPager;
import com.lwkandroid.wingsdemo.widget.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * Description:View层
 * <p>
 *
 * @author
 * @date
 */
public class ImageDetailActivity extends AppBaseActivity<ImageDetailPresenter> implements ImageDetailContract.IView,
        IShareElements
{
    public static final String INTENT_KEY_DATA = "images";
    public static final String INTENT_KEY_INDEX = "index";

    List<NineGridBean> mDataList = new ArrayList<>();
    int mStartIndex;
    CustomViewPager mViewPager;
    ImageDetailAdapter mAdapter;
    DragCloseHelper mDragCloseHelper;

    @Override
    protected void setBarColor()
    {
        BarUtils.setAllBarTransparent(this);
        //如果在拖拽返回关闭的时候，导航栏上又出现拖拽的view的情况，就用以下代码。就和微信的表现形式一样
        //隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        YcShareElement.enableTransition(this);
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
        mViewPager.setPageMargin(ResourceUtils.getDimenPixelSize(R.dimen.spacing_small));
        mAdapter = new ImageDetailAdapter(this, mDataList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mStartIndex);
        addClick(R.id.btn_image_detail_back, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finishAfterTransition();
            }
        });

        mDragCloseHelper = new DragCloseHelper(this);
        mDragCloseHelper.setDragCloseViews(find(R.id.fl_image_detail_container), mViewPager);
        mDragCloseHelper.setOnDragCloseListener(new DragCloseHelper.OnDragCloseListener()
        {
            @Override
            public boolean shouldIgnoreTouchEvent()
            {
                //判断是否不拦截触摸事件的情况
                //如果处于滑动返回状态，或图片是放大状态，或ViewPager正在滑动，不接管触摸事件分发
                return mViewPager.getScrollState() != ViewPager.SCROLL_STATE_IDLE ||
                        ((PhotoView) mAdapter.getItemHolder(mViewPager.getCurrentItem())
                                .findView(R.id.img_adapter_image_detail)).getScale() != 1.0f;
            }

            @Override
            public void onDragStart()
            {
                //拖拽开始。可以在此额外处理一些逻辑
            }

            @Override
            public void onDragging(float percent)
            {
                //拖拽中。percent当前的进度，取值0-1，可以在此额外处理一些逻辑
            }

            @Override
            public void onDragCancel()
            {
                //拖拽取消，会自动复原。可以在此额外处理一些逻辑
            }

            @Override
            public void onDragToClose()
            {
                //拖拽触发即将关闭界面
            }
        });

        YcShareElement.postStartTransition(this);
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
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        if (mDragCloseHelper.handleTouchEvent(event))
        {
            return true;
        } else
        {
            return super.dispatchTouchEvent(event);
        }
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
