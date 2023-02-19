package com.martinezmencias.eventscheduler.domain

data class Event(
    val id: String,
    val name: String,
    val imageUrl: String,
    val startDateAndTime: DateAndTime?,
    val salesUrl: String?,
    val salesDateAndTime: DateAndTime?,
    val venue: Venue,
    val price: Price,
    val favorite: Boolean = false
)
