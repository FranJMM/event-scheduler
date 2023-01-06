package com.martinezmencias.eventscheduler.data.server

import com.martinezmencias.eventscheduler.BuildConfig
import com.martinezmencias.eventscheduler.data.datasource.EventRemoteDataSource
import com.martinezmencias.eventscheduler.domain.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EventServerDataSource(private val remoteService: RemoteService) : EventRemoteDataSource {

    override suspend fun requestEvents(region: String): List<Event> = withContext(Dispatchers.IO) {
        remoteService.requestEvents(
            apiKey = BuildConfig.API_KEY,
            countryCode = region,
            classificationName = "music"
        ).embedded.events.map {
            Event(
                id = it.id,
                name = it.name,
                imageUrl = it.images.first().url
            )
        }
    }
}