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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sodv3203_finalproject_group4.LoadImage
import com.example.sodv3203_finalproject_group4.model.Event
import com.example.sodv3203_finalproject_group4.ui.theme.ShoppingBuddyAppTheme
import androidx.compose.material.Checkbox
import androidx.compose.material.primarySurface
import com.example.sodv3203_finalproject_group4.events
import com.example.sodv3203_finalproject_group4.model.EventStatus
import com.example.sodv3203_finalproject_group4.users

@Composable
fun HomeScreen(navController: NavHostController, userId:Int) {
    var searchText by remember { mutableStateOf("") }
    var showAvailableEvents by remember { mutableStateOf(false) }
    val user = remember {
        users.find { it.userId == userId }
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        // Display the greeting message at the top
        if (user != null) {
            Text(text = "Hi ${user.displayName}!", style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(16.dp))
        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(
                onClick = { navController.navigate("NewEvent/$userId") }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "New Event",
                    tint = Color.Gray
                )
            }
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = {
                    Text(
                        text = "Search by product name",
                        style = TextStyle(color = Color.Gray)
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Gray,
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
            // Checkbox for filtering available events
            Checkbox(
                checked = showAvailableEvents,
                onCheckedChange = { isChecked ->
                    showAvailableEvents = isChecked
                },
                modifier = Modifier.padding(end = 16.dp)
            )
            Text(
                text = "",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.clickable { showAvailableEvents = !showAvailableEvents }
            )
        }

        // Filtered events based on product name search and availability
        val filteredEvents = if (showAvailableEvents) {
            events.filter {
                it.status == EventStatus.Available &&
                        it.productName.contains(searchText, ignoreCase = true)
            }
        } else {
            events.filter {
                it.productName.contains(searchText, ignoreCase = true)
            }
        }

        // Display filtered events
        LazyColumn(modifier = Modifier.padding(8.dp)) {
            items(filteredEvents) { event ->
                EventItem(event = event, navController, userId)
            }
        }
    }
}

@Composable
fun EventItem(event: Event, navController: NavHostController, userId: Int) {
    var isBookmarked by remember { mutableStateOf(event.isBookmark) }

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
                    isBookmarked = !isBookmarked
                    event.isBookmark = !event.isBookmark
                }
            ) {
                Icon(
                    imageVector = if (isBookmarked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Bookmark",
                    tint = MaterialTheme.colors.secondaryVariant
                    //tint = Color(0xFFFF4500)
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
        HomeScreen(navController = navController, userId = 2)
    }
}