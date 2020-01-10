package com.lwkandroid.wingsdemo.bean;

/**
 * Description:
 *
 * @author 20180004
 * @date 2020/1/9
 */
public class TestBean2
{
    private int flag;
    private String content;

    public int getFlag()
    {
        return flag;
    }

    public void setFlag(int flag)
    {
        this.flag = flag;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    @Override
    public String toString()
    {
        return "TestBean2{" +
                "flag=" + flag +
                ", content='" + content + '\'' +
                '}';
    }
}
