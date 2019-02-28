package com.lwkandroid.wings.qrcode;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import com.lwkandroid.wings.R;
import com.lwkandroid.wings.utils.ResourceUtils;

/**
 * Created by LWK
 * TODO 扫描二维码界面的参数
 */

public class QRCodeOptions implements Parcelable
{
    //是否显示为条形码样式
    private boolean isBarCodeMode = false;
    //是否显示相册入口
    private boolean showAlbum = true;
    //是否全屏扫描
    private boolean fullScreenScan = true;
    //ActionBar的标题
    private String actionBarTitle = ResourceUtils.getString(R.string.qrcodescan_title);
    //ActionBar的背景色
    private int actionBarBgColor = Color.parseColor("#50000000");
    //ActionBar文字颜色
    private int actionBarTextColor = Color.WHITE;
    //扫描框的颜色
    private int rectColor = Color.WHITE;
    //扫描框的边角颜色
    private int rectCornerColor = Color.WHITE;
    //扫描线的颜色
    private int scanLineColor = Color.WHITE;
    //扫描线的动画时长
    private int scanLineAnimDuration = 1000;
    //提示语
    private String hintText = ResourceUtils.getString(R.string.qrcodescan_hint);
    //提示语颜色
    private int hintColor = ResourceUtils.getColor(R.color.gray_darker);
    //二维码太小自动缩放
    private boolean autoZoom = false;

    public QRCodeOptions()
    {
    }

    public boolean isBarCodeMode()
    {
        return isBarCodeMode;
    }

    public void setBarCodeMode(boolean barCodeMode)
    {
        this.isBarCodeMode = barCodeMode;
    }

    public boolean isShowAlbum()
    {
        return showAlbum;
    }

    public void setShowAlbum(boolean showAlbum)
    {
        this.showAlbum = showAlbum;
    }

    public boolean isFullScreenScan()
    {
        return fullScreenScan;
    }

    public void setFullScreenScan(boolean fullScreenScan)
    {
        this.fullScreenScan = fullScreenScan;
    }

    public String getActionBarTitle()
    {
        return actionBarTitle;
    }

    public void setActionBarTitle(String actionBarTitle)
    {
        this.actionBarTitle = actionBarTitle;
    }

    public int getActionBarBgColor()
    {
        return actionBarBgColor;
    }

    public void setActionBarBgColor(int actionBarBgColor)
    {
        this.actionBarBgColor = actionBarBgColor;
    }

    public int getActionBarTextColor()
    {
        return actionBarTextColor;
    }

    public void setActionBarTextColor(int actionBarTextColor)
    {
        this.actionBarTextColor = actionBarTextColor;
    }

    public int getRectCornerColor()
    {
        return rectCornerColor;
    }

    public void setRectCornerColor(int rectCornerColor)
    {
        this.rectCornerColor = rectCornerColor;
    }

    public int getScanLineColor()
    {
        return scanLineColor;
    }

    public void setScanLineColor(int scanLineColor)
    {
        this.scanLineColor = scanLineColor;
    }

    public int getScanLineAnimDuration()
    {
        return scanLineAnimDuration;
    }

    public void setScanLineAnimDuration(int scanLineAnimDuration)
    {
        this.scanLineAnimDuration = scanLineAnimDuration;
    }

    public String getHintText()
    {
        return hintText;
    }

    public void setHintText(String hintText)
    {
        this.hintText = hintText;
    }

    public int getHintColor()
    {
        return hintColor;
    }

    public void setHintColor(int hintColor)
    {
        this.hintColor = hintColor;
    }

    public int getRectColor()
    {
        return rectColor;
    }

    public void setRectColor(int rectColor)
    {
        this.rectColor = rectColor;
    }

    public boolean isAutoZoom()
    {
        return autoZoom;
    }

    public void setAutoZoom(boolean autoZoom)
    {
        this.autoZoom = autoZoom;
    }

