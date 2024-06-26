package com.example.sodv3203_finalproject_group4.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sodv3203_finalproject_group4.LoadImage
import com.example.sodv3203_finalproject_group4.model.Event
import com.example.sodv3203_finalproject_group4.model.EventStatus
import com.example.sodv3203_finalproject_group4.model.User
import com.example.sodv3203_finalproject_group4.ui.theme.ShoppingBuddyAppTheme

@Composable
fun HistoryScreen(navController: NavHostController, userId:Int, viewModel: EventViewModel) {
    //val filteredEventList = events.filter { it.status != EventStatus.Available  && it.joinedUsers.contains(userId) }
    val events by viewModel.getAllEvents().collectAsState(initial = emptyList())
    val filteredEventList = events.filter { event ->
        event.status != EventStatus.Available && event.joinedUsers.contains(userId)
    }
    val categories by viewModel.getAllEventCategories().collectAsState(initial = emptyList())
    val categoryMap = categories.associate { it.categoryId to it.categoryName }
    val users by viewModel.getAllUsers().collectAsState(initial = emptyList())

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        HistoryList(filteredEventList, navController, userId, users, categoryMap)
    }
}

@Composable
fun HistoryList(
    eventList: List<Event>,
    navController: NavHostController,
    userId:Int,
    users: List<User>,
    categoryMap: Map<Int, String>
) {
    LazyColumn {
        items(eventList) { event ->
            HistoryListItem(event = event, navController, userId, users, categoryMap)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Divider(
                    color = MaterialTheme.colors.secondaryVariant,
                    thickness = 1.dp
                )
            }
        }
    }
}

@Composable
fun HistoryListItem(
    event: Event,
    navController: NavHostController,
    userId:Int,
    users: List<User>,
    categoryMap: Map<Int, String>
) {
    val categoryName = categoryMap[event.categoryId]

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                // Navigate to EventScreen with parameters
                navController.navigate("eventScreen/$userId/${event.eventId}")
            }
    ) {
        Column {
            Image(
                painter = LoadImage(event.imageName),
                contentDescription = null,
                modifier = Modifier
                    .width(130.dp)
                    .height(80.dp)
                    .clip(shape = RoundedCornerShape(5.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Event by: ${(users.find { it.userId == event.eventBy.toInt() })?.displayName}",
                modifier = Modifier.padding(5.dp)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier
            .weight(1f)
            .padding(5.dp)
        ) {
            Row {
                Column (modifier = Modifier.width(180.dp)) {
                    Text(
                        text = "$categoryName : ${event.productName}",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "$${event.price} ($${String.format("%.1f", event.price / event.currHeadCount)} per share)",
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = { navController.navigate("NewEvent/$userId/${event.eventId}") }
                ) {
                    androidx.compose.material.Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Copy Event",
                        tint = MaterialTheme.colors.secondaryVariant
                        //tint = Color(0xFFFF4500)
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { /* TODO */ },
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonColors(event.status),
                    modifier = Modifier
                        .width(130.dp)
                        .padding(top = 10.dp)
                ) {
                    Text(text = event.status.name)
                }
            }
        }
    }
}

@Composable
private fun ButtonColors(status: EventStatus): ButtonColors {
    return when (status) {
        EventStatus.Available -> ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.secondaryVariant,
            disabledBackgroundColor = Color.Transparent,
            disabledContentColor = MaterialTheme.colors.secondaryVariant
        )
        EventStatus.Joined -> ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = MaterialTheme.colors.secondaryVariant,
            disabledBackgroundColor = Color.Transparent,
            disabledContentColor = MaterialTheme.colors.secondaryVariant
        )
        EventStatus.Paid -> ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.secondaryVariant,
            disabledBackgroundColor = Color.Transparent,
            disabledContentColor = MaterialTheme.colors.secondaryVariant
        )
        EventStatus.Completed -> ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondaryVariant,
            contentColor = Color.White,
            disabledBackgroundColor = Color.Transparent,
            disabledContentColor = Color.White
        )
        EventStatus.Cancelled -> ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF9095A0),
            contentColor = Color.White,
            disabledBackgroundColor = Color.Transparent,
            disabledContentColor = Color.White
        )
        else -> ButtonDefaults.buttonColors()
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    ShoppingBuddyAppTheme {
        val navController = rememberNavController()
        HistoryScreen(navController, 2, viewModel = viewModel())
    }
}