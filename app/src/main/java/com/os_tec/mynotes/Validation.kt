package com.os_tec.velanew.helper

import android.app.Activity
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.os_tec.mynotes.R


class Validation(val activity:Activity) {
    private var bool:Boolean=true
    val context= activity
    private val logKey="ValidationClass.OS"


    fun validateEmail(value:String):Boolean {
         if (TextUtils.isEmpty(value)) {
            toastMessage(context.resources.getString(R.string.EmptyField))
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
             toastMessage(context.resources.getString(R.string.WrongEmail))

             return false
        }
        return bool
    }


    fun validateEditText(value:String):Boolean {
         if (TextUtils.isEmpty(value)) {
            toastMessage(context.resources.getString(R.string.EmptyField))
            return false
        }
        return bool
    }

    fun validatePhone(phoneNumber:String):Boolean {
        if (phoneNumber.trim() == "") {
            toastMessage(context.resources.getString(R.string.EmptyField))
            bool= false

        }else if (phoneNumber.length < 10) {
            toastMessage(context.resources.getString(R.string.WrongPhone))
            bool= false

        } else if (!Patterns.PHONE.matcher(phoneNumber).matches()) {
            toastMessage(context.resources.getString(R.string.WrongPhone))

            bool= false
        }

        return bool
    }


    private fun toastMessage(message:String){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }

    fun logMessage(message:String){
        Log.e(logKey,message)

    }
}