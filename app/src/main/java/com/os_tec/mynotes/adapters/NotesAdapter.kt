package com.os_tec.mynotes.adapters

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.os_tec.mynotes.Alarm.Utils
import com.os_tec.mynotes.AppFunction
import com.os_tec.mynotes.activities.AddActivity
import com.os_tec.mynotes.R
import com.os_tec.mynotes.databinding.RcAlarmBinding
import com.os_tec.mynotes.databinding.RcNotesBinding
import com.os_tec.mynotes.fragments.notes.NotesFragment
import com.os_tec.mynotes.roomdb.Notes
import com.os_tec.mynotes.roomdb.RoomRepository

class NotesAdapter(private val activity: Activity, var data:ArrayList<Notes>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
   companion object{
       const val NOTE_VIEW_TYPE=0
       const val ALARM_VIEW_TYPE=1
   }

    class NoteView(itemView:View):RecyclerView.ViewHolder(itemView){
        private val binding=RcNotesBinding.bind(itemView)
        val title=binding.txtTitle
        val date=binding.txtDate
        val btnFavorite=binding.btnFavorite
        val btnAction=binding.layout
    }

    class AlarmView(itemView:View):RecyclerView.ViewHolder(itemView){
        private val binding=RcAlarmBinding.bind(itemView)
        val title=binding.txtTitle
        val time=binding.txtTime
        val btnAlarm=binding.btnAlarm
        val btnAction=binding.layout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder:RecyclerView.ViewHolder?
        when(viewType){
            NOTE_VIEW_TYPE->{
                val view=LayoutInflater.from(activity).inflate(R.layout.rc_notes,parent,false)
                viewHolder=NoteView(view)

            }
            else->{
                val view=LayoutInflater.from(activity).inflate(R.layout.rc_alarm,parent,false)
                viewHolder=AlarmView(view)

            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {

            NOTE_VIEW_TYPE -> {
                val mHolder = holder as NoteView
                setBtnFavoriteBackground(data[position].isFavorite, mHolder)
                mHolder.title.text = data[position].title
                mHolder.date.text = data[position].date

                mHolder.btnFavorite.setOnClickListener {
                    val isFavorite = data[position].isFavorite
                    if (isFavorite) {//remove from Favorite
                        RoomRepository(activity).addFavorite(data[position].id, false)
                        data[position].isFavorite = false
                        setBtnFavoriteBackground(false, holder)
                    } else if (!isFavorite) {//add in Favorite
                        RoomRepository(activity).addFavorite(data[position].id, true)
                        data[position].isFavorite = true
                        setBtnFavoriteBackground(true, holder)
                    }
                }


                mHolder.btnAction.setOnClickListener {
                    val intent = Intent(activity, AddActivity::class.java)
                    intent.putExtra("id", data[position].id)
                    intent.putExtra("title", data[position].title)
                    intent.putExtra("content", data[position].content)
                    activity.startActivity(intent)
                }
            }

            ALARM_VIEW_TYPE -> {
                val mHolder = holder as AlarmView
                mHolder.title.text = data[position].title
                mHolder.time.text = data[position].date

                mHolder.btnAlarm.setOnClickListener {
                    val isFavorite = data[position].isFavorite

                }


                mHolder.btnAction.setOnClickListener {
                    val intent = Intent(activity, AddActivity::class.java)
                    intent.putExtra("id", data[position].id)
                    intent.putExtra("title", data[position].title)
                    intent.putExtra("content", data[position].content)
                    activity.startActivity(intent)
                }
            }


        }



    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        var viewType=0
        when(data[position].viewType){
            "note"->{viewType=0}
            "alarm"->{viewType=1}
        }
        return viewType
    }

    fun deleteNotes(Position:Int){
        if (data[Position].viewType=="alarm"){//cancel alarm
            Utils.setAlarm(activity,0)
        }
        RoomRepository(activity).deleteNote(data[Position].id)
        data.remove(data[Position])
        notifyDataSetChanged()
        Toast.makeText(activity,"note deleted",Toast.LENGTH_SHORT).show()
        Log.e("DataR","mPosition:$Position:::nPosition:$Position:::ArraySize:${data.size}")

    }

    private fun setBtnFavoriteBackground(isFavorite:Boolean,holder: NoteView){
        if (isFavorite) {//remove from Favorite
            holder.btnFavorite.setColorFilter(ContextCompat.getColor(activity,R.color.red))
        } else if (!isFavorite) {//add in Favorite
            holder.btnFavorite.setColorFilter(ContextCompat.getColor(activity,R.color.gray))


        }
    }
}