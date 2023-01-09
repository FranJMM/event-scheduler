package com.martinezmencias.eventscheduler.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Event(
    @PrimaryKey(autoGenerate = true) val primaryId: Int = 0,
    val id: String,
    val name: String,
    val imageUrl: String,
    val startTime: String?,
    val salesUrl: String?,
    val salesStartTime: String?
)
