package com.example.sodv3203_finalproject_group4.ui

import EventUiState
import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.sodv3203_finalproject_group4.data.EventDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EventViewModel(private val context: Context) : ViewModel() {

    private val _eventUiState = MutableStateFlow(EventUiState())
    val eventUiState: StateFlow<EventUiState> = _eventUiState.asStateFlow()

    fun fetchEvents() {
        _eventUiState.value = EventUiState(isLoading = true)
        val eventsResult = EventDataSource.loadEvents(context)
        if (eventsResult.isNotEmpty()) {
            _eventUiState.value = EventUiState(events = eventsResult)
        } else {
            _eventUiState.value = EventUiState(error = "Failed to fetch events")
        }
    }

    fun onEventSelected(eventId: Int) {
        _eventUiState.update { currentState ->
            currentState.copy(currentEventIndex = eventId)
        }
    }

    fun joinEvent(eventId: Int, userId: Int) {
        val updatedUiState = _eventUiState.value.copy(
            joinedEventIds = _eventUiState.value.joinedEventIds + eventId
        )
        _eventUiState.value = updatedUiState
    }
}
