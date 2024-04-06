package com.example.sodv3203_finalproject_group4.data

import android.content.Context
import android.util.Log

interface AppContainer {
    val shoppingBuddyRepository: ShoppingBuddyRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val shoppingBuddyRepository: ShoppingBuddyRepository by lazy {
        val db = ShoppingBuddyDatabase.getDatabase(context)
        Log.d("AppDataContainer", db.toString())
        OfflineShoppingBuddyRepository(db.eventDao(), db.eventCategoryDao(), db.userDao())
    }
}