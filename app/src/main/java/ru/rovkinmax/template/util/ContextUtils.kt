package ru.rovkinmax.template.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import java.util.*


fun Context.inflate(layoutRes: Int, viewGroup: ViewGroup? = null, attach: Boolean = false): View {
    return LayoutInflater.from(this).inflate(layoutRes, viewGroup, attach)
}

inline fun <reified T : Activity> IntentFor(context: Context, clearHistory: Boolean = false): Intent {
    val intent = Intent(context, T::class.java)
    if (clearHistory) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    return intent
}

inline fun <reified T : Activity> Context.startActivity(clearHistory: Boolean = false) {
    startActivity(IntentFor<T>(this, clearHistory))
}

fun Context.getDimen(res: Int): Int = resources.getDimensionPixelOffset(res)

fun Context.toast(message: String): Unit {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.changeLanguage(lang: String, country: String): Configuration {
    val conf = resources.configuration
    conf.locale = Locale(lang, country)
    Locale.setDefault(conf.locale)
    resources.updateConfiguration(conf, resources.displayMetrics)
    return conf
}

fun Context.openLocationSettings() {
    try {
        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    } catch (e: Exception) {
    }
}