package com.lwkandroid.lib.common.widgets.ui.helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.lwkandroid.lib.common.R;

import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.ColorInt;
import androidx.appcompat.content.res.AppCompatResources;

/**
 * TextView-Helper
 *
 * @author ZhongDaFeng
 */
public class RTextViewHelper extends RBaseHelper<TextView>
{

    //default value
    public static final int ICON_DIR_LEFT = 1, ICON_DIR_TOP = 2, ICON_DIR_RIGHT = 3, ICON_DIR_BOTTOM = 4;

    //icon
    private int mIconHeight;
    private int mIconWidth;
    private int mIconDirection;

    // Text
    protected int mTextColorNormal;
    protected int mTextColorPressed;
    protected int mTextColorUnable;
    protected int mTextColorSelected;
    protected ColorStateList mTextColorStateList;
    protected int[][] states = new int[5][];

    //Icon
    private Drawable mIcon = null;
    private Drawable mIconNormal;
    private Drawable mIconPressed;
    private Drawable mIconUnable;
    private Drawable mIconSelected;

    //typeface
    private String mTypefacePath;

    //drawable和Text居中
    private boolean mDrawableWithText = false;

    /**
     * 是否设置对应的属性
     */
    protected boolean mHasPressedTextColor = false;
    protected boolean mHasUnableTextColor = false;
    protected boolean mHasSelectedTextColor = false;

    //TextView本身设置的padding
    protected int mPaddingLeft, mPaddingRight, mPaddingTop, mPaddingBottom;


    public RTextViewHelper(Context context, TextView view, AttributeSet attrs)
    {
        super(context, view, attrs);
        initAttributeSet(context, attrs);
        //监听View改变
        addOnViewChangeListener();
    }

