package com.example.sodv3203_finalproject_group4.data

import com.example.sodv3203_finalproject_group4.model.Event
import com.example.sodv3203_finalproject_group4.model.EventCategory
import com.example.sodv3203_finalproject_group4.model.User
import kotlinx.coroutines.flow.Flow

interface ShoppingBuddyRepository {
    fun getAllEvents(): Flow<List<Event>>
    fun getEvent(id: Int): Flow<Event?>
    suspend fun insertEvent(event: Event)
    suspend fun deleteEvent(event: Event)
    suspend fun updateEvent(event: Event)
    fun getAllEventCategories(): Flow<List<EventCategory>>
    fun getEventCategory(id: Int): Flow<EventCategory?>
    suspend fun insertEventCategory(eventCategory: EventCategory)
    suspend fun deleteEventCategory(eventCategory: EventCategory)
    suspend fun updateEventCategory(eventCategory: EventCategory)
    fun getAllUsers(): Flow<List<User>>
    fun getUserById(id: Int): Flow<User?>
    fun getUserByEmail(email: String): Flow<User?>
    suspend fun insertUser(user: User): Long
    suspend fun deleteUser(user: User)
    suspend fun updateUser(user: User)
}