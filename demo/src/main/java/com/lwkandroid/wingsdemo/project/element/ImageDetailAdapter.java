package com.lwkandroid.wingsdemo.project.element;

import android.content.Context;
import android.view.ViewGroup;

import com.lwkandroid.vpadapter.VPHolder;
import com.lwkandroid.vpadapter.VPSingleItemAdapter;
import com.lwkandroid.widget.ninegridview.NineGridBean;
import com.lwkandroid.wings.image.ImageLoader;
import com.lwkandroid.wings.utils.ScreenUtils;
import com.lwkandroid.wingsdemo.R;
import com.lwkandroid.wingsdemo.widget.photoview.PhotoView;

import java.util.List;

import androidx.core.view.ViewCompat;

/**
 * Description:
 *
 * @author LWK
 * @date 2019/5/7
 */
public class ImageDetailAdapter extends VPSingleItemAdapter<NineGridBean>
{
    private final int MAX_WIDTH = ScreenUtils.getScreenWidth();
    private final int MAX_HEIGHT = ScreenUtils.getScreenHeight();

    public ImageDetailAdapter(Context context, List<NineGridBean> dataList)
    {
        super(context, dataList);
    }

    @Override
    public VPHolder onCreateViewHolder(ViewGroup parent)
    {
        return createHolderFromLayout(getContext(), parent, R.layout.adapter_image_detail);
    }

    @Override
    public void onBindView(VPHolder holder, NineGridBean nineGridBean, int position)
    {
        PhotoView imageView = holder.findView(R.id.img_adapter_image_detail);
        ViewCompat.setTransitionName(imageView, nineGridBean.getOriginUrl());
        ImageLoader.load(nineGridBean.getOriginUrl())
                .setSize(MAX_WIDTH, MAX_HEIGHT)
                .show(getContext(), imageView);
    }
}
