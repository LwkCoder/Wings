package com.lwkandroid.demo.rxhttp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lwkandroid.lib.core.annotation.NotProguard;

/**
 * Description:刷新动态密钥的结果
 *
 * @author LWK
 * @date 2020/4/7
 */
@NotProguard
public class AccessTokenResultBean implements Parcelable
{
    private String access_token;
    private long access_expire;

    public String getAccess_token()
    {
        return access_token;
    }

    public void setAccess_token(String access_token)
    {
        this.access_token = access_token;
    }

    public long getAccess_expire()
    {
        return access_expire;
    }

    public void setAccess_expire(long access_expire)
    {
        this.access_expire = access_expire;
    }

    @Override
    public String toString()
    {
        return "AccessTokenResultBean{" +
                "access_token='" + access_token + '\'' +
                ", access_expire=" + access_expire +
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
        dest.writeLong(this.access_expire);
    }

    public AccessTokenResultBean()
    {
    }

    protected AccessTokenResultBean(Parcel in)
    {
        this.access_token = in.readString();
        this.access_expire = in.readLong();
    }

    public static final Parcelable.Creator<AccessTokenResultBean> CREATOR = new Parcelable.Creator<AccessTokenResultBean>()
    {
        @Override
        public AccessTokenResultBean createFromParcel(Parcel source)
        {
            return new AccessTokenResultBean(source);
        }

        @Override
        public AccessTokenResultBean[] newArray(int size)
        {
            return new AccessTokenResultBean[size];
        }
    };
}
