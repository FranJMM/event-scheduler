package com.martinezmencias.eventscheduler.usecases

import com.martinezmencias.eventscheduler.data.repository.EventRepository
import com.martinezmencias.eventscheduler.domain.Event
import kotlinx.coroutines.flow.Flow

class GetEventsUseCase(private val eventsRepository: EventRepository) {
    operator fun invoke(): Flow<List<Event>> = eventsRepository.events
}