package com.example.sodv3203_finalproject_group4.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sodv3203_finalproject_group4.R
import com.example.sodv3203_finalproject_group4.data.Datasource
import com.example.sodv3203_finalproject_group4.ui.theme.ShoppingBuddyAppTheme
import java.util.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.text.SpanStyle
import androidx.compose.material.Text
import coil.compose.rememberImagePainter


@Composable
fun NewEventScreen(userId: Int) {
    var firstSelectedDate by remember { mutableStateOf(Calendar.getInstance()) }
    var secondSelectedDate by remember { mutableStateOf(Calendar.getInstance()) }
    var isPhotoUploaded by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<String?>(null) }

    val chooseImageLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            // Handle the selected image URI
            selectedImageUri = it.toString()
            isPhotoUploaded = true
        }
    }
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "User ID: $userId", style = MaterialTheme.typography.h6)
        // This is just to verify that now userId is available
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        item {
            // Display the uploaded photo or the placeholder text
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .padding(bottom = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.medium)
                        .clickable {
                            // Open file picker when the box is clicked
                            chooseImageLauncher.launch("image/*")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    // Display the selected image or the upload icon
                    if (isPhotoUploaded) {
                        selectedImageUri?.let {
                            // Display the selected image
                            val painter = rememberImagePainter(
                                data = it,
                                builder = {
                                    crossfade(true) // Optional: enable crossfade animation
                                }
                            )
                            Image(
                                painter = painter,
                                contentDescription = "Uploaded Photo",
                                modifier = Modifier
                                    .size(140.dp)
                                    .padding(bottom = 8.dp)
                            )
                        }
                    } else {
                        // Display the upload icon
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontSize = 12.sp)) {
                                    append("Click to\n")
                                }
                                withStyle(style = SpanStyle(fontSize = 12.sp)) {
                                    append("upload photo")
                                }
                            },
                            color = Color.Gray
                        )
                    }
                }
            }
        }




        // 2. Row with Category Icon and a pull-down menu to select Category Name
        item {
            CategoryRow()
        }

        // 3. Row with Product icon and input box for text
        item {
            InputRow(iconId = R.drawable.product, hint = "Product Name")
        }

        // 4. Row with Location icon and input box for text
        item {
            InputRow(iconId = R.drawable.location, hint = "Location")
        }

        // 5. Row with People icon and input box for number
        item {
            InputRow(iconId = R.drawable.people, hint = "Number of People")
        }

        // 6. Row with Calendar icon and date pickers
        item {
            DateRangePickerRow(
                firstSelectedDate = firstSelectedDate,
                onFirstDateSelected = { firstSelectedDate = it },
                secondSelectedDate = secondSelectedDate,
                onSecondDateSelected = { secondSelectedDate = it }
            )
        }

        // 7. Row with Money icon and input box for price
        item {
            InputRow(iconId = R.drawable.dollar, hint = "Price per Person", isPriceInput = true)
        }

        // 8. Create button
        item {
            Button(onClick = { /* Handle create button click */ }) {
                Text(text = "Create", modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}

@Composable
fun CategoryRow() {
    var selectedCategory by remember { mutableStateOf(Datasource.categoryList[0]) }
    var expanded by remember { mutableStateOf(false) }

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
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Box to hold the dropdown menu
        Box(
            modifier = Modifier
                .weight(1f) // Occupy the whole available width
                .clickable { expanded = !expanded } // Toggle expanded state
                .border(1.dp, Color.Gray, MaterialTheme.shapes.medium) // Add border
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Display selected category name
                Text(
                    text = stringResource(id = selectedCategory.categoryName),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.weight(1f)
                )

                // Dropdown arrow icon
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // DropdownMenu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(120.dp) // Adjust width as needed
        ) {
            Datasource.categoryList.forEach { category ->
                DropdownMenuItem(onClick = {
                    selectedCategory = category
                    expanded = false
                }) {
                    Text(text = stringResource(id = category.categoryName))
                }
            }
        }
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

@Composable
fun DateRangePickerRow(
    firstSelectedDate: Calendar,
    onFirstDateSelected: (Calendar) -> Unit,
    secondSelectedDate: Calendar,
    onSecondDateSelected: (Calendar) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        // Calendar icon
        Image(
            painter = painterResource(id = R.drawable.calendar),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )

        Spacer(modifier = Modifier.width(8.dp))

        // First date picker
        DatePicker(
            selectedDate = firstSelectedDate,
            onDateChange = onFirstDateSelected,
            modifier = Modifier.weight(1f)
        )

        // "To" text
        Text(text = "to", modifier = Modifier.padding(horizontal = 8.dp))

        // Second date picker
        DatePicker(
            selectedDate = secondSelectedDate,
            onDateChange = onSecondDateSelected,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun DatePicker(
    selectedDate: Calendar,
    onDateChange: (Calendar) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = selectedDate.time.toString(),
        onValueChange = { },
        readOnly = true,
        label = { Text("Date") },
        modifier = modifier
    )



    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            onSelectDate = {
                onDateChange(it)
                showDialog = false
            }
        )
    }
}


@Composable
fun DatePickerDialog(
    onDismissRequest: () -> Unit,
    onSelectDate: (Calendar) -> Unit
) {
    var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = {
                    onSelectDate(selectedDate)
                    onDismissRequest()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text("Cancel")
            }
        },
        title = {
            Text("Select Date")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DatePicker(
                    selectedDate = selectedDate,
                    onDateChange = { selectedDate = it }
                )
            }
        }
    )
}

@Preview
@Composable
fun PreviewDatePicker() {
    val selectedDate = remember { Calendar.getInstance() }
    DatePicker(selectedDate = selectedDate, onDateChange = {})
}

@Preview
@Composable
fun NewEventScreenPreview() {
    ShoppingBuddyAppTheme {
        NewEventScreen(userId = 2)
    }
}
