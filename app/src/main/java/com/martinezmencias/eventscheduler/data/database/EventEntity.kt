package com.martinezmencias.eventscheduler.data.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.*

@Entity
data class EventEntity(
    @Embedded val eventBasic: EventBasicEntity,
    @Relation(
        parentColumn = "venueId",
        entityColumn = "id"
    )
    val venue: VenueEntity
)

@Entity
data class EventBasicEntity(
    @PrimaryKey(autoGenerate = true) val primaryId: Int = 0,
    val id: String,
    val name: String,
    val imageUrl: String,
    val startTime: Date?,
    val salesUrl: String?,
    val salesStartTime: Date?,
    val venueId: String?
)
