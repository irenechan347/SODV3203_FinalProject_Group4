package com.example.sodv3203_finalproject_group4.data

import com.example.sodv3203_finalproject_group4.R
import com.example.sodv3203_finalproject_group4.model.Event
import com.example.sodv3203_finalproject_group4.model.EventCategory
import com.example.sodv3203_finalproject_group4.model.EventStatus
import com.example.sodv3203_finalproject_group4.model.User
import java.text.SimpleDateFormat

/*
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
        User(4, "John", "John Owen", "john@mybvc.ca", "+1 403 765 3333")
    )

    val categoryList = listOf(
        EventCategory(1, "Batteries"),
        EventCategory(2, "Bakery"),
        EventCategory(3, "Fruits")

        //EventCategory(1, R.string.cat_batteries),
        //EventCategory(2, R.string.cat_bakery),
        //EventCategory(3, R.string.cat_fruits),

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
            dateFormat.parse("2024-03-20"),
            dateFormat.parse("2024-03-23"),
            32.0,
            "Oliver",
            EventStatus.Available,
            false,
            "img_event_1",
            //R.drawable.img_event_1,
            listOf(1,2)
        ),
        Event(
            2,
            3,
            "Cherry x 3kg",
            "City Hall",
            4,
            dateFormat.parse("2024-03-16"),
            dateFormat.parse("2024-03-19"),
            30.0,
            "Sally",
            EventStatus.Joined,
            true,
            "img_event_2",
            //R.drawable.img_event_2,
            listOf(1,2,3,4)
        ),
        Event(
            3,
            2,
            "Whole Grain Bread (2 Loaves)",
            "The Baker's Corner",
            3,
            dateFormat.parse("2024-03-17"),
            dateFormat.parse("2024-03-20"),
            5.00,
            "Sally",
            EventStatus.Paid,
            false,
            "img_event_8",
            //R.drawable.img_event_8,
            listOf(1,2,4)
        ),
        Event(
            4,
            2,
            "Organic Flour (5kg Bag)",
            "The Miller's Mill",
            2,
            dateFormat.parse("2024-03-12"),
            dateFormat.parse("2024-03-15"),
            10.00,
            "Emma",
            EventStatus.Completed,
            true,
            "img_event_9",
            //R.drawable.img_event_9,
            listOf(3,4)
        ),
        Event(
            5,
            3,
            "Water Melon (10 Pcs)",
            "Herron Mews",
            2,
            dateFormat.parse("2024-03-16"),
            dateFormat.parse("2024-03-19"),
            30.00,
            "John",
            EventStatus.Cancelled,
            false,
            "img_event_4",
            //R.drawable.img_event_4,
            listOf(4)
        ),
        Event(
            6,
            1,
            "NoBrand Battery 2A x 30 pcs",
            "Bow Valley Colleage",
            3,
            dateFormat.parse("2024-03-28"),
            dateFormat.parse("2024-03-31"),
            38.0,
            "Emma",
            EventStatus.Available,
            false,
            "img_event_5",
            //R.drawable.img_event_5,
            listOf(2,3)
        ),
        Event(
            7,
            1,
            "Duracell  2A x 60 pcs",
            "Sage Hill",
            3,
            dateFormat.parse("2024-03-27"),
            dateFormat.parse("2024-03-30"),
            32.0,
            "John",
            EventStatus.Cancelled,
            true,
            "img_event_6",
            //R.drawable.img_event_6,
            listOf(2,4)
        ),
        Event(
            8,
            1,
            "Duracell  2A x 60 pcs",
            "BrentWood",
            3,
            dateFormat.parse("2024-03-29"),
            dateFormat.parse("2024-04-01"),
            32.0,
            "Sally",
            EventStatus.Available,
            true,
            "img_event_6",
            //R.drawable.img_event_6,
            listOf(1,3)
        ),
        Event(
            9,
            3,
            "Strawbeery 1(box)",
            "Herron Mews",
            2,
            dateFormat.parse("2024-03-29"),
            dateFormat.parse("2024-04-01"),
            36.0,
            "Sally",
            EventStatus.Available,
            true,
            "img_event_3",
            //R.drawable.img_event_3,
            listOf(3)
        ),
        Event(
            10,
            2,
            "Bread 20 Pcs",
            "South Center",
            3,
            dateFormat.parse("2024-03-29"),
            dateFormat.parse("2024-04-01"),
            18.0,
            "John",
            EventStatus.Joined,
            true,
            "img_event_9",
            //R.drawable.img_event_9,
            listOf(1,3,4)
        ),
    )
}
*/