package com.lwkandroid.wings.widget;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.lwkandroid.wings.R;

/**
 * 圆角ImageView，支持四个角不一样
 * 修改自：https://github.com/pungrue26/SelectableRoundedImageView
 * 【注意：如果配合Glide使用，不能调用.crossFade()！！！不能有显示动画！！！】
 */
@SuppressLint("AppCompatCustomView")
public class RoundDiffImageView extends ImageView
{
    public static final String TAG = "RoundImageView";
    private int mResource = 0;
    private static final ScaleType[] sScaleTypeArray = {
            ScaleType.MATRIX,
            ScaleType.FIT_XY,
            ScaleType.FIT_START,
            ScaleType.FIT_CENTER,
            ScaleType.FIT_END,
            ScaleType.CENTER,
            ScaleType.CENTER_CROP,
            ScaleType.CENTER_INSIDE
    };
    //默认缩放方式为FIT_CENTER，和原始控件一致
    private ScaleType mScaleType = ScaleType.FIT_CENTER;
    //公共radius
    private float mRadius = 0.0f;
    //左上角radius
    private float mLeftTopRadius = 0.0f;
    //右上角radius
    private float mRightTopRadius = 0.0f;
    //左下角radius
    private float mLeftBottomRadius = 0.0f;
    //右下角radius
    private float mRightBottomRadius = 0.0f;
    //边框宽度
    private float mBorderWidth = 0.0f;
    //边框默认颜色
    private static final int DEFAULT_BORDER_COLOR = Color.BLACK;
    //边框颜色StateList
    private ColorStateList mBorderColor = ColorStateList.valueOf(DEFAULT_BORDER_COLOR);
    //是否为椭圆
    private boolean isOval = false;
    //显示的Drawable
    private Drawable mDrawable;
    //各个角radius值
    private float[] mRadiusArray = new float[]{0, 0, 0, 0, 0, 0, 0, 0};
    private boolean mPressFeedBack = false;//按压反馈

    public static final int SCALE_TARGET_HEIGHT = 0;// 对高进行缩放
    public static final int SCALE_TARGET_WIDTH = 1;// 对宽进行缩放
    public static final int SCALE_TARGET_EXPAND = 2;// 扩大方式（宽不足拉伸宽，高不足拉伸高）
    public static final int SCALE_TARGET_INSIDE = 3;// 缩小方式（缩小到一条边刚好与原尺寸一样，另一条小于原尺寸）
    private int mWidthScale = 0;// 宽度缩放比
    private int mHeightScale = 0;// 高度缩放比
    private int mScaleTarget = SCALE_TARGET_INSIDE;// 缩放目标

    public RoundDiffImageView(Context context)
    {
        super(context);
    }

