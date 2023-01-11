package com.martinezmencias.eventscheduler.usecases

import com.martinezmencias.eventscheduler.data.repository.EventRepository
import com.martinezmencias.eventscheduler.domain.Event

class SwitchEventFavoriteUseCase (private val repository: EventRepository) {

    suspend operator fun invoke(event: Event) = repository.switchFavorite(event)
}