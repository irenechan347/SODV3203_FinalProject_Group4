package com.example.sodv3203_finalproject_group4.ui


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sodv3203_finalproject_group4.ui.theme.ShoppingBuddyAppTheme

@Composable
fun EventScreen(userId: Int, eventId: Int) {

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "User ID: $userId", style = MaterialTheme.typography.h6)
        Text(text = "Event ID: $eventId", style = MaterialTheme.typography.h6)
        // This is just to verify that now userId/eventId is available
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {}
}

@Preview
@Composable
fun EventScreenPreview() {
    ShoppingBuddyAppTheme {
        EventScreen( 2,2)
    }
}