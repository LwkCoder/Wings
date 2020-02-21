package com.lwkandroid.lib.common.java.widgets.pop;

import android.view.View;

/**
 * 设置给配置参数调用PopupWindow显示方式的接口
 *
 * @author LWK
 */
interface IPopProxy
{
    /**
     * 显示在指定位置
     *
     * @param parent  依赖的View
     * @param gravity 重心
     * @param x       x偏移量
     * @param y       y偏移量
     * @return PopCreator对象
     */
    PopCreator showAtLocation(View parent, int gravity, int x, int y);

    /**
     * 作为某View的菜单
     *
     * @param anchor 依赖的View
     * @return PopCreator对象
     */
    PopCreator showAsDropDown(View anchor);

    /**
     * 作为某View的菜单
     *
     * @param anchor 依赖的View
     * @param xoff   x偏移量
     * @param yoff   y偏移量
     * @return PopCreator对象
     */
    PopCreator showAsDropDown(View anchor, int xoff, int yoff);

    /**
     * 作为某View的菜单
     *
     * @param anchor  依赖的View
     * @param xoff    x偏移量
     * @param yoff    y偏移量
     * @param gravity 重心，Sdk>=19有效
     * @return PopCreator对象
     */
    PopCreator showAsDropDown(View anchor, int xoff, int yoff, int gravity);

    /**
     * 作为某view的菜单且支持水平和垂直权重
     *
     * @param anchor   依赖的View
     * @param xGravity 水平方向权重
     * @param yGravity 垂直方向权重
     * @param xoff     x偏移量
     * @param yoff     y偏移量
     * @return PopCreator对象
     */
    PopCreator showWithAnchor(View anchor, @XGravity int xGravity, @YGravity int yGravity, int xoff, int yoff);
}
