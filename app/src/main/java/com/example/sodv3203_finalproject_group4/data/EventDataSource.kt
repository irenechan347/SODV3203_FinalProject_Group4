package com.example.sodv3203_finalproject_group4.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.sodv3203_finalproject_group4.events
import com.example.sodv3203_finalproject_group4.model.Event
import com.example.sodv3203_finalproject_group4.model.EventCategory
import com.example.sodv3203_finalproject_group4.model.User
import com.example.sodv3203_finalproject_group4.users
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


object EventDataSource {
    private const val EVENTS_JSON_FILENAME = "events.json"
    private const val CATEGORIES_JSON_FILENAME = "categories.json"
    private const val USERS_JSON_FILENAME = "users.json"

    fun updateUser(userId: Int, updatedUser: User) {
        // Find the index of the user with the given userId
        val index = users.indexOfFirst { it.userId == userId }
        if (index != -1) {
            // Update the user data at the found index
            users[index] = updatedUser
        }
    }

    fun addEvent(event: Event) {
        events.add(event)
    }

    fun updateEventList(updatedEvent: Event) {
        val index = events.indexOfFirst { it.eventId == updatedEvent.eventId }
        if (index != -1) {
            events[index] = updatedEvent
        }
    }

    fun loadEvents(context: Context): List<Event> {
        val jsonString = loadJsonFromAsset(context, EVENTS_JSON_FILENAME)
        val eventType = object : TypeToken<List<Event>>() {}.type
        val events = Gson().fromJson<List<Event>>(jsonString, eventType)
        //return Gson().fromJson(jsonString, eventType)
        return events.map { it.copy(imageName = context.resources.getIdentifier(it.imageName, "drawable", context.packageName).toString()) }
    }

    fun loadCategories(context: Context): List<EventCategory> {
        val jsonString = loadJsonFromAsset(context, CATEGORIES_JSON_FILENAME)
        val categoryType = object : TypeToken<List<EventCategory>>() {}.type
        return Gson().fromJson(jsonString, categoryType)
    }

    fun loadUsers(context: Context): List<User> {
        val jsonString = loadJsonFromAsset(context, USERS_JSON_FILENAME)
        val userType = object : TypeToken<List<User>>() {}.type
        return Gson().fromJson(jsonString, userType)
    }

    private fun loadJsonFromAsset(context: Context, fileName: String): String {
        return try {
            Log.d("EventDataSource", "load from path: " + context.assets.open(fileName))
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            ""
        }
    }

    fun saveEvents(context: Context, events: List<Event>) {
        // Update image names to image IDs
        val eventsWithImageIds = events.map { it.copy(imageName = context.resources.getIdentifier(it.imageName, "drawable", context.packageName).toString()) }
        saveJsonToFile(context, EVENTS_JSON_FILENAME, eventsWithImageIds)
        //saveJsonToFile(context, EVENTS_JSON_FILENAME, events)
    }

    fun saveCategories(context: Context, categories: List<EventCategory>) {
        saveJsonToFile(context, CATEGORIES_JSON_FILENAME, categories)
    }

    fun saveUsers(context: Context, users: List<User>) {
        saveJsonToFile(context, USERS_JSON_FILENAME, users)
    }

    private fun saveJsonToFile(context: Context, fileName: String, data: Any) {
        val json = Gson().toJson(data)
        //FileWriter(context.filesDir.resolve(fileName)).use { it.write(json) }
        /*
        val fileWriter: FileWriter = FileWriter(context.filesDir.resolve(fileName))
        val bufferedWriter = BufferedWriter(fileWriter)
        bufferedWriter.write(json)
        bufferedWriter.close()
         */
        /*
        try {
            Log.d("EventDataSource", "save to path: " + context.filesDir.resolve(fileName))
            val stream: FileOutputStream = FileOutputStream(context.filesDir.resolve(fileName), true)
            stream.write(json.toString().toByteArray())
            stream.write("\n".toByteArray())
            stream.close()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
         */
        val file = File(context.filesDir, EVENTS_JSON_FILENAME)
        file.writeText(json)
        Log.d("EventDataSource", "Saved to path: ${file.absolutePath}")
    }
}