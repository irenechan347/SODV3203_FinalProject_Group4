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

    fun getEventById(eventId: Int): Flow<Event?> {
        return shoppingBuddyRepository.getEvent(eventId)
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

    fun getAllUsers(): Flow<List<User>> {
        return shoppingBuddyRepository.getAllUsers()
    }

    suspend fun getUserById(userid: Int): Flow<User?> {
        return shoppingBuddyRepository.getUserById(userid)
    }

    suspend fun getUserByEmail(email: String): Flow<User?> {
        return shoppingBuddyRepository.getUserByEmail(email)
    }

    suspend fun insertUser(user: User): Long {
        return shoppingBuddyRepository.insertUser(user)
    }

    suspend fun updateUserProfile(user: User) {
        shoppingBuddyRepository.updateUser(user)
    }

    suspend fun updateUser(
        userId: Int,
        displayName: String,
        name: String,
        email: String,
        phoneNo: String,
        password: String
    ) {
        val user = User(
            userId = userId,
            displayName = displayName,
            name = name,
            email = email,
            phoneNo = phoneNo,
            password = password
        )
        shoppingBuddyRepository.updateUser(user)
    }

    suspend fun updateEvent(event: Event) {
        shoppingBuddyRepository.updateEvent(event)
    }
}


