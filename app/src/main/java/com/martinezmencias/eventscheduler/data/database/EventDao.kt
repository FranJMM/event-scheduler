package com.martinezmencias.eventscheduler.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Transaction
    @Query("SELECT * FROM EventBasicEntity")
    fun getAll(): Flow<List<EventEntity>>

    @Transaction
    @Query("SELECT * FROM EventBasicEntity WHERE id = :id")
    fun findById(id: String): Flow<EventEntity>

    @Transaction
    @Query("SELECT COUNT(primaryId) FROM EventBasicEntity")
    suspend fun eventCount(): Int

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEventsBasic(events: List<EventBasicEntity>)
}