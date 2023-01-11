package com.martinezmencias.eventscheduler.data.database

import androidx.room.*
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
    @PrimaryKey val id: String,
    val name: String,
    val imageUrl: String,
    val startTime: Date?,
    val salesUrl: String?,
    val salesStartTime: Date?,
    val venueId: String?,
    @Embedded val price: Price,
    val favorite: Boolean
)

data class Price(
    @ColumnInfo(name = "minPrice") val min: Float,
    @ColumnInfo(name = "maxPrice") val max: Float,
    @ColumnInfo(name = "currencyPrice") val currency: String
)