package ru.rovkinmax.template

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import timber.log.Timber

class App : Application() {
    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}