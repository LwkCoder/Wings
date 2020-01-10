package com.lwkandroid.wingsdemo.bean;

/**
 * Description:
 *
 * @author 20180004
 * @date 2020/1/9
 */
public class TestBean1
{
    private int id;
    private String name;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
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
        return "TestBean1{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
