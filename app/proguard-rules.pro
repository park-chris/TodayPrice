# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# kakao sdk
-keep class com.kakao.sdk.**.model.* { <fields>; }
-keep class * extends com.google.gson.TypeAdapter
-dontwarn org.bouncycastle.jsse.**
-dontwarn org.conscrypt.*
-dontwarn org.openjsse.**

# kakao map
-keep class com.kakao.vectormap.** { *; }
-keep interface com.kakao.vectormap.**

# firebase
-keepattributes *Annotation*
-keepattributes Signature
-keep class com.google.android.gms.** { *; }
-keep class com.google.firebase.** { *; }
-keepclassmembers class com.crystal.todayprice.data.** {
  *;
}

# okHttp, retrofit
-dontwarn okhttp3.**
-dontwarn okio.**
-dontnote okhttp3.**
-dontnote retrofit2.Platform
-keepattributes Exceptions

