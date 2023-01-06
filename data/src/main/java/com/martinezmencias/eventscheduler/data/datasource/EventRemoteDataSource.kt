package com.martinezmencias.eventscheduler.data.datasource

import arrow.core.Either
import com.martinezmencias.eventscheduler.domain.Error
import com.martinezmencias.eventscheduler.domain.Event

interface EventRemoteDataSource {
    suspend fun requestEvents(region: String): Either<Error, List<Event>>
}
