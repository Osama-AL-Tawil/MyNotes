package com.os_tec.mynotes

import android.app.Activity
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.os_tec.mynotes.activities.MainActivity
import com.os_tec.mynotes.databinding.CsAddBinding
import com.os_tec.mynotes.roomdb.Notes
import com.os_tec.mynotes.roomdb.RoomRepository
import org.greenrobot.eventbus.EventBus
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class BottomSheetAdd(val activity: Activity) {
    private val layoutInflater = LayoutInflater.from(activity).inflate(R.layout.cs_add, null)
    private val dialog = BottomSheetDialog(activity, R.style.SheetDialogStyle)
    private val binding = CsAddBinding.bind(layoutInflater)

    fun showDialog() {
        bindView()
        dialog.setContentView(layoutInflater)
        dialog.setCancelable(true)
        dialog.behavior.peekHeight= 2000
        dialog.show()
    }

    private fun bindView() {
        //binding.btnClose.setOnClickListener { dismissDialog() }
        binding.btnAlarm.setOnClickListener {
            AppFunction(activity).setTime()
        }
        binding.btnAdd.setOnClickListener {
            val uid= SharedPreferences(activity).getUID()
            val idRandom= UUID.randomUUID().toString()
            val title=binding.edTitle.text.toString()
            val content=binding.edContent.text.toString()
            val currentDateTime = AppFunction(activity).getDate()

            if (SharedPreferences(activity).getTimeMilli().toInt()!= 0){//set alarm note
                val time=SharedPreferences(activity).getTime()
                RoomRepository(activity).addNote(Notes(idRandom,uid,title,content,false,time,R.color.yellow,0,"alarm"))
                EventBus.getDefault().post(Notes(idRandom,uid,title,content,false,time,R.color.yellow,0,"alarm"))
                AppFunction(activity).setAlarm()

            }else{//set normal note
                RoomRepository(activity).addNote(Notes(idRandom,uid,title,content,false,currentDateTime,R.color.red,0,"note"))
                EventBus.getDefault().post(Notes(idRandom,uid,title,content,false,currentDateTime,R.color.red,0,"note"))
            }

             //refresh
            dismissDialog()
            Toast.makeText(activity, "Note Added Successfully", Toast.LENGTH_SHORT).show()
        }
    }

    private fun dismissDialog() {
        dialog.dismissWithAnimation = true
        dialog.dismiss()
    }



}