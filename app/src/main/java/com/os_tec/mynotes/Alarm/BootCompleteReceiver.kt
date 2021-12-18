package com.os_tec.mynotes.Alarm

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.os_tec.mynotes.SharedPreferences

class BootCompleteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            // ideally we should be fetching the data from a database
            val timeInMilli = SharedPreferences(context as Activity).getTimeMilli()
            Utils.setAlarm(context, timeInMilli)
        }
    }
}