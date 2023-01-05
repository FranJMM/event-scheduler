package com.martinezmencias.eventscheduler.data.datasource

import com.martinezmencias.eventscheduler.domain.Event

import kotlinx.coroutines.flow.Flow

interface EventLocalDataSource {

    val events: Flow<List<Event>>

    suspend fun saveEvents(events: List<Event>)
}