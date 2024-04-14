package com.example.sodv3203_finalproject_group4.ui

import androidx.lifecycle.ViewModel
import com.example.sodv3203_finalproject_group4.data.EventUiState
import com.example.sodv3203_finalproject_group4.data.ShoppingBuddyRepository
import com.example.sodv3203_finalproject_group4.model.Event
import com.example.sodv3203_finalproject_group4.model.EventCategory
import com.example.sodv3203_finalproject_group4.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EventViewModel(
    //private val context: Context,
    private val shoppingBuddyRepository: ShoppingBuddyRepository
) : ViewModel() {

    private val _eventUiState = MutableStateFlow(EventUiState())
    val eventUiState: StateFlow<EventUiState> = _eventUiState.asStateFlow()

    suspend fun addEvent(event: Event) {
        //shoppingBuddyRepository.insertEvent(eventUiState.eventDetails.toEvent())
        shoppingBuddyRepository.insertEvent(event)
    }

    fun getAllEventCategories(): Flow<List<EventCategory>> {
        return shoppingBuddyRepository.getAllEventCategories()
    }

    fun getAllEvents(): Flow<List<Event>> {
        return shoppingBuddyRepository.getAllEvents()
    }

    suspend fun addAllEvents(events: MutableList<Event>) {
        events.forEach { shoppingBuddyRepository.insertEvent(it) }
    }

    suspend fun addAllEventCategories(eventCategories: List<EventCategory>) {
        //if(shoppingBuddyRepository.getAllEventCategories().equals(null))
        eventCategories.forEach { shoppingBuddyRepository.insertEventCategory(it) }
    }

    suspend fun addAllUsers(users: MutableList<User>) {
        users.forEach { shoppingBuddyRepository.insertUser(it) }
    }

    suspend fun getUserById(id: Int): Flow<User?> {
        return shoppingBuddyRepository.getUserById(id)
    }

    suspend fun getUserByEmail(email: String): Flow<User?> {
        return shoppingBuddyRepository.getUserByEmail(email)
    }

    suspend fun insertUser(user: User): Long {
        return shoppingBuddyRepository.insertUser(user)
    }


    /*
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
    */
}
