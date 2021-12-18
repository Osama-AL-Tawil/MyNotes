package com.os_tec.mynotes

import android.app.Activity
import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.os_tec.mynotes.roomdb.Notes

class SharedPreferences(val activity: Activity) {
    private val userData=activity.getSharedPreferences("userData",Context.MODE_PRIVATE)
    private val alarmData=activity.getSharedPreferences("alarmData",Context.MODE_PRIVATE)

    fun addUID(uid:String){
        userData.edit()
            .putString("uid",uid)
            .apply()
    }
    fun getUID():String{
        return userData.getString("uid","").toString()
    }


    //alarData
    fun setTime(time:Long,timeString: String){
        alarmData.edit()
            .putLong("timeMilliSecond",time)
            .putString("date",timeString)
            .apply()
    }

    fun getTime():String{
        return alarmData.getString("date","Empty").toString()
    }

    fun getTimeMilli():Long{
        return alarmData.getLong("timeMilliSecond",0)
    }

    fun resetTimeMilli(){
        alarmData.edit()
            .putLong("timeMilliSecond",0).apply()
    }




    fun logout(){
      userData.edit().clear().apply()
    }
}