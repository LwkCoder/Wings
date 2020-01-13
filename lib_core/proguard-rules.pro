#自定义注解NotProguard
-keepattributes *Annotation*
-keep @com.lwkandroid.lib.core.java.annotation.NotProguard class * {*;}
-keep class * {@com.lwkandroid.lib.core.java.annotation.NotProguard <fields>;}
-keepclassmembers class * {@com.lwkandroid.lib.core.java.annotation.NotProguard <methods>;}