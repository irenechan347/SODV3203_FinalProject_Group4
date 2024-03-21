package com.example.sodv3203_finalproject_group4.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sodv3203_finalproject_group4.ShoppingBuddyScreen
import com.example.sodv3203_finalproject_group4.ui.theme.ShoppingBuddyAppTheme

@Composable
<<<<<<< HEAD
fun EventScreen() {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
=======
fun EventScreen(userId: Int, eventId: Int) {
    var searchText by remember { mutableStateOf(TextFieldValue()) }
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "User ID: $userId", style = MaterialTheme.typography.h6)
        Text(text = "Event ID: $eventId", style = MaterialTheme.typography.h6)
        // This is just to verify that now userId/eventId is available
    }

    Column(
>>>>>>> dd590a253b07d491ecb2ac15542f4c9073aea9d6
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = ShoppingBuddyScreen.Event.name
        )
    }
}

@Preview
@Composable
fun EventScreenPreview() {
    ShoppingBuddyAppTheme {
        EventScreen( 2,2)
    }
}