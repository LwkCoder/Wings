package com.lwkandroid.wings.utils.pop;

import android.view.View;

/**
 * Created by LWK
 * TODO 定义PopupCreator外部可调方法的接口
 */

public interface IPopOpeartor
{
    /**
     * 显示在指定位置
     *
     * @param parent  父View
     * @param gravity 重心
     * @param x       x偏移量
     * @param y       y偏移量
     * @param options 配置参数
     * @return PopCreater对象
     */
    PopCreator showAtLocation(View parent, int gravity, int x, int y, PopOptions options);

    /**
     * 作为某View的菜单
     *
     * @param anchor  父View
     * @param options 配置参数
     * @return PopCreater对象
     */
    PopCreator showAsDropDown(View anchor, PopOptions options);

    /**
     * 作为某View的菜单
     *
     * @param anchor  父View
     * @param xoff    x偏移量
     * @param yoff    y偏移量
     * @param options 配置参数
     * @return PopCreater对象
     */
    PopCreator showAsDropDown(View anchor, int xoff, int yoff, PopOptions options);

    /**
     * 作为某View的菜单
     *
     * @param anchor  父View
     * @param xoff    x偏移量
     * @param yoff    y偏移量
     * @param gravity 重心，Sdk>=19有效
     * @param options 配置参数
     * @return PopCreater对象
     */
    PopCreator showAsDropDown(View anchor, int xoff, int yoff, int gravity, PopOptions options);

    /**
     * 判断是否显示
     */
    boolean isShowing();

    /**
     * 关闭
     */
    void dismiss();
}
