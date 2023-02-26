package com.martinezmencias.eventscheduler.data.datasource

import com.martinezmencias.eventscheduler.domain.Event

import kotlinx.coroutines.flow.Flow

interface EventLocalDataSource {

    val events: Flow<List<Event>>

    val favoriteEvents: Flow<List<Event>>

    fun findEventById(id: String): Flow<Event>

    suspend fun isEmpty(): Boolean

    suspend fun saveEvents(events: List<Event>)

    suspend fun updateEvent(event: Event)
}