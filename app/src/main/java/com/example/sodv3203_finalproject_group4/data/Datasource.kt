package com.example.sodv3203_finalproject_group4.data

import com.example.sodv3203_finalproject_group4.R
import com.example.sodv3203_finalproject_group4.model.Event
import com.example.sodv3203_finalproject_group4.model.EventCategory
import com.example.sodv3203_finalproject_group4.model.EventStatus
import com.example.sodv3203_finalproject_group4.model.User
import java.text.SimpleDateFormat

object Datasource {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")

    private val events = mutableListOf<Event>()

    fun addEvent(event: Event) {
        events.add(event)
    }

    fun getUser(userId: Int): User {
        return users.find { it.userId == userId } ?: throw NoSuchElementException("User with ID $userId not found")
    }

    fun updateUser(userId: Int, updatedUser: User) {
        // Find the index of the user with the given userId
        val index = users.indexOfFirst { it.userId == userId }
        if (index != -1) {
            // Update the user data at the found index
            users[index] = updatedUser
        }
    }

    fun updateEventList(updatedEvent: Event) {
        val index = events.indexOfFirst { it.eventId == updatedEvent.eventId }
        if (index != -1) {
            events[index] = updatedEvent
        }
    }

    var users = mutableListOf(
        User(1, "Oliver", "Oliver Johnson", "oliver@mybvc.ca", "+1 403 123 4567"),
        User(2, "Emma", "Emma Thompson", "emma@mybvc.ca", "+1 403 765 4321"),
        User(3, "Sally", "Sally Thomas", "sally@mybvc.ca", "+1 403 765 4444"),
        User(4, "John", "John Owen", "john@mybvc.ca", "+1 403 765 3333"),
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

    var eventList = listOf(
        Event(
            1,
            1,
            "Duracell  2A x 60 pcs",
            "Sage Hill",
            3,
            dateFormat.parse("2024-03-01"),
            dateFormat.parse("2024-03-31"),
            32.0,
            "Oliver",
            EventStatus.Available,
            false,
            R.drawable.img_event_1,
            listOf(1,2,3)
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
            R.drawable.img_event_2,
            listOf(1,2,3,4)
        ),
        Event(
            3,
            2,
            "Whole Grain Bread (2 Loaves)",
            "The Baker's Corner",
            3,
            dateFormat.parse("2024-03-10"),
            dateFormat.parse("2024-03-20"),
            5.00,
            "Sally",
            EventStatus.Paid,
            false,
            R.drawable.img_event_8,
            listOf(1,4)
        ),
        Event(
            4,
            2,
            "Organic Flour (5kg Bag)",
            "The Miller's Mill",
            2,
            dateFormat.parse("2024-03-12"),
            dateFormat.parse("2024-03-25"),
            10.00,
            "Emma",
            EventStatus.Completed,
            true,
            R.drawable.img_event_9,
            listOf(2)
        ),
        Event(
            5,
            3,
            "Water Melon (10 Pcs)",
            "Herron Mews",
            2,
            dateFormat.parse("2024-03-16"),
            dateFormat.parse("2024-03-22"),
            30.00,
            "John",
            EventStatus.Cancelled,
            false,
            R.drawable.img_event_4,
            listOf(4)
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
            "Emma",
            EventStatus.Available,
            false,
            R.drawable.img_event_5,
            listOf(2,3)
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
            "John",
            EventStatus.Available,
            true,
            R.drawable.img_event_6,
            listOf(2,4)
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
            "Sally",
            EventStatus.Available,
            true,
            R.drawable.img_event_6,
            listOf(1,3)
        )
    )
}
