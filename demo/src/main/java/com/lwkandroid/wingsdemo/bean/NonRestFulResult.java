package com.lwkandroid.wingsdemo.bean;

import java.util.List;

/**
 * Created by LWK
 * 完全自定义请求结果，非RestFul风格
 */

public class NonRestFulResult
{
    private String tmall;
    private List<List<String>> result;

    public String getTmall()
    {
        return tmall;
    }

    public void setTmall(String tmall)
    {
        this.tmall = tmall;
    }

    public List<List<String>> getResult()
    {
        return result;
    }

    public void setResult(List<List<String>> result)
    {
        this.result = result;
    }

    @Override
    public String toString()
    {
        return "NonRestFulResult{" +
                "tmall='" + tmall + '\'' +
                ", result=" + result +
                '}';
    }
}
