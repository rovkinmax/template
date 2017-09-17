package ru.rovkinmax.template.util

import android.app.Fragment
import android.app.FragmentManager

inline fun <reified T : Fragment> FragmentManager.showFragment(fragment: T, tag: String = T::class.java.name): Unit {
    beginTransaction()
            .add(fragment, tag)
            .commitAllowingStateLoss()
}
