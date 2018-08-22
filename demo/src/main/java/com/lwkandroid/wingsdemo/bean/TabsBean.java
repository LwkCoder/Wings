package com.lwkandroid.wingsdemo.bean;

/**
 * Created by LWK
 * TODO 内涵段子tab数据
 */

public class TabsBean
{
    private boolean double_col_mode;
    private String umeng_event;
    private boolean is_default_tab;
    private String url;
    private int list_id;
    private int refresh_interval;
    private int type;
    private String name;

    public boolean isDouble_col_mode()
    {
        return double_col_mode;
    }

    public void setDouble_col_mode(boolean double_col_mode)
    {
        this.double_col_mode = double_col_mode;
    }

    public String getUmeng_event()
    {
        return umeng_event;
    }

    public void setUmeng_event(String umeng_event)
    {
        this.umeng_event = umeng_event;
    }

    public boolean isIs_default_tab()
    {
        return is_default_tab;
    }

    public void setIs_default_tab(boolean is_default_tab)
    {
        this.is_default_tab = is_default_tab;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public int getList_id()
    {
        return list_id;
    }

    public void setList_id(int list_id)
    {
        this.list_id = list_id;
    }

    public int getRefresh_interval()
    {
        return refresh_interval;
    }

    public void setRefresh_interval(int refresh_interval)
    {
        this.refresh_interval = refresh_interval;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "TabsBean{" +
                "double_col_mode=" + double_col_mode +
                ", umeng_event='" + umeng_event + '\'' +
                ", is_default_tab=" + is_default_tab +
                ", url='" + url + '\'' +
                ", list_id=" + list_id +
                ", refresh_interval=" + refresh_interval +
                ", type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
