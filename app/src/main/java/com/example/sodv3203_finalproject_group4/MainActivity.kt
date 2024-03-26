package com.example.sodv3203_finalproject_group4

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sodv3203_finalproject_group4.data.EventDataSource
import com.example.sodv3203_finalproject_group4.model.Event
import com.example.sodv3203_finalproject_group4.model.EventCategory
import com.example.sodv3203_finalproject_group4.model.User
import com.example.sodv3203_finalproject_group4.ui.theme.ShoppingBuddyAppTheme

// Define the global variables at the top-level of your file or in an object
lateinit var events: MutableList<Event>
lateinit var categories: List<EventCategory>
lateinit var users: MutableList<User>
lateinit var categoryMap: Map<Int, String>

// Load events, categories, and users when your application starts, for example, in your Application class or the entry point of your app.
fun loadEventData(context: Context) {
    events = EventDataSource.loadEvents(context).toMutableList()
    categories = EventDataSource.loadCategories(context)
    users = EventDataSource.loadUsers(context).toMutableList()
    categoryMap = categories.associate { it.categoryId to it.categoryName }
}

class MainActivity : ComponentActivity() {

    private val WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", ""+applicationContext)
        loadEventData(applicationContext)
        setContent {
            val isSystemInDarkMode = isSystemInDarkTheme()
            ShoppingBuddyAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShoppingBuddyApp()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingBuddyAppPreview() {
    ShoppingBuddyAppTheme {
        ShoppingBuddyApp()
    }
}