package com.martinezmencias.eventscheduler.data.database

import androidx.room.*
import com.martinezmencias.eventscheduler.domain.DateAndTime
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
    @Embedded val startDateAndTime: StartDateAndTime?,
    val salesUrl: String?,
    @Embedded val salesDateAndTime: SalesDateAndTime?,
    val venueId: String?,
    @Embedded val price: Price,
    val favorite: Boolean
)

data class Price(
    @ColumnInfo(name = "minPrice") val min: Float,
    @ColumnInfo(name = "maxPrice") val max: Float,
    @ColumnInfo(name = "currencyPrice") val currency: String
)

data class StartDateAndTime(
    @ColumnInfo(name = "startDate") val startDate: String?,
    @ColumnInfo(name = "startTime") val startTime: String?
)

data class SalesDateAndTime(
    @ColumnInfo(name = "salesDate") val salesDate: String?,
    @ColumnInfo(name = "salesTime") val salesTime: String?
)
