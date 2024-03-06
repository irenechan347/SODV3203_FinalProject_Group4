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
fun HistoryScreen() {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = ShoppingBuddyScreen.History.name
        )
    }
}

@Preview
@Composable
fun HistoryScreenPreview() {
    ShoppingBuddyAppTheme {
        HistoryScreen()
    }
}