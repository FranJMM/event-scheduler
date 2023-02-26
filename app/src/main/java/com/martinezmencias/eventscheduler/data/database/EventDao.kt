package com.martinezmencias.eventscheduler.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Transaction
    @Query("SELECT * FROM EventBasicEntity")
    fun getAll(): Flow<List<EventEntity>>

    @Transaction
    @Query("SELECT * FROM EventBasicEntity WHERE favorite is 1")
    fun getFavorites(): Flow<List<EventEntity>>

    @Transaction
    @Query("SELECT * FROM EventBasicEntity WHERE id = :id")
    fun findById(id: String): Flow<EventEntity>

    @Transaction
    @Query("SELECT COUNT(id) FROM EventBasicEntity")
    suspend fun eventCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEventsBasic(events: List<EventBasicEntity>)

    @Update
    suspend fun updateEventBasic(event: EventBasicEntity)
}