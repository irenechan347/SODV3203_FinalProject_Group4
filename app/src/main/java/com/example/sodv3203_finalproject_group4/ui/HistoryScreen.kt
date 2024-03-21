package com.example.sodv3203_finalproject_group4.ui

import androidx.compose.foundation.Image
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sodv3203_finalproject_group4.data.Datasource
import com.example.sodv3203_finalproject_group4.data.Datasource.eventList
import com.example.sodv3203_finalproject_group4.model.Event
import com.example.sodv3203_finalproject_group4.model.EventStatus
import com.example.sodv3203_finalproject_group4.ui.theme.ShoppingBuddyAppTheme
import kotlin.math.ceil

@Composable
fun HistoryScreen() {
    val filteredEventList = eventList.filter { it.status != EventStatus.Available }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        HistoryList(filteredEventList)
    }
}

@Composable
fun HistoryList(eventList: List<Event>) {
    LazyColumn {
        items(eventList) { event ->
            HistoryListItem(event = event)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Divider(color = Color(0xFFFF4500), thickness = 1.dp)
            }
        }
    }
}

@Composable
fun HistoryListItem(event: Event) {
    val categoryName = Datasource.categoryMap[event.categoryId]

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = event.imageId),
                contentDescription = null,
                modifier = Modifier
                    .width(130.dp)
                    .height(80.dp)
                    .clip(shape = RoundedCornerShape(5.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Event by: ${event.eventBy}",
                modifier = Modifier.padding(5.dp)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier
            .weight(1f)
            .padding(5.dp)
        ) {
            Text(
                text = "$categoryName : ${event.productName}",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "$${event.price} ($${ceil(event.price/event.currHeadCount)} per Share)",
                modifier = Modifier.padding(bottom = 4.dp)
            )
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
            backgroundColor = Color(0xFFFFF4F0),
            contentColor = Color(0xFFFF4500),
            disabledBackgroundColor = Color.Transparent,
            disabledContentColor = Color(0xFFFF4500)
        )
        EventStatus.Joined -> ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color(0xFFFF4500),
            disabledBackgroundColor = Color.Transparent,
            disabledContentColor = Color(0xFFFF4500)
        )
        EventStatus.Paid -> ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFFFF4F0),
            contentColor = Color(0xFFFF4500),
            disabledBackgroundColor = Color.Transparent,
            disabledContentColor = Color(0xFFFF4500)
        )
        EventStatus.Completed -> ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFFF4500),
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
        HistoryScreen()
    }
}