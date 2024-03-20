package com.example.sodv3203_finalproject_group4.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sodv3203_finalproject_group4.R
import com.example.sodv3203_finalproject_group4.data.Datasource
import com.example.sodv3203_finalproject_group4.ui.theme.ShoppingBuddyAppTheme

@Composable
fun NewEventScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 1. Display a blank photo frame with specific size, allow users to upload an image
        Box(
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            // Display blank photo frame here
            Image(
                painter = painterResource(id = R.drawable.placeholder_image),
                contentDescription = null,
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Crop
            )
        }

        // 2. Row with Category Icon and a pull-down menu to select Category Name
        CategoryRow()

        // 3. Row with Product icon and input box for text
        InputRow(iconId = R.drawable.product, hint = "Product Name")

        // 4. Row with Location icon and input box for text
        InputRow(iconId = R.drawable.location, hint = "Location")

        // 5. Row with People icon and input box for number
        InputRow(iconId = R.drawable.people, hint = "Number of People")

        // 6. Row with Calendar icon and date pickers
        InputRow(iconId = R.drawable.calendar, hint = "Select Dates")

        // 7. Row with Money icon and input box for price
        InputRow(iconId = R.drawable.dollar, hint = "Price per Person", isPriceInput = true)

        // 8. Create button
        Button(onClick = { /* Handle create button click */ }) {
            Text(text = "Create", modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}

@Composable
fun CategoryRow() {
    var selectedCategory by remember { mutableStateOf(Datasource.categoryList[0]) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        // Category Icon
        Image(
            painter = painterResource(id = R.drawable.category),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Dropdown menu for selecting category
        DropdownMenu(
            expanded = false, // Change to true to show dropdown menu
            onDismissRequest = { /* Handle dropdown menu dismiss */ },
            modifier = Modifier.wrapContentSize()
        ) {
            Datasource.categoryList.forEach { category ->
                DropdownMenuItem(onClick = { selectedCategory = category }) {
                    Text(text = stringResource(id = category.categoryName))
                }
            }
        }

        // Display selected category name
        Text(
            text = stringResource(id = selectedCategory.categoryName),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun InputRow(iconId: Int, hint: String, isPriceInput: Boolean = false) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        // Icon
        Image(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Input field
        OutlinedTextField(
            value = "",
            onValueChange = { /* Handle input change */ },
            placeholder = { Text(text = hint) },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true,
            modifier = Modifier.weight(1f)
        )

        // Optional currency label for price input
        if (isPriceInput) {
            Text(text = "$", style = MaterialTheme.typography.body1)
        }
    }
}

@Preview
@Composable
fun NewEventScreenPreview() {
    ShoppingBuddyAppTheme {
        NewEventScreen()
    }
}