    @Override
    public String toString()
    {
        return "QRCodeOptions{" +
                "isBarCodeMode=" + isBarCodeMode +
                ", showAlbum=" + showAlbum +
                ", fullScreenScan=" + fullScreenScan +
                ", actionBarTitle='" + actionBarTitle + '\'' +
                ", actionBarBgColor=" + actionBarBgColor +
                ", actionBarTextColor=" + actionBarTextColor +
                ", rectColor=" + rectColor +
                ", rectCornerColor=" + rectCornerColor +
                ", scanLineColor=" + scanLineColor +
                ", scanLineAnimDuration=" + scanLineAnimDuration +
                ", hintText='" + hintText + '\'' +
                ", hintColor=" + hintColor +
                ", autoZoom=" + autoZoom +
                '}';
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeByte(this.isBarCodeMode ? (byte) 1 : (byte) 0);
        dest.writeByte(this.showAlbum ? (byte) 1 : (byte) 0);
        dest.writeByte(this.fullScreenScan ? (byte) 1 : (byte) 0);
        dest.writeString(this.actionBarTitle);
        dest.writeInt(this.actionBarBgColor);
        dest.writeInt(this.actionBarTextColor);
        dest.writeInt(this.rectColor);
        dest.writeInt(this.rectCornerColor);
        dest.writeInt(this.scanLineColor);
        dest.writeInt(this.scanLineAnimDuration);
        dest.writeString(this.hintText);
        dest.writeInt(this.hintColor);
        dest.writeByte(this.autoZoom ? (byte) 1 : (byte) 0);
    }

    protected QRCodeOptions(Parcel in)
    {
        this.isBarCodeMode = in.readByte() != 0;
        this.showAlbum = in.readByte() != 0;
        this.fullScreenScan = in.readByte() != 0;
        this.actionBarTitle = in.readString();
        this.actionBarBgColor = in.readInt();
        this.actionBarTextColor = in.readInt();
        this.rectColor = in.readInt();
        this.rectCornerColor = in.readInt();
        this.scanLineColor = in.readInt();
        this.scanLineAnimDuration = in.readInt();
        this.hintText = in.readString();
        this.hintColor = in.readInt();
        this.autoZoom = in.readByte() != 0;
    }

    public static final Creator<QRCodeOptions> CREATOR = new Creator<QRCodeOptions>()
    {
        @Override
        public QRCodeOptions createFromParcel(Parcel source)
        {
            return new QRCodeOptions(source);
        }

        @Override
        public QRCodeOptions[] newArray(int size)
        {
            return new QRCodeOptions[size];
        }
    };

    public static class Builder
    {
        private QRCodeOptions options;

        public Builder()
        {
            this.options = new QRCodeOptions();
        }

        public Builder setShowAlbum(boolean showAlbum)
        {
            options.setShowAlbum(showAlbum);
            return this;
        }

        public Builder setFullScreenScan(boolean fullScreenScan)
        {
            options.setFullScreenScan(fullScreenScan);
            return this;
        }

        public Builder setActionBarTitle(String title)
        {
            options.setActionBarTitle(title);
            return this;
        }

        public Builder setActionBarBgColor(int color)
        {
            options.setActionBarBgColor(color);
            return this;
        }

        public Builder setActionBarTextColor(int color)
        {
            options.setActionBarTextColor(color);
            return this;
        }

        public Builder setRectCornerColor(int color)
        {
            options.setRectCornerColor(color);
            return this;
        }

        public Builder setScanLineColor(int color)
        {
            options.setScanLineColor(color);
            return this;
        }

        public Builder setScanLineAnimDuration(int duration)
        {
            options.setScanLineAnimDuration(duration);
            return this;
        }

        public Builder setHintText(String hint)
        {
            options.setHintText(hint);
            return this;
        }

        public Builder setHintColor(int color)
        {
            options.setHintColor(color);
            return this;
        }

        public Builder setIsBarCodeMode(boolean isBarCodeMode)
        {
            options.setBarCodeMode(isBarCodeMode);
            return this;
        }

        public Builder setRectColor(int color)
        {
            options.setRectColor(color);
            return this;
        }

        public Builder setAutoZoom(boolean autoZoom)
        {
            options.setAutoZoom(autoZoom);
            return this;
        }

        public QRCodeOptions build()
        {
            return options;
        }
    }
}
