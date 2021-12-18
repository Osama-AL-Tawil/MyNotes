package com.os_tec.mynotes.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

    @Entity
    data class Users(
        @PrimaryKey val uid:String,
        @ColumnInfo(name = "userName") val userName:String,
        @ColumnInfo(name = "email") val email:String,
        @ColumnInfo(name = "password") val password:String
    )

    @Entity
    data class Notes(
        @PrimaryKey val id:String,
        @ColumnInfo(name = "uid") val uid:String,
        @ColumnInfo(name = "title") val title:String,
        @ColumnInfo(name = "content") val content:String,
        @ColumnInfo(name = "isFavorite") var isFavorite:Boolean,
        @ColumnInfo(name = "date") val date:String,
        @ColumnInfo(name = "color") val color:Int,
        @ColumnInfo(name = "timeMilliSecond") val timeMilliSecond:Long,
        @ColumnInfo(name = "viewType") val viewType:String
    )


