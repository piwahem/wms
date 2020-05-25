package com.example.extension

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.example.R

fun Context.showDialog(
    title: String,
    message: String,
    callback: (() -> Unit)? = null
): AlertDialog {
    return AlertDialog.Builder(this).apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton(R.string.label_ok) { _: DialogInterface, _: Int ->
            callback?.invoke()
        }
        setNegativeButton(R.string.label_cancel) { dialog: DialogInterface, _: Int ->
            dialog.cancel()
        }
    }.show()
}