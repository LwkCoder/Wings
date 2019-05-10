package com.lwkandroid.wingsdemo.project.element;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;

import com.lwkandroid.imagepicker.ImagePicker;
import com.lwkandroid.imagepicker.data.ImageBean;
import com.lwkandroid.imagepicker.data.ImagePickType;
import com.lwkandroid.widget.ninegridview.INineGridImageLoader;
import com.lwkandroid.widget.ninegridview.NineGirdImageContainer;
import com.lwkandroid.widget.ninegridview.NineGridBean;
import com.lwkandroid.widget.ninegridview.NineGridView;
import com.lwkandroid.wings.image.ImageLoader;
import com.lwkandroid.wingsdemo.R;
import com.lwkandroid.wingsdemo.app.AppBaseActivity;
import com.lwkandroid.wingsdemo.utils.element.YcShareElement;
import com.lwkandroid.wingsdemo.utils.element.transition.IShareElementSelector;
import com.lwkandroid.wingsdemo.utils.element.transition.IShareElements;
import com.lwkandroid.wingsdemo.utils.element.transition.ShareElementInfo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

/**
 * View层
 */
public class ImageGridActivity extends AppBaseActivity<ImageGridPresenter> implements ImageGridContract.IView, NineGridView.onItemClickListener, IShareElements
{
    NineGridView mNineGridView;
    NineGridBean mGridBean;
    NineGirdImageContainer mGridContainer;

    @Override
    protected void setBarColor()
    {
        YcShareElement.enableTransition(this);
        super.setBarColor();
    }

    @Override
    protected void getIntentData(Intent intent, boolean newIntent)
    {
    }

    @Override
    protected int getContentViewId()
    {
        return R.layout.activity_image_grid;
    }

    @Override
    protected void initUI(View contentView)
    {
        mNineGridView = find(R.id.ngv_test);
        mNineGridView.setImageLoader(new NineGridDisplayer());
        mNineGridView.setOnItemClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState)
    {
    }


    @Override
    public void onClick(int id, View view)
    {
    }

    @Override
    public void onNineGirdAddMoreClick(int cha)
    {
        new ImagePicker()
                .pickType(ImagePickType.MULTI)
                .maxNum(cha)
                .start(this, 100);
    }

    @Override
    public void onNineGirdItemClick(int position, final NineGridBean gridBean, NineGirdImageContainer imageContainer)
    {
        mGridContainer = imageContainer;
        mGridBean = gridBean;
        Intent intent = new Intent(this, ImageDetailActivity.class);
        intent.putExtra(ImageDetailActivity.INTENT_KEY_INDEX, position);
        intent.putParcelableArrayListExtra(ImageDetailActivity.INTENT_KEY_DATA,
                (ArrayList<? extends Parcelable>) mNineGridView.getDataList());

        //1.2.3版本的NineGridView默认设置了NineGridImageContainer中ImageView的TransitionName为originUrl
        Bundle bundle = YcShareElement.buildOptionsBundle(this, this);
        ActivityCompat.startActivity(this, intent, bundle);
    }

    @Override
    public void onNineGirdItemDeleted(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer)
    {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK)
        {
            List<ImageBean> imageList = data.getParcelableArrayListExtra(ImagePicker.INTENT_RESULT_DATA);
            List<NineGridBean> nineGridList = new ArrayList<>();
            for (ImageBean imageBean : imageList)
            {
                nineGridList.add(new NineGridBean(imageBean.getImagePath(), imageBean.getImagePath(), imageBean.getImagePath()));
            }
            mNineGridView.addDataList(nineGridList);
        }
    }

    @Override
    public void onActivityReenter(int resultCode, final Intent data)
    {
        super.onActivityReenter(resultCode, data);
        YcShareElement.onActivityReenter(this, resultCode, data, new IShareElementSelector()
        {
            @Override
            public void selectShareElements(List<ShareElementInfo> list)
            {
                mGridBean = (NineGridBean) list.get(0).getData();
                List<NineGridBean> dataList = mNineGridView.getDataList();
                int index = 0;
                for (int i = 0; i < dataList.size(); i++)
                {
                    if (dataList.get(i).getOriginUrl().equals(mGridBean.getOriginUrl()))
                    {
                        index = i;
                        break;
                    }
                }
                mGridContainer = (NineGirdImageContainer) mNineGridView.getChildAt(index);
            }
        });
    }

    @Override
    public ShareElementInfo[] getShareElements()
    {
        return new ShareElementInfo[]{new ShareElementInfo(mGridContainer.getImageView(), mGridBean)};
    }

    private static class NineGridDisplayer implements INineGridImageLoader
    {

        @Override
        public void displayNineGridImage(Context context, String url, ImageView imageView)
        {
            ImageLoader.load(url)
                    .show(context, imageView);
        }

        @Override
        public void displayNineGridImage(Context context, String url, ImageView imageView, int width, int height)
        {
            ImageLoader.load(url)
                    .setSize(width, height)
                    .show(context, imageView);
        }
    }
}
