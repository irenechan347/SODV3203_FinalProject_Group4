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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import kotlinx.coroutines.launch
import kotlin.math.ceil

@Composable
fun BookmarkScreen(
    navController: NavHostController,
    userId:Int,
    viewModel: EventViewModel
) {
    //val bookmarkedEventList = events.filter { it.isBookmark }
    val events by viewModel.getAllEvents().collectAsState(initial = emptyList())
    val bookmarkedEventList = events.filter { event -> event.isBookmark }
    val categories by viewModel.getAllEventCategories().collectAsState(initial = emptyList())
    val categoryMap = categories.associate { it.categoryId to it.categoryName }
    val users by viewModel.getAllUsers().collectAsState(initial = emptyList())

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        BookmarkList(bookmarkedEventList, navController, userId, viewModel, users, categoryMap)
    }
}

@Composable
fun BookmarkList(
    eventList: List<Event>,
    navController: NavHostController,
    userId:Int,
    viewModel: EventViewModel,
    users: List<User>,
    categoryMap: Map<Int, String>
) {
    LazyColumn {
        items(eventList) { event ->
            BookmarkListItem(event = event, navController, userId, viewModel, users, categoryMap)
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
fun BookmarkListItem(
    event: Event,
    navController: NavHostController,
    userId:Int,
    viewModel: EventViewModel,
    users: List<User>,
    categoryMap: Map<Int, String>
) {
    val categoryName = categoryMap[event.categoryId]
    var isBookmarked by remember { mutableStateOf(event.isBookmark) }

    val coroutineScope = rememberCoroutineScope()

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
                        text = "$${event.price} ($${ceil(event.price/event.currHeadCount)} per Share)",
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
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
fun BookmarkScreenPreview() {
    ShoppingBuddyAppTheme {
        val navController = rememberNavController()
        BookmarkScreen(navController, 2, viewModel = viewModel())
    }
}