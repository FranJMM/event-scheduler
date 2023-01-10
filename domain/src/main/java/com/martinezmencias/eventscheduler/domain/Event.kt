package com.martinezmencias.eventscheduler.domain

import java.util.Date

data class Event(
    val id: String,
    val name: String,
    val imageUrl: String,
    val startTime: Date?,
    val salesUrl: String?,
    val salesStartTime: Date?,
    val venue: Venue,
    val price: Price,
)
