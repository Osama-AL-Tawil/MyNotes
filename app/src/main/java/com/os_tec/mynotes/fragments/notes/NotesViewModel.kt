package com.os_tec.mynotes.fragments.notes

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.os_tec.mynotes.roomdb.Notes
import com.os_tec.mynotes.roomdb.RoomRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NotesViewModel:ViewModel() {
    val data=MutableLiveData<ArrayList<Notes>>()

    fun getData(activity: Activity):MutableLiveData<ArrayList<Notes>>{
        CoroutineScope(Dispatchers.IO).launch {
            data.postValue(RoomRepository(activity).getAllNotes())
            Log.e("roomDB","MainActivity"+ RoomRepository(activity).getAllNotes().toString())
        }
        return data
    }

}