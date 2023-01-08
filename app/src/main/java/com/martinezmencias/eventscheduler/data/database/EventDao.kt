package com.martinezmencias.eventscheduler.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Query("SELECT * FROM Event")
    fun getAll(): Flow<List<Event>>

    @Query("SELECT * FROM Event WHERE id = :id")
    fun findById(id: String): Flow<Event>

    @Query("SELECT COUNT(primaryId) FROM Event")
    suspend fun eventCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<Event>)
}