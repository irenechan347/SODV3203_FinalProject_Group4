package com.example.sodv3203_finalproject_group4.ui


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sodv3203_finalproject_group4.R
import com.example.sodv3203_finalproject_group4.data.Datasource
import com.example.sodv3203_finalproject_group4.ui.theme.ShoppingBuddyAppTheme
import java.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("UnrememberedMutableState")
@Composable
fun EventScreen(userId: Int, eventId: Int) {
    val event = remember { Datasource.eventList.firstOrNull { it.eventId == eventId } }

    if (event != null) {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(10.dp)) {
            item {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .padding(bottom = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = event.imageId),
                        contentDescription = "Event Image",
                        modifier = Modifier
                            .size(140.dp)
                            .padding(bottom = 8.dp)
                    )
                }
            }
            item {
                CategoryRow(
                    selectedCategory = Datasource.categoryList.find { it.categoryId == event.categoryId },
                    onCategorySelected = { /* Not applicable for EventScreen */ },
                    fromEvent = event,
                    selectedCategoryId = event.categoryId,
                    categoryMap = Datasource.categoryMap
                )
            }
            item {
                TextInputRow(
                    iconId = R.drawable.product,
                    hint = "Product Description",
                    initialText = event.productName ?: ""
                ) { /* Not applicable for EventScreen */ }
            }
            item {
                TextInputRow(
                    iconId = R.drawable.location,
                    hint = "Location",
                    initialText = event.location ?: ""
                ) { /* Not applicable for EventScreen */ }
            }
            item {
                Text(
                    text = "Number of People: ${event.currHeadCount}",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "From: ${SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(event.dateFrom)}",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "to: ${SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(event.dateTo)}",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            item {
                Text(
                    text = "Price per share: $${String.format("%.1f", event.price / event.currHeadCount)}",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            item {
                Button(
                    onClick = { /* Functionality to join the event */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Join")
                }
            }
        }
    } else {
        Text(text = "Event not found", modifier = Modifier.padding(16.dp))
    }
}


@Preview
@Composable
fun EventScreenPreview() {
    ShoppingBuddyAppTheme {
        EventScreen(2, 2,)
    }
}