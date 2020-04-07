package com.lwkandroid.demo.rxhttp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lwkandroid.lib.core.annotation.NotProguard;

/**
 * Description:登录结果
 *
 * @author LWK
 * @date 2020/4/7
 */
@NotProguard
public class LoginResultBean implements Parcelable
{
    private String private_token;
    private long private_expire;
    private String access_token;
    private long access_expire;

    public String getPrivate_token()
    {
        return private_token;
    }

    public void setPrivate_token(String private_token)
    {
        this.private_token = private_token;
    }

    public long getPrivate_expire()
    {
        return private_expire;
    }

    public void setPrivate_expire(long private_expire)
    {
        this.private_expire = private_expire;
    }

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
        return "LoginResultBean{" +
                "private_token='" + private_token + '\'' +
                ", private_expire=" + private_expire +
                ", access_token='" + access_token + '\'' +
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
        dest.writeString(this.private_token);
        dest.writeLong(this.private_expire);
        dest.writeString(this.access_token);
        dest.writeLong(this.access_expire);
    }

    public LoginResultBean()
    {
    }

    protected LoginResultBean(Parcel in)
    {
        this.private_token = in.readString();
        this.private_expire = in.readLong();
        this.access_token = in.readString();
        this.access_expire = in.readLong();
    }

    public static final Parcelable.Creator<LoginResultBean> CREATOR = new Parcelable.Creator<LoginResultBean>()
    {
        @Override
        public LoginResultBean createFromParcel(Parcel source)
        {
            return new LoginResultBean(source);
        }

        @Override
        public LoginResultBean[] newArray(int size)
        {
            return new LoginResultBean[size];
        }
    };
}
