package com.lwkandroid.wings.widget.pop;

import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by LWK
 *  定义PopCreator外部可调方法的接口
 */

public interface IPopOperator
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
     * 作为某view的菜单且支持水平和垂直权重
     *
     * @param anchor   依赖的View
     * @param xGravity 水平方向权重
     * @param yGravity 垂直方向权重
     * @param xoff     x偏移量
     * @param yoff     y偏移量
     * @param options  配置参数
     * @return PopCreater对象
     */
    PopCreator showWithAnchor(View anchor, @XGravity int xGravity, @YGravity int yGravity, int xoff, int yoff, PopOptions options);

    /**
     * Updates the state of the popup window, if it is currently being displayed,
     * from the currently set state.
     * 更新PopupWindow状态，如果当前已是显示状态，就从当前状态更新
     */
    void update();

    /**
     * Updates the dimension of the popup window.
     * 更新PopupWindow的大小
     * <p>
     * Calling this function also updates the window with the current popup
     * state as described for {@link #update()}.
     * 调用该方法后也会更新PopupWindow的状态
     *
     * @param width  the new width in pixels, must be >= 0 or -1 to ignore
     * @param height the new height in pixels, must be >= 0 or -1 to ignore
     */
    void update(int width, int height);

    /**
     * Updates the position and the dimension of the popup window.
     * 更新PopupWindow的位置
     * <p>
     * Width and height can be set to -1 to update location only. Calling this
     * function also updates the window with the current popup state as
     * described for {@link #update()}.
     * 如果大小没有改变，参数可以传-1
     * 调用该方法后也会更新PopupWindow的状态
     *
     * @param x      the new x location
     * @param y      the new y location
     * @param width  the new width in pixels, must be >= 0 or -1 to ignore
     * @param height the new height in pixels, must be >= 0 or -1 to ignore
     */
    void update(int x, int y, int width, int height);

    /**
     * Updates the position and the dimension of the popup window.
     * 更新PopupWindow的大小和位置
     * <p>
     * Width and height can be set to -1 to update location only. Calling this
     * function also updates the window with the current popup state as
     * described for {@link #update()}.
     * 如果大小没有改变，参数可以传-1
     * 调用该方法后也会更新PopupWindow的状态
     *
     * @param x      the new x location
     * @param y      the new y location
     * @param width  the new width in pixels, must be >= 0 or -1 to ignore
     * @param height the new height in pixels, must be >= 0 or -1 to ignore
     * @param force  {@code true} to reposition the window even if the specified
     *               position already seems to correspond to the LayoutParams,
     *               {@code false} to only reposition if needed
     */
    void update(int x, int y, int width, int height, boolean force);

    /**
     * Updates the position and the dimension of the popup window.
     * 更新PopupWindow的大小和位置
     * <p>
     * Calling this function also updates the window with the current popup
     * state as described for {@link #update()}.
     * 调用该方法后也会更新PopupWindow的状态
     *
     * @param anchor the popup's anchor view
     * @param width  the new width in pixels, must be >= 0 or -1 to ignore
     * @param height the new height in pixels, must be >= 0 or -1 to ignore
     */
    void update(View anchor, int width, int height);

    /**
     * Updates the position and the dimension of the popup window.
     * 更新PopupWindow的大小和位置
     * <p>
     * Width and height can be set to -1 to update location only. Calling this
     * function also updates the window with the current popup state as
     * described for {@link #update()}.
     * 如果大小没有改变，参数可以传-1
     * 调用该方法后也会更新PopupWindow的状态
     * <p>
     * If the view later scrolls to move {@code anchor} to a different
     * location, the popup will be moved correspondingly.
     * 如果依附的父View后来滚到到新的位置，PopupWindow也会跟随
     *
     * @param anchor the popup's anchor view
     * @param xoff   x offset from the view's left edge
     * @param yoff   y offset from the view's bottom edge
     * @param width  the new width in pixels, must be >= 0 or -1 to ignore
     * @param height the new height in pixels, must be >= 0 or -1 to ignore
     */
    void update(View anchor, int xoff, int yoff, int width, int height);

    /**
     * 获取PopupWindow对象
     */
    PopupWindow getPopupWindow();

    /**
     * 获取ContentView对象
     */
    PopBaseContentView getContentView();

    /**
     * 判断是否显示
     */
    boolean isShowing();

    /**
     * 关闭
     */
    void dismiss();
}
