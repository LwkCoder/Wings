package com.lwkandroid.wings.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by LWK
 *  自定义注解，标识避免Proguard混淆
 * 【需在proguard-rules.pro下添加如下指令】
 * -keepattributes *Annotation*
 * -keep @com.lwkandroid.wings.annotation.NotProguard class * {*;}
 * -keep class * {@com.lwkandroid.x5testapp.wings.NotProguard <fields>;}
 * -keepclassmembers class * {@com.lwkandroid.wings.annotation.NotProguard <methods>;}
 */

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD})
public @interface NotProguard
{

}


