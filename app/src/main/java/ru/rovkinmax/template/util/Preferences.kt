package ru.rovkinmax.template.util

import android.content.Context


class Preferences(context: Context) {
    private val appContext: Context = context.applicationContext

    var sampleParam by appContext.bindPreference(default = "")

}