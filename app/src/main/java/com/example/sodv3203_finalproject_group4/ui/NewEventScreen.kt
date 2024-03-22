package com.example.sodv3203_finalproject_group4.ui

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import coil.compose.rememberImagePainter
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import java.text.SimpleDateFormat
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.AnnotatedString



private val LocalFocusManager = staticCompositionLocalOf<FocusManager?> { null }

@SuppressLint("UnrememberedMutableState")
@Composable
<<<<<<< Updated upstream
fun NewEventScreen(userId: Int, eventId: Int = -1) {
    var firstSelectedDate by remember { mutableStateOf(Calendar.getInstance()) }
    var secondSelectedDate by remember { mutableStateOf(Calendar.getInstance()) }
=======
fun NewEventScreen(userId: Int) {

    // Initialize firstSelectedDate to today's date
    val today = Date()
    val firstSelectedDate by remember { mutableStateOf(today) }

    // Initialize secondSelectedDate to firstSelectedDate + 3 days
    val calendar = Calendar.getInstance()
    calendar.time = firstSelectedDate
    calendar.add(Calendar.DAY_OF_YEAR, 3)
    var secondSelectedDate by remember { mutableStateOf(calendar.time) }

>>>>>>> Stashed changes
    var isPhotoUploaded by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<String?>(null) }

    val fromEvent = remember {
        Datasource.eventList.find { it.eventId == eventId }
    }

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
                                    crossfade(true)
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
                        // Display the placeholder image
                        Image(
                            painter = painterResource(id = R.drawable.img_event_1),
                            contentDescription = "Placeholder Image",
                            modifier = Modifier
                                .size(140.dp)
                                .fillMaxSize()
                                .padding(bottom = 8.dp)
                        )
                        // Display the upload icon
//                        Text(
//                            text = buildAnnotatedString {
//                                withStyle(style = SpanStyle(fontSize = 12.sp)) {
//                                    append("Click to\n")
//                                }
//                                withStyle(style = SpanStyle(fontSize = 12.sp)) {
//                                    append("upload photo")
//                                }
//                            },
//                            color = Color.Gray
//                        )
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
            TextInputRow(iconId = R.drawable.product, hint = "Product Description")
            { newValue ->
                // Handle value change
            }
        }

        // 4. Row with Location icon and input box for text
        item {
            TextInputRow(iconId = R.drawable.location, hint = "Location")
            { newValue ->
                // Handle value change
            }
        }

        // 5. Row with People icon and input box for number
        item {
            PeopleInputRow(iconId = R.drawable.people, hint = "Number of People (1-5)")
            { newValue ->
                // Handle value change
            }
        }

        // 6. Row with Calendar icon and date inputs
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                // Calendar icon
                Image(
                    painter = painterResource(id = R.drawable.calendar),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )

                Spacer(modifier = Modifier.width(8.dp))

                // First date input field (non-editable)
                Box(
                    modifier = Modifier
                        .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.medium)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable { /* Empty onClick lambda to prevent interaction */ }
                        .weight(1f)
                ) {
                    // Display the selected date
                    Text(
                        text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(firstSelectedDate),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // "To" text
                Text(text = "to", modifier = Modifier.align(Alignment.CenterVertically))

                Spacer(modifier = Modifier.width(8.dp))

                // Second date input field
                DateInputField(selectedDate = mutableStateOf(secondSelectedDate), modifier = Modifier.weight(1f))
            }
        }


        // 7. Row with Money icon and input box for price
        item {
            PriceInputRow(iconId = R.drawable.dollar, hint = "Price per Person")
            { newValue ->
                // Handle value change
            }
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
fun TextInputRow(
    iconId: Int,
    hint: String,
    maxInputLength: Int = 30, // Maximum input length
    onValueChange: (String) -> Unit // Callback for value change
) {
    var errorMessage by remember { mutableStateOf<String?>(null) }

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
        var text by remember { mutableStateOf(TextFieldValue()) }

        OutlinedTextField(
            value = text,
            onValueChange = {
                val newValue = it.text
                text = it

                // Check if input length exceeds the maximum limit
                if (newValue.length > maxInputLength) {
                    errorMessage = "Maximum input length is $maxInputLength"
                } else {
                    errorMessage = null
                    onValueChange(newValue)
                }
            },
            placeholder = { Text(text = hint) },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true,
            modifier = Modifier.weight(1f)
        )
    }

    // Display error message if present
    errorMessage?.let { message ->
        Snackbar(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            content = { Text(text = message) },
            action = {
                TextButton(onClick = { errorMessage = null }) {
                    Text(text = "Dismiss")
                }
            }
        )
    }
}



@Composable
fun PeopleInputRow(
    iconId: Int,
    hint: String,
    onValueChange: (Int) -> Unit // Callback for integer value change
) {
    var errorMessage by remember { mutableStateOf<String?>(null) }

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
        var text by remember { mutableStateOf(TextFieldValue()) }

        OutlinedTextField(
            value = text,
            onValueChange = {
                val newValue = it.text
                text = it
                val intValue = newValue.toIntOrNull()
                if (intValue != null && intValue in 1..5) {
                    onValueChange(intValue)
                    errorMessage = null // Clear error message if input is valid
                } else {
                    errorMessage = "Please enter a number between 1 and 5"
                }
            },
            placeholder = { Text(text = hint) },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f)
        )
    }

    // Display error message if present
    errorMessage?.let { message ->
        Snackbar(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            content = { Text(text = message) },
            action = {
                TextButton(onClick = { errorMessage = null }) {
                    Text(text = "Dismiss")
                }
            }
        )
    }
}

@Composable
fun MyComposable() {
    val selectedDate = remember { mutableStateOf(Date()) }

    DateInputField(selectedDate = selectedDate)
}

@Composable
fun DateInputField(selectedDate: MutableState<Date>, modifier: Modifier = Modifier) {
    val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())

    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = dateFormat.format(selectedDate.value),
        onValueChange = { newValue ->
            // Parse the input text to a Date object
            val parsedDate = parseDate(newValue)
            parsedDate?.let {
                selectedDate.value = it
            }
        },
        label = { /* Label text */ },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                // Hide the keyboard when done
                focusManager?.clearFocus()
            }
        ),
        modifier = modifier
    )
}

private fun parseDate(input: String): Date? {
    return try {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        dateFormat.isLenient = false // Ensure strict parsing
        dateFormat.parse(input)
    } catch (e: Exception) {
        null
    }
}


private fun isValidDateFormat(date: String): Boolean {
    return date.matches(Regex("\\d{4}/\\d{2}/\\d{2}"))
}





@Composable
fun PriceInputRow(
    iconId: Int,
    hint: String,
    onValueChange: (Double) -> Unit // Callback for price value change
) {
    // Implementation for input row specifically for prices
}
@Preview
@Composable
fun NewEventScreenPreview() {
    ShoppingBuddyAppTheme {
        NewEventScreen(userId = 2)
    }
}
