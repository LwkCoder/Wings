package com.lwkandroid.wings.net.cookie;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by LWK
 * TODO
 */

public interface ICookieJar extends CookieJar
{
    void add(List<Cookie> cookies);

    void remove(HttpUrl url, Cookie cookie);

    void clear();
}
