package com.os_tec.mynotes.roomdb

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.os_tec.mynotes.SharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomRepository (val activity: Activity?){
    private val dbBuilder = Room.databaseBuilder(
        activity!!, AppDatabase::class.java, "database-name").build()

    private val db=dbBuilder.roomInterface()


    suspend fun getAllNotes():ArrayList<Notes>{
        var data=ArrayList<Notes>()
        CoroutineScope(Dispatchers.IO).launch {
          data=db.getNotes(SharedPreferences(activity!!).getUID()) as ArrayList<Notes>
            Log.e("roomDB",data.toString())
        }.join()

        return data
    }

    suspend fun getFavorite():ArrayList<Notes>{
        var data=ArrayList<Notes>()
        CoroutineScope(Dispatchers.IO).launch {
          data=db.getFavorite(SharedPreferences(activity!!).getUID()) as ArrayList<Notes>
            Log.e("roomDB",data.toString())
        }.join()

        return data
    }

    fun addNote(data:Notes){
        CoroutineScope(Dispatchers.IO).launch {
            db.addNote(data)
            Log.e("roomDB",data.toString())
        }
    }

    fun deleteNote(noteId:String){
        CoroutineScope(Dispatchers.IO).launch {
            db.deleteNote(noteId)
        }
    }

    fun deleteAlarm(){
        CoroutineScope(Dispatchers.IO).launch {
            db.deleteAlarm()
        }
    }

    fun updateNote(id: String,title:String,content:String){
        CoroutineScope(Dispatchers.IO).launch {
            db.updateNotes(id,title,content)
        }
    }

    fun updateFavorite(id:String,isFavorite: Boolean){
        CoroutineScope(Dispatchers.IO).launch {
            db.updateFavorite(id,isFavorite)
        }
    }

    fun addFavorite(id:String,isFavorite:Boolean){
        CoroutineScope(Dispatchers.IO).launch {
            db.addFavorite(id,isFavorite)

        }
    }

    suspend fun signIn(userName: String, password: String): Users? {
        var userData:Users?=null
        CoroutineScope(Dispatchers.IO).launch {
            userData=db.signIn(userName, password)
        }.join()

        return userData
    }

     fun signup(users: Users){
        CoroutineScope(Dispatchers.IO).launch {
            db.signup(users)
        }
    }

}