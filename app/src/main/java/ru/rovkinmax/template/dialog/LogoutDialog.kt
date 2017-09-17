package ru.rovkinmax.template.dialog

import android.app.Dialog
import android.app.DialogFragment
import android.app.FragmentManager
import android.os.Bundle
import android.support.v7.app.AlertDialog
import ru.rovkinmax.template.R
import ru.rovkinmax.template.activity.SplashActivity
import ru.rovkinmax.template.util.showFragment
import ru.rovkinmax.template.util.startActivity

class LogoutDialog : DialogFragment() {

    companion object {
        fun show(fragmentManager: FragmentManager) {
            fragmentManager.showFragment(LogoutDialog())
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity, R.style.AppTheme_Dialog).create()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isCancelable = false
        activity.startActivity<SplashActivity>(clearHistory = true)

    }

}
