package com.example.sodv3203_finalproject_group4.model

import androidx.annotation.DrawableRes
import java.util.Date

enum class EventStatus() {
    Available,
    Joined,
    Paid,
    Completed,
    Cancelled
}

data class Event(
    var eventId: Int,
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
    //@DrawableRes var imageId: Int,
    var imageName: String,
    var joinedUsers: List<Int> = emptyList()
)
