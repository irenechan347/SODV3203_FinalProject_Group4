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
    val users = listOf(
        User(1, "Oliver", "Oliver Johnson", "oliver@mybvc.ca", "+1 403 123 4567"),
        User(2, "Emma", "Emma Thompson", "emma@mybvc.ca", "+1 403 765 4321"),
        // Add more User objects as needed
    )

    val categoryList = listOf(
        EventCategory(1, R.string.cat_batteries),
        EventCategory(2, R.string.cat_bakery),
        EventCategory(3, R.string.cat_fruits),
        //EventCategory(4, R.string.cat_others)
    )

    val categoryMap = mapOf(
        1 to "Batteries",
        2 to "Bakery",
        3 to "Fruits",
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
            R.drawable.img_event_2
        ),
        Event(
            3,
            2,  // Category ID
            "Whole Grain Bread (2 Loaves)",  // Title
            "The Baker's Corner",
            3,
            dateFormat.parse("2024-03-10"),
            dateFormat.parse("2024-03-20"),
            5.00,
            "JohnDoe",
            EventStatus.Paid,
            false,
            R.drawable.img_event_8
        ),
        Event(
            4,
            2,  // Category ID (Same as above)
            "Organic Flour (5kg Bag)",
            "The Miller's Mill",
            2,
            dateFormat.parse("2024-03-12"),
            dateFormat.parse("2024-03-25"),
            10.00,
            "JaneSmith",
            EventStatus.Completed,
            false,
            R.drawable.img_event_9
        ),
        Event(
            5,
            3,  // Category ID (Same as above)
            "Water Melon (10 Pcs)",
            "Herron Mews",
            2,
            dateFormat.parse("2024-03-16"),
            dateFormat.parse("2024-03-22"),
            30.00,
            "Sally",
            EventStatus.Cancelled,
            false,
            R.drawable.img_event_4
        ),
        Event(
            6,
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
            R.drawable.img_event_5
        ),
        Event(
            7,
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
            R.drawable.img_event_6
        ),
        Event(
            8,
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
            R.drawable.img_event_6
        ),

    )
}