package com.lwkandroid.wingsdemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LWK
 *
 */
public class TestData implements Parcelable
{
    private String name;
    private String value;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "TestData{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
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
        dest.writeString(this.name);
        dest.writeString(this.value);
    }

    public TestData()
    {
    }

    protected TestData(Parcel in)
    {
        this.name = in.readString();
        this.value = in.readString();
    }

    public static final Parcelable.Creator<TestData> CREATOR = new Parcelable.Creator<TestData>()
    {
        @Override
        public TestData createFromParcel(Parcel source)
        {
            return new TestData(source);
        }

        @Override
        public TestData[] newArray(int size)
        {
            return new TestData[size];
        }
    };
}
