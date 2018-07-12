package com.lwkandroid.wings.utils.pop;

import android.view.View;

/**
 * Created by LWK
 * TODO 设置给配置参数调用PopupWindow显示方式的接口
 */

public interface IPopProxy
{
    /**
     * 显示在指定位置
     *
     * @param parent  父View
     * @param gravity 重心
     * @param x       x偏移量
     * @param y       y偏移量
     * @return PopCreater对象
     */
    PopCreator showAtLocation(View parent, int gravity, int x, int y);

    /**
     * 作为某View的菜单
     *
     * @param anchor 父View
     * @return PopCreater对象
     */
    PopCreator showAsDropDown(View anchor);

    /**
     * 作为某View的菜单
     *
     * @param anchor 父View
     * @param xoff   x偏移量
     * @param yoff   y偏移量
     * @return PopCreater对象
     */
    PopCreator showAsDropDown(View anchor, int xoff, int yoff);

    /**
     * 作为某View的菜单
     *
     * @param anchor  父View
     * @param xoff    x偏移量
     * @param yoff    y偏移量
     * @param gravity 重心，Sdk>=19有效
     * @return PopCreater对象
     */
    PopCreator showAsDropDown(View anchor, int xoff, int yoff, int gravity);
}