package com.example.sodv3203_finalproject_group4.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.sodv3203_finalproject_group4.model.Event
import com.example.sodv3203_finalproject_group4.model.EventCategory
import com.example.sodv3203_finalproject_group4.model.User
import com.example.sodv3203_finalproject_group4.util.DateConverter
import com.example.sodv3203_finalproject_group4.util.ListConverter

@Database(entities = [Event::class, EventCategory::class, User::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, ListConverter::class)
abstract class ShoppingBuddyDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
    abstract fun eventCategoryDao(): EventCategoryDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var Instance: ShoppingBuddyDatabase? = null

        fun getDatabase(context: Context): ShoppingBuddyDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ShoppingBuddyDatabase::class.java, "shopping_buddy_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}