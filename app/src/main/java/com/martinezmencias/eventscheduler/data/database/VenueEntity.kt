package com.martinezmencias.eventscheduler.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VenueEntity(
    @PrimaryKey val id: String,
    val name: String,
    val city: String,
    val state: String,
    val country: String,
    val address: String
)