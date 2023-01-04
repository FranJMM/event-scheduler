package com.martinezmencias.eventscheduler.data.datasource

import com.martinezmencias.eventscheduler.domain.Event

interface EventRemoteDataSource {
    suspend fun requestEvents(): List<Event>
}
