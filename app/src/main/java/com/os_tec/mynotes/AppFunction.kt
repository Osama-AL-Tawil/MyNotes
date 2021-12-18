package com.os_tec.mynotes

import android.R
import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.text.format.DateFormat
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.os_tec.mynotes.Alarm.Utils
import java.text.SimpleDateFormat
import java.util.*


class AppFunction(val activity:Activity) {

     fun showKeyboard() {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

     fun hideKeyboard() {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
    }

    fun getDate():String{
        val c: Calendar = Calendar.getInstance()
        val monthName = arrayOf(
            "January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November",
            "December"
        )
        val month = monthName[c.get(Calendar.MONTH)]
        println("Month name:$month")
        //val year: Int = c.get(Calendar.YEAR)
        val date: Int = c.get(Calendar.DATE)
        //val currentDate =" $date $month /$year"
            return "$date\n$month"
    }

   fun setTime() {
        var timeInMilliSeconds:Long=0
       // Get Current Time
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        // Launch Time Picker Dialog
        val timePickerDialog = TimePickerDialog(activity,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minuteOfHour ->

                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minuteOfHour)
                calendar.set(Calendar.SECOND, 0)
                val date=longDateFormat(calendar)
                timeInMilliSeconds = date.time

                //save time in sharedPreferences
                SharedPreferences(activity).setTime(timeInMilliSeconds,normalTimeFormat(hourOfDay,minuteOfHour))//storeTime

                Log.e("TIME","TimeMilliS:"+date.time.toString())
                Log.e("TIME","Time:"+normalTimeFormat(hourOfDay,minuteOfHour))
                Log.e("TIME", "Date:$date")


            }, hour, minute, false)
        timePickerDialog.show()
    }


    fun setAlarm(){
        val time= SharedPreferences(activity).getTimeMilli()
        if (time.toInt() != 0) {
            Toast.makeText(activity, "Alarm has been set!", Toast.LENGTH_LONG).show()
            Utils.setAlarm(activity, time)
            SharedPreferences(activity).resetTimeMilli()
        } else {
            Toast.makeText(activity, "Please enter the time first!", Toast.LENGTH_LONG).show()
        }
    }

    fun normalTimeFormat(hourOfDay:Int,minuteOfHour:Int):String{
        val amPm = if (hourOfDay < 12) "am" else "pm"
        return String.format("%02d:%02d %s", hourOfDay, minuteOfHour, amPm)
    }

//    fun convertDate(dateInMilliseconds:Long, dateFormat:String): {
//        return DateFormat.format(dateFormat,dateInMilliseconds)
//    }

    fun longDateFormat(calendar:Calendar):Date{
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
        val formattedDate = sdf.format(calendar.time)
        return sdf.parse(formattedDate)!!
    }


    fun passwordToggle(status:Boolean, editText: EditText, toggleImage: ImageView):Boolean{
        var toggleStatus=status
        if (!toggleStatus){
            toggleStatus=true
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
            toggleImage.setImageResource(com.os_tec.mynotes.R.drawable.ic_eye_hide)
        }else{
            toggleStatus=false
            editText.transformationMethod= HideReturnsTransformationMethod.getInstance()
            toggleImage.setImageResource(com.os_tec.mynotes.R.drawable.ic_eye_show)
        }
        return toggleStatus
    }

//    fun setLocalLanguage(context: Context): ContextWrapper {
//        // get chosen language from shared preference
//        val appLanguage = SharedPreferences().getAppLanguage()
//        val local = Locale(appLanguage)
//        return ContextUtils.updateLocale(context, local)
//    }
}