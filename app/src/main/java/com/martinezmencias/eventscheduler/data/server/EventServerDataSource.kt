package com.martinezmencias.eventscheduler.data.server

import arrow.core.Either
import com.martinezmencias.eventscheduler.BuildConfig
import com.martinezmencias.eventscheduler.data.datasource.EventRemoteDataSource
import com.martinezmencias.eventscheduler.data.util.parseDate
import com.martinezmencias.eventscheduler.data.util.tryCall
import com.martinezmencias.eventscheduler.domain.Error
import com.martinezmencias.eventscheduler.domain.Event
import com.martinezmencias.eventscheduler.domain.Price
import com.martinezmencias.eventscheduler.domain.Venue

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
            imageUrl = images.filter { it.ratio == "16_9" }.maxByOrNull { it.width }?.url ?: "",
            startTime = dates?.start?.time.parseDate(),
            salesUrl = salesUrl,
            salesStartTime = salesDates?.publicSales?.startTime.parseDate(),
            venue = embeddedVenues?.venues?.first()?.toDomainModel() ?: Venue("", "", "", "", "", ""),
            price = prices?.find { it.type == "standard" }?.toDomainModel() ?: Price(0F, 0F, "")
        )

    private fun RemoteVenue.toDomainModel() =
        Venue(
            id = id,
            name = name,
            city = city?.name ?: "",
            country = country?.name ?: "",
            state = state?.name ?: "",
            address = address?.line1 ?: ""
        )

    private fun RemotePrice.toDomainModel() = Price(min = min, max = max, currency = currency)
}