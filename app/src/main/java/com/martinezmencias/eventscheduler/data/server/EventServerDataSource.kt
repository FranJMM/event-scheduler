package com.martinezmencias.eventscheduler.data.server

import arrow.core.Either
import com.martinezmencias.eventscheduler.BuildConfig
import com.martinezmencias.eventscheduler.data.datasource.EventRemoteDataSource
import com.martinezmencias.eventscheduler.data.util.tryCall
import com.martinezmencias.eventscheduler.domain.Error
import com.martinezmencias.eventscheduler.domain.Event

class EventServerDataSource(private val remoteService: RemoteService) : EventRemoteDataSource {

    override suspend fun requestEvents(region: String): Either<Error, List<Event>> = tryCall {
        remoteService.requestEvents(
            apiKey = BuildConfig.API_KEY,
            countryCode = region,
            classificationName = "music"
        ).toDomainModel()
    }

    private fun RemoteResult.toDomainModel() = embedded.toDomainModel()

    private fun RemoteEmbedded.toDomainModel() = events.map {
        it.toDomainModel()
    }

    private fun RemoteEvent.toDomainModel() =
        Event(
            id = id,
            name = name,
            imageUrl = images.first().url
        )
}