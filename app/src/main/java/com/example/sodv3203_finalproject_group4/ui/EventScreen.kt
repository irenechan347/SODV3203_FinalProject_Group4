package com.example.sodv3203_finalproject_group4.ui


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sodv3203_finalproject_group4.LoadImage
import com.example.sodv3203_finalproject_group4.R
import com.example.sodv3203_finalproject_group4.model.EventStatus
import com.example.sodv3203_finalproject_group4.ui.theme.ShoppingBuddyAppTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("UnrememberedMutableState")
@Composable
fun EventScreen(navController: NavHostController, userId: Int, eventId: Int, viewModel: EventViewModel) {
    val events by viewModel.getAllEvents().collectAsState(initial = emptyList())
    val categories by viewModel.getAllEventCategories().collectAsState(initial = emptyList())
    val categoryMap = categories.associate { it.categoryId to it.categoryName }
    val users by viewModel.getAllUsers().collectAsState(initial = emptyList())

    //val event = remember { events.firstOrNull { it.eventId == eventId } }
    val event = events.firstOrNull() { event -> event.eventId == eventId }

    val user = remember { users.firstOrNull { it.userId == userId } }
    var showDialog by remember { mutableStateOf(false) }
    val eventStatus = remember { mutableStateOf(EventStatus.Available) }
    var expanded by remember { mutableStateOf(false) }
    var selectedStatus by remember { mutableStateOf(EventStatus.Available) }

    val coroutineScope = rememberCoroutineScope()

    if (event != null) {
        val joinedUsers =
            event.joinedUsers.mapNotNull { userId -> users.firstOrNull { it.userId == userId } }
        val isUserJoined = event.joinedUsers.contains(userId)

        // Define updatedEvent based on whether the user is already joined or not
        val updatedEvent = if (!isUserJoined) {
            event.copy(joinedUsers = event.joinedUsers + userId)
        } else {
            event // No need to update if user is already joined
        }

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)) {
            item {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .clickable(enabled = false) { },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = LoadImage(event.imageName),
                        contentDescription = "Event Image",
                        modifier = Modifier
                            .size(140.dp)
                    )
                }
            }

            // Display event by information
            item {
                Text(
                    text = "Event by: ${(users.find { it.userId == event.eventBy.toInt() })?.displayName}",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            item {
                Box(
                    modifier = Modifier
                        .clickable(enabled = false) { },
                ) {
                    CategoryRow(
                        selectedCategory = categories.find { it.categoryId == event.categoryId },
                        onCategorySelected = { /* Not applicable for EventScreen */ },
                        fromEvent = event,
                        selectedCategoryId = event.categoryId,
                        categoryMap = categoryMap,
                        categories = categories
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .clickable(enabled = false) { },
                ) {
                    TextInputRow(
                        iconId = R.drawable.product,
                        hint = "Product Description",
                        initialText = event.productName ?: ""
                    ) { /* Not applicable for EventScreen */ }
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .clickable(enabled = false) { },
                ) {
                    TextInputRow(
                        iconId = R.drawable.location,
                        hint = "Location",
                        initialText = event.location ?: ""
                    ) { /* Not applicable for EventScreen */ }
                }
            }

            // Display NO. of People
            item {
                Text(
                    text = "Number of People: ${event.currHeadCount}",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Display price
            item {
                Text(
                    text = "$${event.price} ($${String.format("%.1f", event.price / event.currHeadCount)} per share)",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "From: ${
                            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                                event.dateFrom
                            )
                        }",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "To: ${
                            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                                event.dateTo
                            )
                        }",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Display joined users
            item {
                Text(
                    text = "Joined Users:",
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            joinedUsers.forEach { user ->
                item {
                    val fontWeight =
                        if (user.userId == userId) FontWeight.Bold else FontWeight.Normal
                    Text(
                        text = "- ${user.displayName}    ${user.phoneNo}",
                        style = MaterialTheme.typography.body1.copy(fontWeight = fontWeight),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }

            // Display event status
            item {
                Text(
                    text = "Status: ${event.status}",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            // Join button
            if (event.status == EventStatus.Available && !isUserJoined && joinedUsers.size < event.currHeadCount) {
                item {
                    Button(
                        onClick = {
                            // Update the event with the new list of joined users and updated status
                            //EventDataSource.updateEventList(updatedEvent)
                            coroutineScope.launch {
                                showDialog = true
                                val updatedEvent = event.copy(
                                    joinedUsers = event.joinedUsers + userId,
                                    status = if (event.joinedUsers.size + 1 == event.currHeadCount) {
                                        EventStatus.Joined
                                    } else {
                                        event.status
                                    }
                                )

                                viewModel.updateEvent(updatedEvent)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Join")
                    }
                }
            }

            //if (EventDataSource.isEventOwner(userId, eventId)&& event.status == EventStatus.Available) {
            if ((event?.eventBy?.toIntOrNull() == userId) && event.status == EventStatus.Available) {
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { expanded = true },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Change Event Status")
                        }
                        if (expanded) {
                            Column {
                                Text(text = "Please pick an option:")
                                EventStatus.values()
                                    .filter{it!=EventStatus.Joined}
                                    .forEach { status ->
                                    Button(
                                        onClick = {
                                            coroutineScope.launch {
                                                selectedStatus = status
                                                expanded = false
                                                // Update the event status here only if the selected status is not "Available"
                                                if (selectedStatus != EventStatus.Available) {
                                                    //EventDataSource.updateEventStatus(eventId, selectedStatus)
                                                    val updatedEvent = event.copy(
                                                        status = selectedStatus
                                                    )
                                                    viewModel.updateEvent(updatedEvent)
                                                    // Navigate to the history page
                                                    navController.navigate("history/$userId")
                                                }
                                            }
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(status.name)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Success") },
            text = { Text(text = "The event is joined.") },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        // navController.navigate("home/$userId")
                        navController.popBackStack()
                    }
                ) {
                    Text(text = "OK")
                }
            }
        )
    }
}




@Preview
@Composable
fun EventScreenPreview() {
    ShoppingBuddyAppTheme {
        val navController = rememberNavController()
        EventScreen(navController,2, 4, viewModel = viewModel())
    }
}