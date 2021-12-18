package com.os_tec.mynotes.roomdb

import androidx.room.*

@Dao
interface RoomInterface {

    @Insert
    fun signup(users: Users)
    @Query("SELECT * FROM users WHERE username=:username AND password=:password")

    fun signIn(username:String,password:String):Users

    @Query("SELECT * FROM notes WHERE uid=:uid")
    fun getNotes(uid:String):List<Notes>

    @Query("SELECT * FROM notes WHERE uid=:uid AND isFavorite Like 1")
    fun getFavorite(uid: String):List<Notes>

    @Insert
    fun addNote(note: Notes)

    @Delete
    fun deleteAll(note: Notes)

    @Query("UPDATE notes SET title=:title,content=:content WHERE id Like:id")
    fun updateNotes(id:String,title:String,content:String)

    @Query("UPDATE notes SET isFavorite=:isFavorite WHERE id Like:id")
    fun updateFavorite(id:String,isFavorite: Boolean)

    @Query("DELETE FROM Notes WHERE id Like (:noteId) ")
    fun deleteNote(noteId: String)

    @Query("DELETE FROM Notes WHERE viewType LIKE 'alarm' ")
    fun deleteAlarm()

    @Query("UPDATE notes SET isFavorite=:isFavorite WHERE id Like:id")
    fun addFavorite(id:String,isFavorite:Boolean)

//    @Update
//    fun updateNotes(note: Notes)
}