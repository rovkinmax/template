package ru.rovkinmax.template.util

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

inline fun <reified T : Any> Intent.extrasNullable(key: String, defaultValue: T? = null): T? = extras.extrasNullable(key, defaultValue)

inline fun <reified T : Any> Intent.extras(key: String, defaultValue: T? = null): T = extras.extras(key, defaultValue)

inline fun <reified T : Any> Activity.extrasNullable(key: String, defaultValue: T? = null): T? = intent.extras.extrasNullable(key, defaultValue)

inline fun <reified T : Any> Activity.extras(key: String, defaultValue: T? = null): T = intent.extras.extras(key, defaultValue)

inline fun <reified T : Any> Bundle.extrasNullable(key: String, defaultValue: T? = null): T? {
    return if (containsKey(key)) extras(key) else defaultValue
}

inline fun <reified T : Any> Bundle.extras(key: String, defaultValue: T? = null): T {
    val clazz = T::class.java
    when {
        typed<Long>(clazz) -> {
            return (if (defaultValue == null) getLong(key)
            else getLong(key, defaultValue as Long)) as T
        }

        typed<Int>(clazz) -> {
            return (if (defaultValue == null) getInt(key)
            else getInt(key, defaultValue as Int)) as T
        }

        typed<Double>(clazz) -> {
            return (if (defaultValue == null) getDouble(key)
            else getDouble(key, defaultValue as Double)) as T
        }

        typed<String>(clazz) -> {
            return (if (defaultValue == null) getString(key)
            else getString(key, defaultValue as String)) as T
        }

        typed<Boolean>(clazz) -> {
            return (if (defaultValue == null) getBoolean(key)
            else getBoolean(key, defaultValue as Boolean)) as T
        }

        typed<Parcelable>(clazz) -> {
            return getParcelable<Parcelable>(key) as T
        }

        typed<Serializable>(clazz) -> {
            return getSerializable(key) as T
        }

        typed<IntArray>(clazz) -> {
            return getIntArray(key) as T
        }

        else -> throw IllegalArgumentException("Unsupported type ${T::class.java.name}")
    }
}

fun Activity.hasExtras(key: String): Boolean = intent.extras.containsKey(key)

inline fun <reified T : Any> typed(desiredClass: Class<*>): Boolean {
    return T::class.java.isAssignableFrom(desiredClass)
}

inline fun <reified T : Activity> Activity.startActivityForResult(requestCode: Int): Unit {
    val intent = IntentFor<T>(this)
    startActivityForResult(intent, requestCode)
}