package com.example.sodv3203_finalproject_group4.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

enum class EventStatus() {
    Available,
    Joined,
    Paid,
    Completed,
    Cancelled
}

@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true)
    var eventId: Int = 0,
    var userId: Int,
    var categoryId: Int,
    var productName: String,
    var location: String,
    var currHeadCount: Int,
    var dateFrom: Date,
    var dateTo: Date,
    var price: Double,
    var eventBy: String,
    var status: EventStatus,
    var isBookmark: Boolean,
    var imageName: String,
    var joinedUsers: List<Int> = emptyList()
)
