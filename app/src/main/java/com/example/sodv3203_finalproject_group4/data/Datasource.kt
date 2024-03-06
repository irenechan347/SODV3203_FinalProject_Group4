package com.example.sodv3203_finalproject_group4.data

import com.example.sodv3203_finalproject_group4.R
import com.example.sodv3203_finalproject_group4.model.Event
import com.example.sodv3203_finalproject_group4.model.EventCategory
import com.example.sodv3203_finalproject_group4.model.EventStatus
import com.example.sodv3203_finalproject_group4.model.User
import java.text.SimpleDateFormat

import java.util.Date

private val dateFormat = SimpleDateFormat("yyyy-MM-dd")

object Datasource {
    val user = User(
        1,
        "Oliver",
        "Oliver Johnson",
        "oliver@mybvc.ca",
        "+1 403 123 4567"
    )

    val categoryList = listOf(
        EventCategory(1, R.string.cat_batteries),
        EventCategory(2, R.string.cat_bakery),
        EventCategory(3, R.string.cat_fruits)
    )

    val eventList = listOf(
        Event(
            1,
            1,
            "Duracell  2A x 60 pcs",
            "Sage Hill",
            3,
            dateFormat.parse("2024-03-01"),
            dateFormat.parse("2024-03-31"),
            32.0,
            "Bruce787",
            EventStatus.Available,
            false,
            R.drawable.img_event_1
        ),
        Event(
            2,
            3,
            "Cherry x 3kg",
            "City Hall",
            4,
            dateFormat.parse("2024-03-01"),
            dateFormat.parse("2024-03-31"),
            30.0,
            "Sally",
            EventStatus.Joined,
            true,
            R.drawable.img_event_1
        )
    )
}