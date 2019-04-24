package com.lwkandroid.wingsdemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Description: 模拟获取AccessToken的请求结果
 *
 * @author LWK
 * @date 2019/4/24
 */
public class AccessTokenBean implements Parcelable
{
    private String access_token;
    private long expire_time;

    public long getExpire_time()
    {
        return expire_time;
    }

    public void setExpire_time(long expire_time)
    {
        this.expire_time = expire_time;
    }

    public String getAccess_token()
    {
        return access_token;
    }

    public void setAccess_token(String access_token)
    {
        this.access_token = access_token;
    }

    /**
     * 判断是否过期
     */
    public boolean isExpire()
    {
        return System.currentTimeMillis() - expire_time >= 5000;
    }

    @Override
    public String toString()
    {
        return "AccessTokenBean{" +
                "access_token='" + access_token + '\'' +
                ", expire_time=" + expire_time +
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
        dest.writeString(this.access_token);
        dest.writeLong(this.expire_time);
    }

    public AccessTokenBean()
    {
    }

    protected AccessTokenBean(Parcel in)
    {
        this.access_token = in.readString();
        this.expire_time = in.readLong();
    }

    public static final Creator<AccessTokenBean> CREATOR = new Creator<AccessTokenBean>()
    {
        @Override
        public AccessTokenBean createFromParcel(Parcel source)
        {
            return new AccessTokenBean(source);
        }

        @Override
        public AccessTokenBean[] newArray(int size)
        {
            return new AccessTokenBean[size];
        }
    };
}
