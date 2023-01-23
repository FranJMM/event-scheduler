package com.martinezmencias.eventscheduler.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martinezmencias.eventscheduler.data.util.toError
import com.martinezmencias.eventscheduler.usecases.GetEventsUseCase
import com.martinezmencias.eventscheduler.usecases.RequestEventsUseCase
import com.martinezmencias.eventscheduler.domain.Error
import com.martinezmencias.eventscheduler.domain.Event
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
            getEventsUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect() { events ->
                    _state.update { UiState(events = events) }
                }
        }
    }

    fun onUiReady() {
        viewModelScope.launch {
            val result = requestEventsUseCase()
            _state.update { state.value.copy(error = result)  }
        }
    }

    data class UiState(
        val events: List<Event>? = null,
        val error: Error? = null
    )
}