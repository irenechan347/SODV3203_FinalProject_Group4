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
    val eventId: Int,
    val categoryId: Int,
    val productName: String,
    val location: String,
    val currHeadCount: Int,
    val dateFrom: Date,
    val dateTo: Date,
    val price: Double,
    val eventBy: String,
    val status: EventStatus,
    val isBookmark: Boolean,
    @DrawableRes val imageId: Int
)
