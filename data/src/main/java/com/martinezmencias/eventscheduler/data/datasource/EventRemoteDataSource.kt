package com.martinezmencias.eventscheduler.data.datasource

import com.martinezmencias.eventscheduler.domain.Event

interface EventRemoteDataSource {
    suspend fun requestEvents(region: String): List<Event>
}
