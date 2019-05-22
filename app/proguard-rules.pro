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

#----------------okhttp3---------------------
-dontwarn javax.annotation.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-dontwarn org.codehaus.mojo.animal_sniffer.*
-dontwarn okhttp3.internal.platform.ConscryptPlatform
#----------------rxjava rxandroid ---------------------
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.ArrayQueueField* {
long producerIndex;
long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
-dontwarn javax.annotation.**
-dontwarn javax.inject.**
#----------------Retrofit ---------------------
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepattributes Signature, InnerClasses, EnclosingMethod,Exceptions
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

#----------------glide---------------------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# for DexGuard only
-keepresourcexmlelements manifest/application/meta-data@value=GlideModule

#----------------eventbus---------------------
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#----------------BaseRecyclerViewAdapterHelper---------------------
-keep class com.chad.library.adapter.** {*;}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}
#----------------DBFlow---------------------
-keep class * extends com.raizlabs.android.dbflow.config.DatabaseHolder { *; }
#----------------Gson---------------------
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
# 避免实体类被混淆
-keep class com.kimiffy.cn.biubiu.bean.** { *; }
#------------------aop--------------------
-keep class com.kimiffy.cn.biubiu.utils.aop.annotation.** { *; }
-adaptclassstrings
-keepattributes InnerClasses, EnclosingMethod, Signature, *Annotation*
-keepnames @org.aspectj.lang.annotation.Aspect class * {
    ajc* <methods>;
}
#AgentWeb
-keep class com.just.agentweb.** {
    *;
}
-dontwarn com.just.agentweb.**
#banner
-keep class com.youth.banner.** {
    *;
 }