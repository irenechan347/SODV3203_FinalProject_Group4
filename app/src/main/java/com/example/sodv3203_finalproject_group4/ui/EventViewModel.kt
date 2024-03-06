package com.example.sodv3203_finalproject_group4.ui

import androidx.lifecycle.ViewModel
import com.example.sodv3203_finalproject_group4.data.EventUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EventViewModel : ViewModel() {
    private val _eventUiState = MutableStateFlow(EventUiState())
    val eventUiState: StateFlow<EventUiState> = _eventUiState.asStateFlow()

}