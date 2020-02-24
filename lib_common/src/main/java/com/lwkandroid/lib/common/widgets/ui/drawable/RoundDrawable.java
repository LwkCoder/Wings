package com.lwkandroid.lib.common.widgets.ui.drawable;

import android.graphics.Bitmap;
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
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Round Drawable
 */
public final class RoundDrawable extends Drawable
{

    private Bitmap mBitmap;
    private final int mBitmapWidth;
    private final int mBitmapHeight;
    private final RectF mBitmapRect = new RectF();

    private final Paint mBitmapPaint;
    private final Paint mBorderPaint;

    private final RectF mBorderRect = new RectF();
    private final RectF mDrawableRect = new RectF();

    private Path mPath = new Path();
    private RectF mRectF = new RectF();
    private final RectF mBounds = new RectF();
    private final RectF mBoundsFinal = new RectF();

    private boolean mRebuildShader = true;
    private final Matrix mShaderMatrix = new Matrix();
    private ImageView.ScaleType mScaleType = ImageView.ScaleType.FIT_CENTER;

    //圆角
    private float mCorner = -1;
    private float mCornerTopLeft = 0;
    private float mCornerTopRight = 0;
    private float mCornerBottomLeft = 0;
    private float mCornerBottomRight = 0;
    private float mCornerRadii[] = new float[8];
    //边框
    private float mBorderWidth = 0;
    private int mBorderColor = Color.BLACK;
    //是否圆形
    private boolean mCircle = true;

    public RoundDrawable(Bitmap bitmap)
    {
        mBitmap = bitmap;
        mBitmapWidth = bitmap.getWidth();
        mBitmapHeight = bitmap.getHeight();
        mBitmapRect.set(0, 0, mBitmapWidth, mBitmapHeight);

        mBitmapPaint = new Paint();
        mBitmapPaint.setStyle(Paint.Style.FILL);
        mBitmapPaint.setAntiAlias(true);

        mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        updateBorder();
    }

    @Override
    protected void onBoundsChange(Rect bounds)
    {
        super.onBoundsChange(bounds);
        //更新Bounds
        mBoundsFinal.set(bounds);
        //更新变化矩阵
        updateShaderMatrix();
        //更新圆角
        updateConner();
    }

