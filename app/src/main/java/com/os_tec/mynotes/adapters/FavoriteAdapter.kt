package com.os_tec.mynotes.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.os_tec.mynotes.activities.AddActivity
import com.os_tec.mynotes.R
import com.os_tec.mynotes.databinding.RcFavoriteBinding
import com.os_tec.mynotes.roomdb.Notes
import com.os_tec.mynotes.roomdb.RoomRepository

class FavoriteAdapter(private val activity: Activity,  var data:ArrayList<Notes>):RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {
    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        private val binding = RcFavoriteBinding.bind(itemView)
        val title = binding.txtTitle
        val date = binding.txtDate
        val btnFavorite = binding.btnFavorite
        val btnAction = binding.layout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.rc_favorite,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.title.text=data[position].title
        holder.date.text=data[position].date


        holder.btnFavorite.setOnClickListener {
            RoomRepository(activity).updateFavorite(data[position].id, false)
            data.remove(data[position])
            notifyDataSetChanged()
        }


        holder.btnAction.setOnClickListener {
            val intent=Intent(activity, AddActivity::class.java)
            intent.putExtra("id",data[position].id)
            intent.putExtra("title",data[position].title)
            intent.putExtra("content",data[position].content)
            activity.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return data.size
    }


}