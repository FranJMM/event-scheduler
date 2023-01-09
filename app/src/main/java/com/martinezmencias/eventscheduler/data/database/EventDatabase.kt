package com.martinezmencias.eventscheduler.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [EventBasicEntity::class, VenueEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class EventDatabase : RoomDatabase() {

    companion object {
        fun createDatabase(application: Application) = Room.databaseBuilder(
            application,
            EventDatabase::class.java,
            "event-db"
        ).build()
    }

    abstract fun eventDao(): EventDao

    abstract fun venueDao(): VenueDao
}
