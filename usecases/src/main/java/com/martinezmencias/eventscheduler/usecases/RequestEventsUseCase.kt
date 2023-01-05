package com.martinezmencias.eventscheduler.usecases

import com.martinezmencias.eventscheduler.data.repository.EventRepository
import com.martinezmencias.eventscheduler.domain.Event

class RequestEventsUseCase(private val eventsRepository: EventRepository) {

    suspend operator fun invoke(): List<Event> = eventsRepository.requestEvents()
}