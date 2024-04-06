package com.example.sodv3203_finalproject_group4.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "eventCategories")
data class EventCategory(
    @PrimaryKey(autoGenerate = true)
    val categoryId: Int = 0,
    val categoryName: String
)
