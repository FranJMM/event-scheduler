package com.martinezmencias.eventscheduler.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martinezmencias.eventscheduler.usecases.GetEventsUseCase
import com.martinezmencias.eventscheduler.usecases.RequestEventsUseCase
import com.martinezmencias.eventscheduler.domain.Error
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ListViewModel(
    private val getEventsUseCase: GetEventsUseCase,
    private val requestEventsUseCase: RequestEventsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getEventsUseCase().collect() { events ->
                _state.update { UiState(events = events) }
            }
        }
    }

    fun onUiReady() {
        viewModelScope.launch {
            val result = requestEventsUseCase()
            _state.update { UiState(error = result)  }
        }
    }

    data class UiState(
        val events: List<com.martinezmencias.eventscheduler.domain.Event>? = null,
        val error: Error? = null
    )
}