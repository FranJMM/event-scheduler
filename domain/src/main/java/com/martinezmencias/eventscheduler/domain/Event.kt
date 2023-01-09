package com.martinezmencias.eventscheduler.domain

data class Event(
    val id: String,
    val name: String,
    val imageUrl: String,
    val startTime: String?,
    val salesUrl: String?,
    val salesStartTime: String?
)
