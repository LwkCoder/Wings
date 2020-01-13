#BusUtils
-keepclassmembers class * {
    @com.lwkandroid.lib.core.BusUtils$Bus <methods>;
}
#NotProguard
-keep @com.lwkandroid.lib.core.java.annotation.NotProguard class * {*;}
-keep class * {@com.lwkandroid.lib.core.java.annotation.NotProguard <fields>;}
-keepclassmembers class * {@com.lwkandroid.lib.core.java.annotation.NotProguard <methods>;}
