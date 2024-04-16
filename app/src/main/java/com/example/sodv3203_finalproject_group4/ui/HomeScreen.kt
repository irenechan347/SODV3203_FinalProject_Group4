package com.example.sodv3203_finalproject_group4.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sodv3203_finalproject_group4.LoadImage
import com.example.sodv3203_finalproject_group4.model.Event
import com.example.sodv3203_finalproject_group4.model.EventCategory
import com.example.sodv3203_finalproject_group4.model.EventStatus
import com.example.sodv3203_finalproject_group4.ui.theme.ShoppingBuddyAppTheme
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavHostController, userId:Int, viewModel: EventViewModel) {
    val events by viewModel.getAllEvents().collectAsState(initial = emptyList())
    val eventCategories by viewModel.getAllEventCategories().collectAsState(initial = emptyList())
    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<EventCategory?>(null) }
    var sortByDate by remember { mutableStateOf(false) }
    /*val user = remember {
        users.find { it.userId == userId }
    }*/
    val filteredEvents = events.filter { event ->
        event.status == EventStatus.Available &&
                event.productName.contains(searchText, ignoreCase = true) &&
                (selectedCategory == null || event.categoryId == selectedCategory?.categoryId)
    }
    // Apply sorting if enabled
    val sortedEvents = if (sortByDate) {
        filteredEvents.sortedByDescending { it.dateFrom } // Sort events by dateFrom in descending order
    } else {
        filteredEvents // If sorting is not enabled, return the original list
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        // Display the greeting message at the top
        /*if (user != null) {
            Text(text = "Hi ${user.displayName}!", style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(16.dp))
        }*/

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            // "New Event" button
            IconButton(
                onClick = { navController.navigate("NewEvent/$userId") }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "New Event",
                    tint = Color.Gray
                )
            }
            // Search bar
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(10.dp, 16.dp),
                placeholder = {
                    Text(
                        text = "Search by product name",
                        style = TextStyle(color = Color.Gray)
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.secondaryVariant,
                    unfocusedBorderColor = Color.LightGray
                ),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.Gray
                    )
                }
            )
            Spacer(modifier = Modifier.width(16.dp))
        }

        // Filter buttons for each category
        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(eventCategories) { category ->
                Button(
                    onClick = { selectedCategory = if (selectedCategory == category) null else category },
                    modifier = Modifier.padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (selectedCategory == category) MaterialTheme.colors.secondaryVariant else Color.White,
                        contentColor = if (selectedCategory == category) Color.White else MaterialTheme.colors.secondaryVariant
                    )
                ) {
                    Text(text = category.categoryName)
                }
            }
        }

        // Sorting controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Sort by date:",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = { sortByDate = !sortByDate }
            ) {
                Icon(
                    imageVector = if (sortByDate) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = "Sort by date",
                    tint = MaterialTheme.colors.secondaryVariant
                )
            }
        }



        // Display filtered events
        LazyColumn(modifier = Modifier.padding(8.dp)) {
            items(sortedEvents) { event ->
                EventItem(event = event, navController, userId, viewModel)
            }
        }
    }
}

@Composable
fun EventItem(
    event: Event,
    navController: NavHostController,
    userId: Int,
    viewModel: EventViewModel
) {
    var isBookmarked by remember { mutableStateOf(event.isBookmark) }

    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {navController.navigate("eventScreen/$userId/${event.eventId}")},
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = LoadImage(event.imageName),
                contentDescription = "${event.productName} image",
                modifier = Modifier
                    .size(64.dp)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = event.productName,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Location: ${event.location}",
                    style = MaterialTheme.typography.body2,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Price: $${event.price}",
                    style = MaterialTheme.typography.body2,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        isBookmarked = !isBookmarked
                        event.isBookmark = !event.isBookmark

                        viewModel.updateEvent(event)
                    }
                }
            ) {
                Icon(
                    imageVector = if (isBookmarked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Bookmark",
                    tint = MaterialTheme.colors.secondaryVariant
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    ShoppingBuddyAppTheme {
        val navController = rememberNavController()
        HomeScreen(navController = navController, userId = 2, viewModel = viewModel() )
    }
}