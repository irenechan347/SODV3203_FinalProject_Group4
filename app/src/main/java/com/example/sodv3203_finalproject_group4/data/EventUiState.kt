package com.example.sodv3203_finalproject_group4.data

import com.example.sodv3203_finalproject_group4.model.Event

data class EventUiState(
    val events: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentEventIndex: Int = 0,
    val joinedEventIds: List<Int> = emptyList()
) {
    val currentEvent: Event?
        get() = if (events.isNotEmpty() && currentEventIndex in events.indices) events[currentEventIndex] else null

    val totalEvents: Int
        get() = events.size
}
