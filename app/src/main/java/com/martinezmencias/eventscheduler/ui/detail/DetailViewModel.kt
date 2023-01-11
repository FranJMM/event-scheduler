package com.martinezmencias.eventscheduler.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martinezmencias.eventscheduler.domain.Event
import com.martinezmencias.eventscheduler.usecases.FindEventUseCase
import com.martinezmencias.eventscheduler.usecases.SwitchEventFavoriteUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailViewModel(
    private val eventId: String,
    private val findEventUseCase: FindEventUseCase,
    private val switchEventFavoriteUseCase: SwitchEventFavoriteUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    data class UiState(val event: Event? = null, val error: Error? = null)

    init {
        viewModelScope.launch {
            findEventUseCase(eventId).collect() { event ->
                _state.update { UiState(event) }
            }
        }
    }

    fun onFavoriteClicked() {
        viewModelScope.launch {
            state.value.event?.let { event ->
                switchEventFavoriteUseCase(event)
            }
        }
    }
}