    public RoundDiffImageView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public RoundDiffImageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RoundDiffImageView, defStyle, 0);

        int widthScale = mWidthScale, heightScale = mHeightScale, scaleTarget = mScaleTarget;

        if (a != null)
        {
            final int scaleTypeIndex = a.getInt(R.styleable.RoundDiffImageView_android_scaleType, -1);
            if (scaleTypeIndex >= 0)
                setScaleType(sScaleTypeArray[scaleTypeIndex]);

            mRadius = a.getDimensionPixelSize(R.styleable.RoundDiffImageView_riv_radius, 0);
            mLeftTopRadius = a.getDimensionPixelSize(R.styleable.RoundDiffImageView_riv_left_top_radius, 0);
            mRightTopRadius = a.getDimensionPixelSize(R.styleable.RoundDiffImageView_riv_right_top_radius, 0);
            mLeftBottomRadius = a.getDimensionPixelSize(R.styleable.RoundDiffImageView_riv_left_bottom_radius, 0);
            mRightBottomRadius = a.getDimensionPixelSize(R.styleable.RoundDiffImageView_riv_right_bottom_radius, 0);
            widthScale = a.getInteger(R.styleable.RoundDiffImageView_riv_width_scale, 0);
            heightScale = a.getInteger(R.styleable.RoundDiffImageView_riv_height_scale, 0);
            scaleTarget = a.getInt(R.styleable.RoundDiffImageView_riv_scale_target, SCALE_TARGET_INSIDE);
            mPressFeedBack = a.getBoolean(R.styleable.ShapeImageView_siv_press_feedback, false);

            if (mRadius < 0.0f)
                mRadius = 0.0f;
            if (mLeftTopRadius <= 0.0f)
                mLeftTopRadius = mRadius == 0.0f ? 0.0f : mRadius;
            if (mRightTopRadius <= 0.0f)
                mRightTopRadius = mRadius == 0.0f ? 0.0f : mRadius;
            if (mLeftBottomRadius <= 0.0f)
                mLeftBottomRadius = mRadius == 0.0f ? 0.0f : mRadius;
            if (mRightBottomRadius <= 0.0f)
                mRightBottomRadius = mRadius == 0.0f ? 0.0f : mRadius;

            mRadiusArray = new float[]{
                    mLeftTopRadius, mLeftTopRadius,
                    mRightTopRadius, mRightTopRadius,
                    mRightBottomRadius, mRightBottomRadius,
                    mLeftBottomRadius, mLeftBottomRadius};

            mBorderWidth = a.getDimensionPixelSize(
                    R.styleable.RoundDiffImageView_riv_border_width, 0);
            if (mBorderWidth < 00.f)
                mBorderWidth = 0.0f;

            mBorderColor = a.getColorStateList(R.styleable.RoundDiffImageView_riv_border_color);
            if (mBorderColor == null)
                mBorderColor = ColorStateList.valueOf(DEFAULT_BORDER_COLOR);

            isOval = a.getBoolean(R.styleable.RoundDiffImageView_riv_is_oval, false);
            a.recycle();
        }

        if (mPressFeedBack)
            setClickable(true);
        setFixedSize(widthScale, heightScale);
        setScaleTarget(scaleTarget);
        updateDrawable();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        if (mWidthScale <= 0 || mHeightScale <= 0)
        {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int measureWidth = getMeasuredWidth();
        final int measureHeight = getMeasuredHeight();
        switch (mScaleTarget)
        {
            case SCALE_TARGET_HEIGHT:
                setMeasuredDimension(measureWidth, measureWidth * mHeightScale / mWidthScale);
                break;
            case SCALE_TARGET_WIDTH:
                setMeasuredDimension(measureHeight * mWidthScale / mHeightScale, measureHeight);
                break;
            case SCALE_TARGET_EXPAND:
                if (measureWidth * mHeightScale < measureHeight * mWidthScale)
                {
                    // 宽不足
                    setMeasuredDimension(measureHeight * mWidthScale / mHeightScale, measureHeight);
                } else
                {
                    // 高不足
                    setMeasuredDimension(measureWidth, measureWidth * mHeightScale / mWidthScale);
                }
                break;
            default:
            case SCALE_TARGET_INSIDE:
                if (measureWidth * mHeightScale > measureHeight * mWidthScale)
                {
                    setMeasuredDimension(measureHeight * mWidthScale / mHeightScale, measureHeight);
                } else
                {
                    setMeasuredDimension(measureWidth, measureWidth * mHeightScale / mWidthScale);
                }
                break;
        }
    }

    @Override
    protected void drawableStateChanged()
    {
        super.drawableStateChanged();
        invalidate();
    }

    @Override
    public ScaleType getScaleType()
    {
        return mScaleType;
    }

    @Override
    public void setScaleType(ScaleType scaleType)
    {
        super.setScaleType(scaleType);
        mScaleType = scaleType;
        updateDrawable();
    }

    @Override
    public void setImageDrawable(Drawable drawable)
    {
        mResource = 0;
        mDrawable = RoundedDrawable.fromDrawable(drawable, getResources());
        super.setImageDrawable(mDrawable);
        updateDrawable();
    }

    @Override
    public void setImageBitmap(Bitmap bm)
    {
        mResource = 0;
        mDrawable = RoundedDrawable.fromBitmap(bm, getResources());
        super.setImageDrawable(mDrawable);
        updateDrawable();
    }

    @Override
    public void setImageResource(int resId)
    {
        if (mResource != resId)
        {
            mResource = resId;
            mDrawable = resolveResource();
            super.setImageDrawable(mDrawable);
            updateDrawable();
        }
    }

    @Override
    public void setImageURI(Uri uri)
    {
        super.setImageURI(uri);
        setImageDrawable(getDrawable());
    }

    private Drawable resolveResource()
    {
        Resources rsrc = getResources();
        if (rsrc == null)
            return null;

        Drawable d = null;

        if (mResource != 0)
        {
            try
            {
                d = rsrc.getDrawable(mResource);
            } catch (NotFoundException e)
            {
                Log.w(TAG, "RoundImageView Unable to find resource: " + mResource, e);
                // Don't try again.
                mResource = 0;
            }
        }
        return RoundedDrawable.fromDrawable(d, getResources());
    }

    private void updateDrawable()
    {
        if (mDrawable == null)
            return;

        ((RoundedDrawable) mDrawable).setScaleType(mScaleType);
        ((RoundedDrawable) mDrawable).setCornerRadiusArray(mRadiusArray);
        ((RoundedDrawable) mDrawable).setBorderWidth(mBorderWidth);
        ((RoundedDrawable) mDrawable).setBorderColor(mBorderColor);
        ((RoundedDrawable) mDrawable).setOval(isOval);
    }

    /**
     * 获取四个角radius公共值，单位px
     */
    public float getRadius()
    {
        return mRadius;
    }

    /**
     * 获取左上角radius，单位px
     */
    public float getLeftTopRadius()
    {
        return mLeftTopRadius;
    }

    /**
     * 获取右上角radius，单位px
     */
    public float getRightTopRadius()
    {
        return mRightTopRadius;
    }

    /**
     * 获取左下角radius，单位px
     */
    public float getLeftBottomRadius()
    {
        return mLeftBottomRadius;
    }

    /**
     * 获取右下角radius，单位px
     */
    public float getRightBottomRadius()
    {
        return mRightBottomRadius;
    }

    /**
     * 设置各个角的radius，单位为dp
     */
    public void setRadius(float radius)
    {
        mRadius = radius * getResources().getDisplayMetrics().density;
        setRadius(radius, radius, radius, radius);
    }

    /**
     * 设置各个角的radius，单位为dp
     *
     * @param leftTop     左上角radius
     * @param rightTop    右上角radius
     * @param leftBottom  左下角radius
     * @param rightBottom 右下角radius
     */
    public void setRadius(float leftTop, float rightTop, float leftBottom, float rightBottom)
    {
        final float density = getResources().getDisplayMetrics().density;

        mLeftTopRadius = leftTop * density;
        mRightTopRadius = rightTop * density;
        mLeftBottomRadius = leftBottom * density;
        mRightBottomRadius = rightBottom * density;

        mRadiusArray = new float[]{
                mLeftTopRadius, mLeftTopRadius,
                mRightTopRadius, mRightTopRadius,
                mRightBottomRadius, mRightBottomRadius,
                mLeftBottomRadius, mLeftBottomRadius};
        updateDrawable();
    }

    /**
     * 获取边框宽度，单位px
     */
    public float getBorderWidth()
    {
        return mBorderWidth;
    }

    /**
     * 设置边框宽度，单位dp
     */
    public void setBorderWidth(float width)
    {
        float scaledWidth = getResources().getDisplayMetrics().density * width;
        if (mBorderWidth == scaledWidth)
            return;

        mBorderWidth = scaledWidth;
        updateDrawable();
        invalidate();
    }

    /**
     * 获取边框颜色
     */
    public int getBorderColor()
    {
        return mBorderColor.getDefaultColor();
    }

    /**
     * 设置边框颜色
     */
    public void setBorderColor(int color)
    {
        setBorderColor(ColorStateList.valueOf(color));
    }

    /**
     * 获取边框颜色ColorStateList
     */
    public ColorStateList getBorderColors()
    {
        return mBorderColor;
    }

    /**
     * 设置边框颜色ColorStateList
     */
    public void setBorderColor(ColorStateList colors)
    {
        if (mBorderColor.equals(colors))
        {
            return;
        }

        mBorderColor = (colors != null) ? colors : ColorStateList
                .valueOf(DEFAULT_BORDER_COLOR);
        updateDrawable();
        if (mBorderWidth > 0)
        {
            invalidate();
        }
    }

    /**
     * 是否为椭圆
     */
    public boolean isOval()
    {
        return isOval;
    }

    /**
     * 设置是否为椭圆
     */
    public void setOval(boolean oval)
    {
        isOval = oval;
        updateDrawable();
        invalidate();
    }

    /**
     * 获取缩放目标
     *
     * @return 缩放目标
     */
    @SuppressWarnings("unused")
    public int getScaleTarget()
    {
        return mScaleTarget;
    }

    /**
     * 设置缩放目标
     *
     * @param target 缩放目标
     */
    public void setScaleTarget(int target)
    {
        if (target != SCALE_TARGET_INSIDE && target != SCALE_TARGET_HEIGHT
                && target != SCALE_TARGET_WIDTH && target != SCALE_TARGET_EXPAND)
            return;
        if (mScaleTarget != target)
        {
            mScaleTarget = target;
            requestLayout();
            invalidate();
        }
    }

    /**
     * 设置缩放比（任意值小于等于0则关闭该功能）
     *
     * @param widthScale  宽度缩放比
     * @param heightScale 高度缩放比
     */
    public void setFixedSize(int widthScale, int heightScale)
    {
        if (mWidthScale != widthScale || mHeightScale != heightScale)
        {
            mWidthScale = widthScale;
            mHeightScale = heightScale;
            requestLayout();
            invalidate();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN)
        {
            if (mPressFeedBack)
                setAlpha(0.8f);
        } else if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_CANCEL)
        {
            if (mPressFeedBack)
                setAlpha(1.0f);
        }
        return super.dispatchTouchEvent(event);
    }

    private static class RoundedDrawable extends Drawable
    {
        private static final String TAG = "RoundedDrawable";
        private static final int DEFAULT_BORDER_COLOR = Color.BLACK;

        private RectF mBounds = new RectF();
        private RectF mBorderBounds = new RectF();

        private final RectF mBitmapRect = new RectF();
        private final int mBitmapWidth;
        private final int mBitmapHeight;

        private final Paint mBitmapPaint;
        private final Paint mBorderPaint;

        private BitmapShader mBitmapShader;

        private float[] mRadiusArray = new float[]{0, 0, 0, 0, 0, 0, 0, 0};
        private float[] mBorderRadiusArray = new float[]{0, 0, 0, 0, 0, 0, 0, 0};

        private boolean mOval = false;

        private float mBorderWidth = 0;
        private ColorStateList mBorderColor = ColorStateList.valueOf(DEFAULT_BORDER_COLOR);
        private ScaleType mScaleType = ScaleType.FIT_CENTER;

        private Path mPath = new Path();
        private Bitmap mBitmap;
        private boolean mBoundsConfigured = false;

        public RoundedDrawable(Bitmap bitmap, Resources r)
        {
            mBitmap = bitmap;
            mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

            if (bitmap != null)
            {
                mBitmapWidth = bitmap.getScaledWidth(r.getDisplayMetrics());
                mBitmapHeight = bitmap.getScaledHeight(r.getDisplayMetrics());
            } else
            {
                mBitmapWidth = mBitmapHeight = -1;
            }

            mBitmapRect.set(0, 0, mBitmapWidth, mBitmapHeight);

            mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mBitmapPaint.setStyle(Paint.Style.FILL);
            mBitmapPaint.setShader(mBitmapShader);

            mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mBorderPaint.setStyle(Paint.Style.STROKE);
            mBorderPaint.setColor(mBorderColor.getColorForState(getState(), DEFAULT_BORDER_COLOR));
            mBorderPaint.setStrokeWidth(mBorderWidth);
        }

        public static RoundedDrawable fromBitmap(Bitmap bitmap, Resources r)
        {
            if (bitmap != null)
                return new RoundedDrawable(bitmap, r);
            else
                return null;
        }

        public static Drawable fromDrawable(Drawable drawable, Resources r)
        {
            if (drawable != null)
            {
                if (drawable instanceof RoundedDrawable)
                {
                    return drawable;
                } else if (drawable instanceof LayerDrawable)
                {
                    LayerDrawable ld = (LayerDrawable) drawable;
                    final int num = ld.getNumberOfLayers();
                    for (int i = 0; i < num; i++)
                    {
                        Drawable d = ld.getDrawable(i);
                        ld.setDrawableByLayerId(ld.getId(i), fromDrawable(d, r));
                    }
                    return ld;
                }

                Bitmap bm = drawableToBitmap(drawable);
                if (bm != null)
                {
                    return new RoundedDrawable(bm, r);
                } else
                {
                    Log.w(TAG, "RoundedDrawable failed to create bitmap from drawable!");
                }
            }
            return drawable;
        }

        public static Bitmap drawableToBitmap(Drawable drawable)
        {
            if (drawable == null)
            {
                return null;
            }

            if (drawable instanceof BitmapDrawable)
            {
                return ((BitmapDrawable) drawable).getBitmap();
            }

            Bitmap bitmap;
            int width = Math.max(drawable.getIntrinsicWidth(), 2);
            int height = Math.max(drawable.getIntrinsicHeight(), 2);
            try
            {
                bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
            } catch (IllegalArgumentException e)
            {
                e.printStackTrace();
                bitmap = null;
            }
            return bitmap;
        }

        @Override
        public boolean isStateful()
        {
            return mBorderColor.isStateful();
        }

        @Override
        protected boolean onStateChange(int[] state)
        {
            int newColor = mBorderColor.getColorForState(state, 0);
            if (mBorderPaint.getColor() != newColor)
            {
                mBorderPaint.setColor(newColor);
                return true;
            } else
            {
                return super.onStateChange(state);
            }
        }

        private void configureBounds(Canvas canvas)
        {
            // I have discovered a truly marvelous explanation of this,
            // which this comment space is too narrow to contain. :)
            // If you want to understand what's going on here,
            // See http://www.joooooooooonhokim.com/?p=289
            Rect clipBounds = canvas.getClipBounds();
            Matrix canvasMatrix = canvas.getMatrix();

            if (ScaleType.CENTER == mScaleType)
            {
                mBounds.set(clipBounds);
            } else if (ScaleType.CENTER_CROP == mScaleType)
            {
                applyScaleToRadii(canvasMatrix);
                mBounds.set(clipBounds);
            } else if (ScaleType.FIT_XY == mScaleType)
            {
                Matrix m = new Matrix();
                m.setRectToRect(mBitmapRect, new RectF(clipBounds), Matrix.ScaleToFit.FILL);
                mBitmapShader.setLocalMatrix(m);
                mBounds.set(clipBounds);
            } else if (ScaleType.FIT_START == mScaleType || ScaleType.FIT_END == mScaleType
                    || ScaleType.FIT_CENTER == mScaleType || ScaleType.CENTER_INSIDE == mScaleType)
            {
                applyScaleToRadii(canvasMatrix);
                mBounds.set(mBitmapRect);
            } else if (ScaleType.MATRIX == mScaleType)
            {
                applyScaleToRadii(canvasMatrix);
                mBounds.set(mBitmapRect);
            }
        }

        private void applyScaleToRadii(Matrix m)
        {
            float[] values = new float[9];
            m.getValues(values);
            for (int i = 0; i < mRadiusArray.length; i++)
            {
                mRadiusArray[i] = mRadiusArray[i] / values[0];
            }
        }

        private void adjustCanvasForBorder(Canvas canvas)
        {
            Matrix canvasMatrix = canvas.getMatrix();
            final float[] values = new float[9];
            canvasMatrix.getValues(values);

            final float scaleFactorX = values[0];
            final float scaleFactorY = values[4];
            final float translateX = values[2];
            final float translateY = values[5];

            final float newScaleX = mBounds.width()
                    / (mBounds.width() + mBorderWidth + mBorderWidth);
            final float newScaleY = mBounds.height()
                    / (mBounds.height() + mBorderWidth + mBorderWidth);

            canvas.scale(newScaleX, newScaleY);
            if (ScaleType.FIT_START == mScaleType || ScaleType.FIT_END == mScaleType
                    || ScaleType.FIT_XY == mScaleType || ScaleType.FIT_CENTER == mScaleType
                    || ScaleType.CENTER_INSIDE == mScaleType || ScaleType.MATRIX == mScaleType)
            {
                canvas.translate(mBorderWidth, mBorderWidth);
            } else if (ScaleType.CENTER == mScaleType || ScaleType.CENTER_CROP == mScaleType)
            {
                // First, make translate values to 0
                canvas.translate(
                        -translateX / (newScaleX * scaleFactorX),
                        -translateY / (newScaleY * scaleFactorY));
                // Then, set the final translate values.
                canvas.translate(-(mBounds.left - mBorderWidth), -(mBounds.top - mBorderWidth));
            }
        }

        private void adjustBorderWidthAndBorderBounds(Canvas canvas)
        {
            Matrix canvasMatrix = canvas.getMatrix();
            final float[] values = new float[9];
            canvasMatrix.getValues(values);

            final float scaleFactor = values[0];

            float viewWidth = mBounds.width() * scaleFactor;
            mBorderWidth = (mBorderWidth * mBounds.width()) / (viewWidth - (2 * mBorderWidth));
            mBorderPaint.setStrokeWidth(mBorderWidth);

            mBorderBounds.set(mBounds);
            mBorderBounds.inset(-mBorderWidth / 2, -mBorderWidth / 2);
        }

        private void setBorderRadii()
        {
            for (int i = 0; i < mRadiusArray.length; i++)
            {
                if (mRadiusArray[i] > 0)
                {
                    mBorderRadiusArray[i] = mRadiusArray[i];
                    mRadiusArray[i] = mRadiusArray[i] - mBorderWidth;
                }
            }
        }

        @Override
        public void draw(Canvas canvas)
        {
            canvas.save();
            if (!mBoundsConfigured)
            {
                configureBounds(canvas);
                if (mBorderWidth > 0)
                {
                    adjustBorderWidthAndBorderBounds(canvas);
                    setBorderRadii();
                }
                mBoundsConfigured = true;
            }

            if (mOval)
            {
                if (mBorderWidth > 0)
                {
                    adjustCanvasForBorder(canvas);
                    mPath.addOval(mBounds, Path.Direction.CW);
                    canvas.drawPath(mPath, mBitmapPaint);
                    mPath.reset();
                    mPath.addOval(mBorderBounds, Path.Direction.CW);
                    canvas.drawPath(mPath, mBorderPaint);
                } else
                {
                    mPath.addOval(mBounds, Path.Direction.CW);
                    canvas.drawPath(mPath, mBitmapPaint);
                }
            } else
            {
                if (mBorderWidth > 0)
                {
                    adjustCanvasForBorder(canvas);
                    mPath.addRoundRect(mBounds, mRadiusArray, Path.Direction.CW);
                    canvas.drawPath(mPath, mBitmapPaint);
                    mPath.reset();
                    mPath.addRoundRect(mBorderBounds, mBorderRadiusArray, Path.Direction.CW);
                    canvas.drawPath(mPath, mBorderPaint);
                } else
                {
                    mPath.addRoundRect(mBounds, mRadiusArray, Path.Direction.CW);
                    canvas.drawPath(mPath, mBitmapPaint);
                }
            }
            canvas.restore();
        }

        public void setCornerRadiusArray(float[] radius)
        {
            if (radius == null)
                return;

            if (radius.length != 8)
            {
                throw new ArrayIndexOutOfBoundsException("RoundImageView's radius array needs 8 values");
            }

            for (int i = 0; i < radius.length; i++)
            {
                mRadiusArray[i] = radius[i];
            }
        }

        @Override
        public int getOpacity()
        {
            return (mBitmap == null || mBitmap.hasAlpha() || mBitmapPaint.getAlpha() < 255) ? PixelFormat.TRANSLUCENT
                    : PixelFormat.OPAQUE;
        }

        @Override
        public void setAlpha(int alpha)
        {
            mBitmapPaint.setAlpha(alpha);
            invalidateSelf();
        }

        @Override
        public void setColorFilter(ColorFilter cf)
        {
            mBitmapPaint.setColorFilter(cf);
            invalidateSelf();
        }

        @Override
        public void setDither(boolean dither)
        {
            mBitmapPaint.setDither(dither);
            invalidateSelf();
        }

        @Override
        public void setFilterBitmap(boolean filter)
        {
            mBitmapPaint.setFilterBitmap(filter);
            invalidateSelf();
        }

        @Override
        public int getIntrinsicWidth()
        {
            return mBitmapWidth;
        }

        @Override
        public int getIntrinsicHeight()
        {
            return mBitmapHeight;
        }

        public float getBorderWidth()
        {
            return mBorderWidth;
        }

        public void setBorderWidth(float width)
        {
            mBorderWidth = width;
            mBorderPaint.setStrokeWidth(width);
        }

        public int getBorderColor()
        {
            return mBorderColor.getDefaultColor();
        }

        public void setBorderColor(int color)
        {
            setBorderColor(ColorStateList.valueOf(color));
        }

        public ColorStateList getBorderColors()
        {
            return mBorderColor;
        }

        /**
         * Controls border color of this ImageView.
         *
         * @param colors The desired border color. If it's null, no border will be
         *               drawn.
         */
        public void setBorderColor(ColorStateList colors)
        {
            if (colors == null)
            {
                mBorderWidth = 0;
                mBorderColor = ColorStateList.valueOf(Color.TRANSPARENT);
                mBorderPaint.setColor(Color.TRANSPARENT);
            } else
            {
                mBorderColor = colors;
                mBorderPaint.setColor(mBorderColor.getColorForState(getState(),
                        DEFAULT_BORDER_COLOR));
            }
        }

        public boolean isOval()
        {
            return mOval;
        }

        public void setOval(boolean oval)
        {
            mOval = oval;
        }

        public ScaleType getScaleType()
        {
            return mScaleType;
        }

        public void setScaleType(ScaleType scaleType)
        {
            if (scaleType == null)
            {
                return;
            }
            mScaleType = scaleType;
        }
    }

}