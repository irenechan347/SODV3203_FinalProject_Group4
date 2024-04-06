package com.example.sodv3203_finalproject_group4.data

import com.example.sodv3203_finalproject_group4.model.Event
import com.example.sodv3203_finalproject_group4.model.EventCategory
import com.example.sodv3203_finalproject_group4.model.User
import kotlinx.coroutines.flow.Flow

class OfflineShoppingBuddyRepository(
    private val eventDao: EventDao,
    private val eventCategoryDao: EventCategoryDao,
    private val userDao: UserDao
) : ShoppingBuddyRepository {
    override fun getAllEvents(): Flow<List<Event>> = eventDao.getAllEvents()
    override fun getEvent(id: Int): Flow<Event?> = eventDao.getEvent(id)
    override suspend fun insertEvent(event: Event) = eventDao.insert(event)
    override suspend fun deleteEvent(event: Event) = eventDao.delete(event)
    override suspend fun updateEvent(event: Event) = eventDao.update(event)
    override fun getAllEventCategories(): Flow<List<EventCategory>> = eventCategoryDao.getAllEventCategories()
    override fun getEventCategory(id: Int): Flow<EventCategory?> = eventCategoryDao.getEventCategory(id)
    override suspend fun insertEventCategory(eventCategory: EventCategory) = eventCategoryDao.insert(eventCategory)
    override suspend fun deleteEventCategory(eventCategory: EventCategory) = eventCategoryDao.delete(eventCategory)
    override suspend fun updateEventCategory(eventCategory: EventCategory) = eventCategoryDao.update(eventCategory)
    override fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()
    override fun getUserById(id: Int): Flow<User?> = userDao.getUserById(id)
    override fun getUserByEmail(email: String): Flow<User?> = userDao.getUserByEmail(email)
    override suspend fun insertUser(user: User) = userDao.insert(user)
    override suspend fun deleteUser(user: User) = userDao.delete(user)
    override suspend fun updateUser(user: User) = userDao.update(user)
}