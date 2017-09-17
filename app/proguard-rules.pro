-optimizationpasses 2
-allowaccessmodification

#Preserve annotations, line numbers, and source file names
-keepattributes Signature,Exceptions,InnerClasses,EnclosingMethod,*Annotation*,LineNumberTable

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#To skip running ProGuard on Crashlytics
-keep public class * extends java.lang.Exception
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**
-dontnote com.crashlytics.**
-dontnote io.fabric.**
-printmapping mapping.txt



# Retrofit
-dontwarn okhttp3.**
-dontnote okhttp3.**
-dontwarn okio.**
-dontwarn retrofit2.**
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8
-dontwarn com.squareup.picasso.**
-keep public class * extends okhttp3.OkHttpClient{*;}
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}


# Retrolambda
-dontwarn java.lang.invoke.*
-dontwarn **$$Lambda$*


# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-dontnote com.google.gson.**


-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}