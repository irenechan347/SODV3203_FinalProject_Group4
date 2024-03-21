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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sodv3203_finalproject_group4.ShoppingBuddyScreen
import com.example.sodv3203_finalproject_group4.data.Datasource
import com.example.sodv3203_finalproject_group4.model.Event
import com.example.sodv3203_finalproject_group4.ui.theme.ShoppingBuddyAppTheme

@Composable
fun HomeScreen(navController: NavHostController, userId:Int) {
    var searchText by remember { mutableStateOf("") }
    val user = remember {
        Datasource.users.find { it.userId == userId }
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
        }

        val filteredEvents = Datasource.eventList.filter {
            it.productName.contains(searchText, ignoreCase = true)
        }

        LazyColumn(modifier = Modifier.padding(8.dp)) {
            items(filteredEvents) { event ->
                EventItem(event = event)
            }
        }
    }
}

@Composable
fun EventItem(event: Event) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { /* Handle click on event item */ },
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = event.imageId),
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