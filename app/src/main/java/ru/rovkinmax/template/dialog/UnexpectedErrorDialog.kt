package ru.rovkinmax.template.dialog

import android.app.Dialog
import android.app.DialogFragment
import android.app.FragmentManager
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import ru.rovkinmax.template.R
import ru.rovkinmax.template.util.showFragment


class UnexpectedErrorDialog : DialogFragment() {

    private var dismissCallback: (() -> Unit)? = null

    companion object {
        fun show(fragmentManager: FragmentManager, func: (() -> Unit)?) {
            val dialog = UnexpectedErrorDialog().apply { dismissCallback = func }
            fragmentManager.showFragment(dialog)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity, R.style.AppTheme_Dialog)
                .setMessage(R.string.error_unexpected)
                .setPositiveButton(R.string.button_ok, null)
                .create()
    }

    override fun onDismiss(dialog: DialogInterface?) {
        dismissCallback?.invoke()
        super.onDismiss(dialog)
    }
}
