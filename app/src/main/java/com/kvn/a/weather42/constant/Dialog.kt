package com.kvn.a.weather42.constant

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.Toast

class Dialog(context: Context, msg: String) {

    val dg = AlertDialog.Builder(context).create()
    val c = context
    val msg = msg

    fun showToast(toastDuration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(c,msg, toastDuration).show()
    }

    fun alert() {
            dg.setTitle("Alert")
            dg.setButton("OK") { dialog, which -> dg.dismiss() }
            dg.show()
    }

    fun alert3Dg(callback: String) {
        dg.setTitle("Alert")
        dg.setMessage(msg)
        dg.setButton(AlertDialog.BUTTON_POSITIVE, "POSITIVE", {
            dialogInterface, i ->
        })
        dg.setButton(AlertDialog.BUTTON_NEGATIVE, "NEGATIVE", {
            dialogInterface, j ->
        })
        dg.setButton(AlertDialog.BUTTON_NEUTRAL, "NEUTRAL", {
            dialogInterface, k ->
        })
        dg.show()
    }

    fun alert2Dg(callback: String) {
        dg.setTitle("Alert")
        dg.setMessage(msg)
        dg.setButton(AlertDialog.BUTTON_POSITIVE, "YES", {
            dialogInterface, i ->
            if(callback == "logout") {
               // Logout(c)
            } else {
                dg.dismiss()
            }
        })
        dg.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL", {
            dialogInterface, i -> dg.dismiss()
        })
        dg.show()
    }

    fun alertDg() {
        dg.setTitle("Alert")
        dg.setMessage(msg)
        dg.setButton(AlertDialog.BUTTON_POSITIVE, "OK", {
            dialogInterface, i ->
        })
        dg.show()
    }

}