package com.os_tec.mynotes.activities

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.os_tec.mynotes.AppFunction
import com.os_tec.mynotes.databinding.ActivityAddBinding
import com.os_tec.mynotes.roomdb.RoomRepository
import org.greenrobot.eventbus.EventBus
import java.util.*

class AddActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        var timeInMilliSeconds:Long=0

        supportActionBar!!.title="My Note"
        val id=intent.getStringExtra("id").toString()
        val title=intent.getStringExtra("title").toString()
        val content=intent.getStringExtra("content").toString()

        binding.edTitle.setText(title)
        binding.edContent.setText(content)


        binding.btnAdd.setOnClickListener {
            val nTitle=binding.edTitle.text.toString()
            val nContent=binding.edContent.text.toString()
            RoomRepository(this@AddActivity).updateNote(id,nTitle,nContent)
            EventBus.getDefault().post("refresh") //refresh

            finish()
        }

        binding.btnSetDate.setOnClickListener {
            AppFunction(this).setTime()
        }
        binding.btnAddAlarm.setOnClickListener {
            AppFunction(this).setAlarm()
        }


    }
}