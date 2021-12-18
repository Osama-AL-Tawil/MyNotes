package com.os_tec.mynotes.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Users::class,Notes::class], version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun roomInterface(): RoomInterface
}
