package com.example.sodv3203_finalproject_group4.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.sodv3203_finalproject_group4.model.EventCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface EventCategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(eventCategory: EventCategory)

    @Update
    suspend fun update(eventCategory: EventCategory)

    @Delete
    suspend fun delete(eventCategory: EventCategory)

    @Query("SELECT * from eventCategories WHERE categoryId = :id")
    fun getEventCategory(id: Int): Flow<EventCategory>

    @Query("SELECT * from eventCategories ORDER BY categoryId ASC")
    fun getAllEventCategories(): Flow<List<EventCategory>>
}