    /**
     * 设置View变化监听
     */
    private void addOnViewChangeListener()
    {
        if (mView == null)
        {
            return;
        }
        if (!mDrawableWithText)
        {
            return;
        }
        //大小变化
        mView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                mView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mPaddingLeft = mView.getPaddingLeft();
                mPaddingRight = mView.getPaddingRight();
                mPaddingTop = mView.getPaddingTop();
                mPaddingBottom = mView.getPaddingBottom();
                setIcon();
            }
        });
        //文本改变
        mView.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                setIcon();
            }
        });

    }

    /**
     * 初始化控件属性
     *
     * @param context
     * @param attrs
     */
    private void initAttributeSet(Context context, AttributeSet attrs)
    {
        if (context == null || attrs == null)
        {
            setup();
            return;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RTextView);
        //icon
        //Vector兼容处理
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            mIconNormal = a.getDrawable(R.styleable.RTextView_icon_src_normal);
            mIconPressed = a.getDrawable(R.styleable.RTextView_icon_src_pressed);
            mIconUnable = a.getDrawable(R.styleable.RTextView_icon_src_unable);
            mIconSelected = a.getDrawable(R.styleable.RTextView_icon_src_selected);
        } else
        {
            int normalId = a.getResourceId(R.styleable.RTextView_icon_src_normal, -1);
            int pressedId = a.getResourceId(R.styleable.RTextView_icon_src_pressed, -1);
            int unableId = a.getResourceId(R.styleable.RTextView_icon_src_unable, -1);
            int selectedId = a.getResourceId(R.styleable.RTextView_icon_src_selected, -1);

            if (normalId != -1)
            {
                mIconNormal = AppCompatResources.getDrawable(context, normalId);
            }
            if (pressedId != -1)
            {
                mIconPressed = AppCompatResources.getDrawable(context, pressedId);
            }
            if (unableId != -1)
            {
                mIconUnable = AppCompatResources.getDrawable(context, unableId);
            }
            if (selectedId != -1)
            {
                mIconSelected = AppCompatResources.getDrawable(context, selectedId);
            }
        }
        mIconWidth = a.getDimensionPixelSize(R.styleable.RTextView_icon_width, 0);
        mIconHeight = a.getDimensionPixelSize(R.styleable.RTextView_icon_height, 0);
        mIconDirection = a.getInt(R.styleable.RTextView_icon_direction, ICON_DIR_LEFT);
        //text
        mTextColorNormal = a.getColor(R.styleable.RTextView_text_color_normal, mView.getCurrentTextColor());
        mTextColorPressed = a.getColor(R.styleable.RTextView_text_color_pressed, 0);
        mTextColorUnable = a.getColor(R.styleable.RTextView_text_color_unable, 0);
        mTextColorSelected = a.getColor(R.styleable.RTextView_text_color_selected, 0);
        //typeface
        mTypefacePath = a.getString(R.styleable.RTextView_text_typeface);
        //drawableWithText
        mDrawableWithText = a.getBoolean(R.styleable.RTextView_icon_with_text, false);

        a.recycle();

        mHasPressedTextColor = mTextColorPressed != 0;
        mHasUnableTextColor = mTextColorUnable != 0;
        mHasSelectedTextColor = mTextColorSelected != 0;

        //setup
        setup();

    }

    /**
     * 设置
     */
    private void setup()
    {

        /**
         * icon
         */
        if (!mView.isEnabled())
        {
            mIcon = mIconUnable;
        } else if (mView.isSelected())
        {
            mIcon = mIconSelected;
        } else
        {
            mIcon = mIconNormal;
        }

        /**
         * 设置文字颜色默认值
         */
        if (!mHasPressedTextColor)
        {
            mTextColorPressed = mTextColorNormal;
        }
        if (!mHasUnableTextColor)
        {
            mTextColorUnable = mTextColorNormal;
        }
        if (!mHasSelectedTextColor)
        {
            mTextColorSelected = mTextColorNormal;
        }

        // unable
        states[0] = new int[]{-android.R.attr.state_enabled};
        // focused
        states[1] = new int[]{android.R.attr.state_focused};
        // pressed
        states[2] = new int[]{android.R.attr.state_pressed};
        // selected
        states[3] = new int[]{android.R.attr.state_selected};
        // normal
        states[4] = new int[]{android.R.attr.state_enabled};

        //设置文本颜色
        setTextColor();

        //设置ICON
        setIcon();

        //设置文本字体样式
        setTypeface();

    }

    /************************
     * Typeface
     ************************/

    public RTextViewHelper setTypeface(String typefacePath)
    {
        this.mTypefacePath = typefacePath;
        setTypeface();
        return this;
    }

    public String getTypefacePath()
    {
        return mTypefacePath;
    }

    private void setTypeface()
    {
        if (!TextUtils.isEmpty(mTypefacePath))
        {
            AssetManager assetManager = mContext.getAssets();
            Typeface typeface = Typeface.createFromAsset(assetManager, mTypefacePath);
            mView.setTypeface(typeface);
        }
    }

    /************************
     * Icon
     ************************/

    public RTextViewHelper setIconNormal(Drawable icon)
    {
        this.mIconNormal = icon;
        this.mIcon = icon;
        setIcon();
        return this;
    }

    public Drawable getIconNormal()
    {
        return mIconNormal;
    }

    public RTextViewHelper setIconPressed(Drawable icon)
    {
        this.mIconPressed = icon;
        this.mIcon = icon;
        setIcon();
        return this;
    }

    public Drawable getIconPressed()
    {
        return mIconPressed;
    }

    public RTextViewHelper setIconUnable(Drawable icon)
    {
        this.mIconUnable = icon;
        this.mIcon = icon;
        setIcon();
        return this;
    }

    public Drawable getIconUnable()
    {
        return mIconUnable;
    }

    public RTextViewHelper setIconSelected(Drawable icon)
    {
        this.mIconSelected = icon;
        this.mIcon = icon;
        setIcon();
        return this;
    }

    public Drawable getIconSelected()
    {
        return mIconSelected;
    }

    public RTextViewHelper setIconSize(int iconWidth, int iconHeight)
    {
        this.mIconWidth = iconWidth;
        this.mIconHeight = iconHeight;
        setIcon();
        return this;
    }

    public RTextViewHelper setIconWidth(int iconWidth)
    {
        this.mIconWidth = iconWidth;
        setIcon();
        return this;
    }

    public int getIconWidth()
    {
        return mIconWidth;
    }

    public RTextViewHelper setIconHeight(int iconHeight)
    {
        this.mIconHeight = iconHeight;
        setIcon();
        return this;
    }

    public int getIconHeight()
    {
        return mIconHeight;
    }

    public RTextViewHelper setIconDirection(int iconDirection)
    {
        this.mIconDirection = iconDirection;
        setIcon();
        return this;
    }

    public int getIconDirection()
    {
        return mIconDirection;
    }

    /**
     * 主要用于子类调用
     * 备注:用于库内确定逻辑的调用，不建议开发者直接调用
     */
    @SuppressWarnings("unchecked")
    protected void setIcon(Drawable icon)
    {
        this.mIcon = icon;
        setIcon();
    }

    private void setIcon()
    {
        //未设置图片大小
        if (mIconHeight == 0 && mIconWidth == 0)
        {
            if (mIcon != null)
            {
                mIconWidth = mIcon.getIntrinsicWidth();
                mIconHeight = mIcon.getIntrinsicHeight();
            }
        }
        setIcon(mIcon, mIconWidth, mIconHeight, mIconDirection);
    }

    private void setIcon(Drawable drawable, int drawableWidth, int drawableHeight, int direction)
    {
        if (drawable != null)
        {
            if (drawableWidth != 0 && drawableHeight != 0)
            {
                drawable.setBounds(0, 0, drawableWidth, drawableHeight);
            }
            //drawable间距
            int drawablePadding = mView.getCompoundDrawablePadding();
            //水平方向上drawable间距
            int drawablePaddingHorizontal = drawablePadding;
            //垂直方向上drawable间距
            int drawablePaddingVertical = drawablePadding;
            switch (direction)
            {
                case ICON_DIR_LEFT:
                    drawableHeight = 0;
                    drawablePaddingVertical = 0;
                    mView.setCompoundDrawables(drawable, null, null, null);
                    break;
                case ICON_DIR_TOP:
                    drawableWidth = 0;
                    drawablePaddingHorizontal = 0;
                    mView.setCompoundDrawables(null, drawable, null, null);
                    break;
                case ICON_DIR_RIGHT:
                    drawableHeight = 0;
                    drawablePaddingVertical = 0;
                    mView.setCompoundDrawables(null, null, drawable, null);
                    break;
                case ICON_DIR_BOTTOM:
                    drawableWidth = 0;
                    drawablePaddingHorizontal = 0;
                    mView.setCompoundDrawables(null, null, null, drawable);
                    break;
            }

            if (!mDrawableWithText)
            {
                return;
            }
            if (mView.getWidth() == 0 || mView.getHeight() == 0)
            {
                return;
            }

            final int drawableWidthFinal = drawableWidth;
            final int drawableHeightFinal = drawableHeight;
            final int drawablePaddingVerticalFinal = drawablePaddingVertical;
            final int drawablePaddingHorizontalFinal = drawablePaddingHorizontal;
            //view.getLineCount() need post
            mView.post(new Runnable()
            {
                @Override
                public void run()
                {
                    //水平方向计算
                    float textWidth = getTextWidth(mView, drawableWidthFinal,
                            mPaddingLeft, mPaddingRight, drawablePaddingHorizontalFinal);
                    float bodyWidth = textWidth + drawableWidthFinal + drawablePaddingHorizontalFinal;//内容宽度
                    float actualWidth = mView.getWidth() - (mPaddingLeft + mPaddingRight);//实际可用宽度
                    int translateX = (int) (actualWidth - bodyWidth) / 2;//两边使用
                    if (translateX < 0)
                    {
                        translateX = 0;
                    }

                    //垂直方向计算
                    float textHeight = getTextHeight(mView, drawableHeightFinal, mPaddingTop,
                            mPaddingBottom, drawablePaddingVerticalFinal);
                    float bodyHeight = textHeight + drawableHeightFinal + drawablePaddingVerticalFinal;//内容高度
                    float actualHeight = mView.getHeight() - (mPaddingTop + mPaddingBottom);//实际可用高度
                    int translateY = (int) (actualHeight - bodyHeight) / 2;
                    if (translateY < 0)
                    {
                        translateY = 0;
                    }

                    //关键技术点
                    mView.setPadding(translateX + mPaddingLeft, translateY + mPaddingTop,
                            translateX + mPaddingRight, translateY + mPaddingBottom);
                }
            });
        }
    }

    /************************
     * text color
     ************************/

    public RTextViewHelper setTextColorNormal(@ColorInt int textColor)
    {
        this.mTextColorNormal = textColor;
        if (!mHasPressedTextColor)
        {
            mTextColorPressed = mTextColorNormal;
        }
        if (!mHasUnableTextColor)
        {
            mTextColorUnable = mTextColorNormal;
        }
        if (!mHasSelectedTextColor)
        {
            mTextColorSelected = mTextColorNormal;
        }
        setTextColor();
        return this;
    }

    public int getTextColorNormal()
    {
        return mTextColorNormal;
    }

    public RTextViewHelper setTextColorPressed(@ColorInt int textColor)
    {
        this.mTextColorPressed = textColor;
        this.mHasPressedTextColor = true;
        setTextColor();
        return this;
    }

    public int getTextColorPressed()
    {
        return mTextColorPressed;
    }

    public RTextViewHelper setTextColorUnable(@ColorInt int textColor)
    {
        this.mTextColorUnable = textColor;
        this.mHasUnableTextColor = true;
        setTextColor();
        return this;
    }

    public int getTextColorUnable()
    {
        return mTextColorUnable;
    }


    public RTextViewHelper setTextColorSelected(@ColorInt int textColor)
    {
        this.mTextColorSelected = textColor;
        this.mHasSelectedTextColor = true;
        setTextColor();
        return this;
    }

    public int getTextColorSelected()
    {
        return mTextColorSelected;
    }

    public RTextViewHelper setTextColor(@ColorInt int normal, @ColorInt int pressed, @ColorInt int unable, @ColorInt int selected)
    {
        this.mTextColorNormal = normal;
        this.mTextColorPressed = pressed;
        this.mTextColorUnable = unable;
        this.mTextColorSelected = selected;
        this.mHasPressedTextColor = true;
        this.mHasUnableTextColor = true;
        this.mHasSelectedTextColor = true;
        setTextColor();
        return this;
    }

    protected void setTextColor()
    {
        //unable,focused,pressed,selected,normal
        int[] colors = new int[]{mTextColorUnable, mTextColorPressed, mTextColorPressed, mTextColorSelected, mTextColorNormal};
        mTextColorStateList = new ColorStateList(states, colors);
        mView.setTextColor(mTextColorStateList);
    }

    /**
     * 设置是否启用
     * 备注:用于库内确定逻辑的调用，不建议开发者直接调用
     */
    @SuppressWarnings("unchecked")
    public void setEnabled(boolean enabled)
    {
        if (enabled)
        {
            if (mIconNormal != null)
            {
                mIcon = mIconNormal;
                setIcon();
            }
        } else
        {
            if (mIconUnable != null)
            {
                mIcon = mIconUnable;
                setIcon();
            }
        }
    }

    /**
     * 设置是否选中
     * 备注:用于库内确定逻辑的调用，不建议开发者直接调用
     */
    @SuppressWarnings("unchecked")
    public void setSelected(boolean selected)
    {
        if (!mView.isEnabled())
        {
            return;
        }
        if (selected)
        {
            if (mIconSelected != null)
            {
                mIcon = mIconSelected;
                setIcon();
            }
        } else
        {
            if (mIconNormal != null)
            {
                mIcon = mIconNormal;
                setIcon();
            }
        }
    }

    /**
     * 触摸事件逻辑
     * 备注:用于库内确定逻辑的调用，不建议开发者直接调用
     */
    @SuppressWarnings("unchecked")
    public void onTouchEvent(MotionEvent event)
    {
        if (!mView.isEnabled())
        {
            return;
        }
        int action = event.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_UP:
                if (mIconNormal != null)
                {
                    mIcon = mView.isSelected() ? mIconSelected : mIconNormal;
                    setIcon();
                }
                break;
            case MotionEvent.ACTION_DOWN:
                if (mIconPressed != null)
                {
                    mIcon = mIconPressed;
                    setIcon();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();
                if (isOutsideView(x, y))
                {
                    if (mIconNormal != null)
                    {
                        mIcon = mView.isSelected() ? mIconSelected : mIconNormal;
                        setIcon();
                    }
                }
                break;
            //父级控件获取控制权
            case MotionEvent.ACTION_CANCEL:
                if (mIconNormal != null)
                {
                    mIcon = mView.isSelected() ? mIconSelected : mIconNormal;
                    setIcon();
                }
                break;
        }
    }

    /**
     * 获取Text实际宽度
     * 备注:单行最大宽度
     *
     * @param view                      view
     * @param drawableWidth             drawable宽度
     * @param paddingLeft               内边距-左
     * @param paddingRight              内边距-右
     * @param drawablePaddingHorizontal 水平方向上drawable间距
     * @return
     */
    private float getTextWidth(TextView view, int drawableWidth, int paddingLeft, int paddingRight, int drawablePaddingHorizontal)
    {
        if (view == null)
        {
            return 0;
        }

        float textWidth;
        /**
         * 1.是否存在\n换行（获取宽度最大的值）
         * 2.不存在\n获取单行宽度值（判断是否自动换行临界值）
         */
        String originalText = view.getText().toString();
        if (originalText.contains("\n"))
        {
            String[] originalTextArray;
            ArrayList<Float> widthList;
            originalTextArray = originalText.split("\n");
            int arrayLen = originalTextArray.length;
            widthList = new ArrayList<>(arrayLen);
            for (int i = 0; i < arrayLen; i++)
            {
                widthList.add(view.getPaint().measureText(originalTextArray[i]));
            }
            textWidth = Collections.max(widthList);
        } else
        {
            textWidth = view.getPaint().measureText(originalText);
        }

        //计算自动换行临界值，不允许超过换行临界值
        int maxWidth = view.getWidth() - drawableWidth - paddingLeft - paddingRight - drawablePaddingHorizontal;
        if (textWidth > maxWidth)
        {
            textWidth = maxWidth;
        }
        return textWidth;
    }

    /**
     * 获取Text实际高度
     * 备注:多行最大高度
     *
     * @param view                    view
     * @param drawableHeight          drawable高度
     * @param paddingTop              内边距-左
     * @param paddingBottom           内边距-右
     * @param drawablePaddingVertical 垂直方向上drawable间距
     * @return
     */
    private float getTextHeight(TextView view, int drawableHeight, int paddingTop, int paddingBottom, int drawablePaddingVertical)
    {
        if (view == null)
        {
            return 0;
        }

        /**
         * 1.单行高度*行数
         * 2.最大高度临界值
         */
        Paint.FontMetrics fontMetrics = view.getPaint().getFontMetrics();
        //单行高度
        float singleLineHeight = Math.abs((fontMetrics.bottom - fontMetrics.top));
        float textHeight = singleLineHeight * view.getLineCount();

        //最大高度临界值，不允许超过最大高度临界值
        //最大允许的宽度
        int maxHeight = view.getHeight() - drawableHeight - paddingTop - paddingBottom - drawablePaddingVertical;
        if (textHeight > maxHeight)
        {
            textHeight = maxHeight;
        }
        return textHeight;
    }

}
