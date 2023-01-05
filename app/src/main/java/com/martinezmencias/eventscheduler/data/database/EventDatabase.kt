package com.martinezmencias.eventscheduler.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Event::class], version = 1, exportSchema = false)
abstract class EventDatabase : RoomDatabase() {

    companion object {
        fun createDatabase(application: Application) = Room.databaseBuilder(
            application,
            EventDatabase::class.java,
            "event-db"
        ).build()
    }

    abstract fun eventDao(): EventDao
}