    @Override
    public void setAlpha(int alpha)
    {
        mBitmapPaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter)
    {
        mBitmapPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity()
    {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void draw(@NonNull Canvas canvas)
    {

        if (mRebuildShader)
        {
            BitmapShader bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            bitmapShader.setLocalMatrix(mShaderMatrix);// Shader.TileMode.CLAMP
            mBitmapPaint.setShader(bitmapShader);
            mRebuildShader = false;
        }

        if (mCircle)
        {
            // mDrawableRect 宽高是实际显示图片的宽高，类似于 marginTop 了  mDrawableRect.left
            float cx = mDrawableRect.width() / 2f + mDrawableRect.left;
            float cy = mDrawableRect.height() / 2f + mDrawableRect.top;
            float radiusX = mDrawableRect.width() / 2f;
            float radiusY = mDrawableRect.height() / 2f;
            float radiusDrawable = Math.min(radiusX, radiusY);
            float radiusBitmap = Math.min(mBitmapHeight, mBitmapWidth);
            float radius = Math.min(radiusBitmap, radiusDrawable);
            canvas.drawCircle(cx, cy, radius, mBitmapPaint);

            if (mBorderWidth > 0)
            {
                cx = mBorderRect.width() / 2f + mBorderRect.left;
                cy = mBorderRect.height() / 2f + mBorderRect.top;
                radiusX = mBorderRect.width() / 2f;
                radiusY = mBorderRect.height() / 2f;
                radiusDrawable = Math.min(radiusX, radiusY);
                radiusBitmap = Math.min(mBitmapHeight, mBitmapWidth);
                radius = Math.min(radiusBitmap, radiusDrawable);
                canvas.drawCircle(cx, cy, radius, mBorderPaint);
            }
        } else
        {
            updateDrawablePath();
            canvas.drawPath(mPath, mBitmapPaint);
            if (mBorderWidth > 0)
            {
                updateBorderPath();
                canvas.drawPath(mPath, mBorderPaint);
            }
        }
    }

    /**
     * 更新圆角
     */
    private void updateConner()
    {
        if (mCorner >= 0)
        {
            for (int i = 0; i < mCornerRadii.length; i++)
            {
                mCornerRadii[i] = mCorner;
            }
            return;
        }

        if (mCorner < 0)
        {
            mCornerRadii[0] = mCornerTopLeft;
            mCornerRadii[1] = mCornerTopLeft;
            mCornerRadii[2] = mCornerTopRight;
            mCornerRadii[3] = mCornerTopRight;
            mCornerRadii[4] = mCornerBottomRight;
            mCornerRadii[5] = mCornerBottomRight;
            mCornerRadii[6] = mCornerBottomLeft;
            mCornerRadii[7] = mCornerBottomLeft;
            return;
        }
    }

    /**
     * 更新Drawable路径
     */
    private void updateDrawablePath()
    {
        //must重置
        mPath.reset();
        mPath.addRoundRect(mDrawableRect, mCornerRadii, Path.Direction.CCW);
    }

    /**
     * 更新边框路径
     */
    private void updateBorderPath()
    {
        //must重置
        mPath.reset();
        mPath.addRoundRect(mBorderRect, mCornerRadii, Path.Direction.CCW);
    }

    /**
     * 根据ScaleType更新ShaderMatrix
     * 此函数涉及更新的属性：mBorderWidth || mScaleType || mCircle
     */
    private void updateShaderMatrix()
    {
        float scale;
        float dx = 0, dy = 0;
        float height, width;
        float half = mBorderWidth / 2f;
        mBounds.set(mBoundsFinal);
        switch (mScaleType)
        {
            case CENTER_INSIDE:
                if (mBitmapWidth <= mBounds.width() && mBitmapHeight <= mBounds.height())
                {
                    scale = 1.0f;
                    height = mBitmapHeight;
                    width = mBitmapWidth;
                } else
                {//bitmap > drawable
                    scale = Math.min(mBounds.width() / (float) mBitmapWidth, mBounds.height() / (float) mBitmapHeight);
                    if (mBounds.height() < mBounds.width())
                    {//高<宽
                        height = mBounds.height();
                        width = mBitmapWidth * scale;
                    } else if (mBounds.height() > mBounds.width())
                    {//宽<高
                        height = mBitmapHeight * scale;
                        width = mBounds.width();
                    } else
                    {//宽=高
                        height = mBitmapHeight * scale;
                        width = mBitmapWidth * scale;
                    }
                }
                //X,Y偏移
                dx = (int) ((mBounds.width() - mBitmapWidth * scale) * 0.5f + 0.5f);
                dy = (int) ((mBounds.height() - mBitmapHeight * scale) * 0.5f + 0.5f);

                mRectF = new RectF(dx, dy, width + dx, height + dy);
                //非圆 1/2兼容圆角
                mRectF.inset(mCircle ? mBorderWidth : half, mCircle ? mBorderWidth : half);
                mShaderMatrix.reset();
                mShaderMatrix.setScale(scale, scale);
                mShaderMatrix.postTranslate(dx, dy);
                break;
            case CENTER:
                height = Math.min(mBounds.height(), mBitmapRect.height());
                width = Math.min(mBounds.width(), mBitmapRect.width());
                //裁剪或者Margin（如果View大，则 margin Bitmap，如果View小则裁剪Bitmap）
                float cutOrMarginH = mBounds.height() - mBitmapRect.height();
                float cutOrMarginW = mBounds.width() - mBitmapRect.width();
                float halfH = cutOrMarginH / 2f, halfW = cutOrMarginW / 2f;
                float top = halfH > 0 ? halfH : 0;
                float left = halfW > 0 ? halfW : 0;
                dx = halfW;
                dy = halfH;

                mRectF = new RectF(left, top, left + width, top + height);
                //非圆 1/2兼容圆角
                mRectF.inset(mCircle ? mBorderWidth : half, mCircle ? mBorderWidth : half);
                mShaderMatrix.reset();
                mShaderMatrix.postTranslate((int) (dx + 0.5f) + half, (int) (dy + 0.5f) + half);
                break;
            case CENTER_CROP:
                mRectF.set(mBounds);
                //非圆 1/2兼容圆角
                mRectF.inset(mCircle ? mBorderWidth : half, mCircle ? mBorderWidth : half);
                if (mBitmapWidth * mRectF.height() > mRectF.width() * mBitmapHeight)
                {
                    scale = mRectF.height() / (float) mBitmapHeight;
                    dx = (mRectF.width() - mBitmapWidth * scale) * 0.5f;
                } else
                {
                    scale = mRectF.width() / (float) mBitmapWidth;
                    dy = (mRectF.height() - mBitmapHeight * scale) * 0.5f;
                }
                mShaderMatrix.reset();
                mShaderMatrix.setScale(scale, scale);
                mShaderMatrix.postTranslate((int) (dx + 0.5f) + half, (int) (dy + 0.5f) + half);
                break;
            default:
            case FIT_CENTER:
            case FIT_END:
            case FIT_START:
                //非圆 1/2兼容圆角
                mBounds.inset(mCircle ? mBorderWidth : half, mCircle ? mBorderWidth : half);
                mRectF.set(mBitmapRect);
                mShaderMatrix.setRectToRect(mBitmapRect, mBounds, scaleTypeToScaleToFit(mScaleType));
                mShaderMatrix.mapRect(mRectF);
                mShaderMatrix.setRectToRect(mBitmapRect, mRectF, Matrix.ScaleToFit.FILL);
                break;
            case FIT_XY:
                //非圆 1/2兼容圆角
                mBounds.inset(mCircle ? mBorderWidth : half, mCircle ? mBorderWidth : half);
                mRectF.set(mBounds);
                mShaderMatrix.reset();
                mShaderMatrix.setRectToRect(mBitmapRect, mRectF, Matrix.ScaleToFit.FILL);
                break;
        }
        if (mCircle)
        {
            //还原
            mBorderRect.set(mRectF.left - half, mRectF.top - half, mRectF.right + half, mRectF.bottom + half);
        } else
        {
            mBorderRect.set(mBoundsFinal);
            mBorderRect.inset(half, half);
        }
        mDrawableRect.set(mRectF);
        mRebuildShader = true;
    }

    /**
     * 更新边框
     */
    private void updateBorder()
    {
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(mBorderWidth);
    }

    public Bitmap getBitmap()
    {
        return mBitmap;
    }

    public RoundDrawable setScaleType(ImageView.ScaleType scaleType)
    {
        if (scaleType == null)
        {
            scaleType = ImageView.ScaleType.FIT_CENTER;
        }
        if (mScaleType != scaleType)
        {
            mScaleType = scaleType;
            //更新变化矩阵
            updateShaderMatrix();
            invalidateSelf();
        }
        return this;
    }

    public RoundDrawable setCircle(boolean circle)
    {
        mCircle = circle;
        //更新变化矩阵
        updateShaderMatrix();
        invalidateSelf();
        return this;
    }

    public RoundDrawable setBorderWidth(float borderWidth)
    {
        mBorderWidth = borderWidth;
        updateBorder();
        //更新变化矩阵
        updateShaderMatrix();
        invalidateSelf();
        return this;
    }

    public RoundDrawable setBorderColor(int borderColor)
    {
        mBorderColor = borderColor;
        updateBorder();
        invalidateSelf();
        return this;
    }

    public RoundDrawable setConner(float corner, float topLeft, float topRight, float bottomLeft, float bottomRight)
    {
        mCorner = corner;
        mCornerTopLeft = topLeft;
        mCornerTopRight = topRight;
        mCornerBottomLeft = bottomLeft;
        mCornerBottomRight = bottomRight;
        updateConner();
        invalidateSelf();
        return this;
    }

    public static RoundDrawable fromBitmap(Bitmap bitmap)
    {
        if (bitmap != null)
        {
            return new RoundDrawable(bitmap);
        } else
        {
            return null;
        }
    }

    public static Drawable fromDrawable(Drawable drawable)
    {
        if (drawable != null)
        {
            if (drawable instanceof RoundDrawable)
            {
                // just return if it's already a RoundedDrawable
                return drawable;
            } else if (drawable instanceof LayerDrawable)
            {
                ConstantState cs = drawable.mutate().getConstantState();
                LayerDrawable ld = (LayerDrawable) (cs != null ? cs.newDrawable() : drawable);

                int num = ld.getNumberOfLayers();

                // loop through layers to and change to RoundedDrawables if possible
                for (int i = 0; i < num; i++)
                {
                    Drawable d = ld.getDrawable(i);
                    ld.setDrawableByLayerId(ld.getId(i), fromDrawable(d));
                }
                return ld;
            }

            // try to get a bitmap from the drawable and
            Bitmap bm = drawableToBitmap(drawable);
            if (bm != null)
            {
                return new RoundDrawable(bm);
            }
        }
        return drawable;
    }

    public static Bitmap drawableToBitmap(Drawable drawable)
    {
        if (drawable instanceof BitmapDrawable)
        {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap;
        int width = Math.max(drawable.getIntrinsicWidth(), 2);
        int height = Math.max(drawable.getIntrinsicHeight(), 2);
        try
        {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        } catch (Throwable e)
        {
            e.printStackTrace();
            bitmap = null;
        }

        return bitmap;
    }

    private static Matrix.ScaleToFit scaleTypeToScaleToFit(ImageView.ScaleType st)
    {
        /**
         * 根据源码改造  sS2FArray[st.nativeInt - 1]
         */
        switch (st)
        {
            case FIT_XY:
                return Matrix.ScaleToFit.FILL;
            case FIT_START:
                return Matrix.ScaleToFit.START;
            case FIT_END:
                return Matrix.ScaleToFit.END;
            case FIT_CENTER:
                return Matrix.ScaleToFit.CENTER;
            default:
                return Matrix.ScaleToFit.CENTER;
        }
    }
}
