package com.example.sodv3203_finalproject_group4.ui

import android.annotation.SuppressLint
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sodv3203_finalproject_group4.R
import com.example.sodv3203_finalproject_group4.ui.theme.ShoppingBuddyAppTheme
import java.util.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import coil.compose.rememberImagePainter
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import com.example.sodv3203_finalproject_group4.model.Event
import com.example.sodv3203_finalproject_group4.model.EventCategory
import com.example.sodv3203_finalproject_group4.model.EventStatus
import java.text.SimpleDateFormat
import kotlin.math.ceil
import android.util.Log
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sodv3203_finalproject_group4.LoadImage
import com.example.sodv3203_finalproject_group4.categories
import com.example.sodv3203_finalproject_group4.categoryMap
import com.example.sodv3203_finalproject_group4.data.EventDataSource
import com.example.sodv3203_finalproject_group4.events

@SuppressLint("UnrememberedMutableState")
@Composable
fun NewEventScreen(navController: NavHostController, userId: Int, eventId: Int = -1) {

    // Find the maximum event ID from the existing event list
    val maxEventId = events.maxOfOrNull { it.eventId } ?: 0

    // Increment the maximum event ID by 1 to generate a new event ID
    val newEventId = maxEventId + 1

    // Initialize firstSelectedDate to today's date
    val today = Date()
    val firstSelectedDate by remember { mutableStateOf(today) }

    // Initialize secondSelectedDate to firstSelectedDate + 3 days
    val calendar = Calendar.getInstance()
    calendar.time = firstSelectedDate
    calendar.add(Calendar.DAY_OF_YEAR, 3)
    val secondSelectedDate by remember { mutableStateOf(calendar.time) }

    var isPhotoUploaded by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<String?>(null) }

    // Define state variables for the number of people and the price
    var numberOfPeople by remember { mutableIntStateOf(1) }
    var price by remember { mutableDoubleStateOf(0.0) }

    // Calculate price per share based on the input values
    var pricePerShare by remember { mutableDoubleStateOf(0.0) }
    pricePerShare = price / numberOfPeople

// Define a variable to hold the selected category ID
    var selectedCategoryId by remember { mutableStateOf<Int?>(null) }
    var selectedCategory by remember { mutableStateOf<EventCategory?>(null) }

// Update selectedCategoryId and selectedCategory when a category is selected
    val onCategorySelected: (EventCategory?) -> Unit = { category ->
        selectedCategoryId =
            category?.categoryId // Update the selectedCategoryId with the categoryId of the selected category
        selectedCategory = category // Update the selectedCategory state with the selected category
    }

    // Define a state variable to hold the product name
    var productName by remember { mutableStateOf("") }

    // Define a mutable state variable to hold the location value
    var location by remember { mutableStateOf("") }

    // Define a mutable state for showDialog
    var showDialog by remember { mutableStateOf(false) }

    val fromEvent = remember {
        events.find { it.eventId == eventId }
    }

    val chooseImageLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
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
                    // Check if fromEvent is not null and if there's a selected image URI
                    val defaultImageId = fromEvent?.imageName

                    // Display the selected image or the upload icon
                    if (isPhotoUploaded || defaultImageId != null) {
                        val painter = if (isPhotoUploaded) {
                            // Display the selected image
                            rememberImagePainter(
                                data = selectedImageUri,
                                builder = {
                                    crossfade(true)
                                }
                            )
                        } else {
                            // Display the default image based on imageId
                            //painterResource(id = defaultImageId!!)
                            LoadImage(defaultImageId!!)
                        }
                        Image(
                            painter = painter,
                            contentDescription = "Uploaded Photo",
                            modifier = Modifier
                                .size(140.dp)
                                .padding(bottom = 8.dp)
                        )
                        /*
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
                         */
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
            if (fromEvent != null) {
                CategoryRow(
                    selectedCategory = categories.find { it.categoryId == fromEvent.categoryId },
                    onCategorySelected = onCategorySelected,
                    fromEvent = fromEvent,
                    selectedCategoryId = selectedCategoryId,
                    categoryMap = categoryMap
                )
            } else {
                CategoryRow(
                    selectedCategory = null, // Initial value when no category is selected
                    onCategorySelected = onCategorySelected,
                    fromEvent = null, // Pass null for fromEvent when it is not available
                    selectedCategoryId = selectedCategoryId,
                    categoryMap = categoryMap
                )
            }
        }


        // 3. Row with Product icon and input box for the productName
        item {
            if (fromEvent != null) {
                TextInputRow(
                    iconId = R.drawable.product,
                    hint = "Product Description",
                    initialText = fromEvent.productName ?: "",
                    onValueChange = { newValue ->
                        productName = newValue
                    }
                )
            } else {
                TextInputRow(
                    iconId = R.drawable.product,
                    hint = "Product Description",
                    onValueChange = { newValue ->
                        productName = newValue
                    }
                )
            }
        }

        // 4. Row with Location icon and input box for text
        item {
            if (fromEvent != null) {
                TextInputRow(
                    iconId = R.drawable.location,
                    hint = "Location",
                    initialText = fromEvent.location ?: ""
                ) { newValue ->
                    // Update the location value when it changes
                    location = newValue
                }
            } else {
                TextInputRow(
                    iconId = R.drawable.location,
                    hint = "Location"
                ) { newValue ->
                    // Update the location value when it changes
                    location = newValue
                }
            }
        }

        // 5. Row with People icon and input box for number
        item {
            PeopleInputRow(
                iconId = R.drawable.people,
                hint = "Number of People (2-4)",
                onNumberOfPeopleChange = { newValue ->
                    numberOfPeople = newValue
                    // Recalculate price per share when the number of people changes
                    pricePerShare = ceil(price / newValue * 10) / 10
                }
            )
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
                        text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                            firstSelectedDate
                        ),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // "To" text
                Text(text = "to", modifier = Modifier.align(Alignment.CenterVertically))

                Spacer(modifier = Modifier.width(8.dp))

                // Second date input field
                DateInputField(
                    selectedDate = mutableStateOf(secondSelectedDate),
                    modifier = Modifier.weight(1f)
                )
            }
        }


        // 7. Row with Money icon and input box for price
        item {
            PriceInputRow(
                iconId = R.drawable.dollar,
                hint = "Product Price",
                numberOfPeople = numberOfPeople,
            ) { newValue ->
                price = newValue
            }
        }

        // 8. Create button
        item {
            CreateButton(
                newEventId = newEventId,
                selectedCategoryId = selectedCategoryId,
                productName = productName,
                location = location,
                numberOfPeople = numberOfPeople,
                firstSelectedDate = firstSelectedDate,
                secondSelectedDate = secondSelectedDate,
                price = price,
                userId = userId,
                fromEvent = fromEvent,
                onEventCreated = { showDialog = true }
            )
            // Show dialog if showDialog is true
            if (showDialog) {
                ShowDataSentDialog(
                    navController = navController,
                    userId = userId,
                    onDismiss = {
                        showDialog = false
                    }
                )
            }
        }
    }
}

@Composable
fun ShowDataSentDialog(navController: NavHostController, userId: Int, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
            navController.navigate("HomeScreen/$userId")
        },
        title = { Text("Data Sent") },
        text = { Text("The data has been successfully sent to the database.") },
        confirmButton = {
            Button(
                onClick = {
                    onDismiss()
                    navController.navigate("home/$userId")
                }
            ) {
                Text("OK")
            }
        }
    )
}


@Composable
fun MissingFieldsDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Missing Fields") },
        text = { Text("Please fill in all required fields.") },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}


@Composable
fun CategoryRow(
    selectedCategory: EventCategory?,
    onCategorySelected: (EventCategory?) -> Unit,
    fromEvent: Event?,
    selectedCategoryId: Int?,
    categoryMap: Map<Int, String>
) {
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
                    text = selectedCategoryId?.let { categoryId ->
                        categoryMap[categoryId] ?: "Select Category"
                    } ?: "Select Category",
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
            categories.forEach { category ->
                DropdownMenuItem(onClick = {
                    onCategorySelected(category)
                    expanded = false
                }) {
                    //Text(text = stringResource(id = category.categoryName))
                    Text(text = category.categoryName)
                }
            }
        }
    }
}




@Composable
fun TextInputRow(
    iconId: Int,
    hint: String,
    initialText: String = "",
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
        var text by remember { mutableStateOf(TextFieldValue(initialText)) }

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
    onNumberOfPeopleChange: (Int) -> Unit
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
                errorMessage = if (intValue != null && intValue in 2..4) {
                    onNumberOfPeopleChange(intValue)
                    null // Clear error message if input is valid
                } else {
                    "Please enter a number between 2 and 4"
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
fun DateInputField(selectedDate: MutableState<Date>, modifier: Modifier = Modifier) {
    val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())

    OutlinedTextField(
        value = dateFormat.format(selectedDate.value),
        onValueChange = { /* No-op */ }, // Disable text input
        readOnly = true, // Make the text field read-only
        label = { /* Label text */ },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        modifier = modifier
    )

}


@Composable
fun PriceInputRow(
    iconId: Int,
    hint: String,
    numberOfPeople: Int, // Number of people input
    onValueChange: (Double) -> Unit // Callback for price value change
) {
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var pricePerShare by remember { mutableDoubleStateOf(0.0) } // Initialize pricePerShare

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

        // Input box for price
        var text by remember { mutableStateOf(TextFieldValue()) }

        OutlinedTextField(
            value = text,
            onValueChange = {
                val newValue = it.text
                text = it
                val doubleValue = newValue.toDoubleOrNull()
                if (doubleValue != null) {
                    pricePerShare = ceil(doubleValue / numberOfPeople * 10) / 10 // Update pricePerShare based on the number of people
                    onValueChange(doubleValue)
                    errorMessage = null // Clear error message if input is valid
                } else {
                    errorMessage = "Please enter a valid price"
                }
            },
            placeholder = { Text(text = hint) },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Text field to display price per share
        OutlinedTextField(
            value = "($${String.format("%.1f", pricePerShare)} per share)",
            onValueChange = {},
            readOnly = true,
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
fun CreateButton(
    newEventId: Int,
    selectedCategoryId: Int?,
    productName: String,
    location: String,
    numberOfPeople: Int,
    firstSelectedDate: Date,
    secondSelectedDate: Date,
    price: Double,
    userId: Int,
    fromEvent: Event?,
    onEventCreated: () -> Unit
) {
    // Format the dates to YYYYMMDD format
    val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
    val formattedFirstDate = dateFormat.format(firstSelectedDate)
    val formattedSecondDate = dateFormat.format(secondSelectedDate)
    var showDialog by remember { mutableStateOf(false) }

    Button(onClick = {
        Log.d("CreateButton", "Button clicked")
        Log.d("CreateButton", "newEventId: $newEventId")
        Log.d("CreateButton", "selectedCategoryId: $selectedCategoryId")
        Log.d("CreateButton", "productName: $productName")
        Log.d("CreateButton", "location: $location")
        Log.d("CreateButton", "numberOfPeople: $numberOfPeople")
        Log.d("CreateButton", "firstSelectedDate: $formattedFirstDate")
        Log.d("CreateButton", "secondSelectedDate: $formattedSecondDate")
        Log.d("CreateButton", "price: $price")
        Log.d("CreateButton", "userId: $userId")
        Log.d("CreateButton", "fromEvent: $fromEvent")

        // Check if selectedCategoryId is not null and other required fields are not empty
        if (selectedCategoryId != null && productName.isNotBlank() && location.isNotBlank() && numberOfPeople>0 && price>0) {
            // Create the event
            val newEvent = Event(
                eventId = newEventId,
                categoryId = selectedCategoryId!!,
                productName = productName,
                location = location,
                currHeadCount = numberOfPeople,
                dateFrom = dateFormat.parse(formattedFirstDate),
                dateTo = dateFormat.parse(formattedSecondDate),
                price = price,
                eventBy = userId.toString(),
                status = EventStatus.Available, // Default status
                isBookmark = false, // Default value
                imageName = fromEvent?.imageName
                    ?: "img_event_1" // Use the imageId from fromEvent if available, otherwise use the default image
            )

            // Add the new event to the datasource
            EventDataSource.addEvent(newEvent)

            // Call the callback function to notify that event is created
            onEventCreated()
        } else {
            // Log a message or show an error indicating that required fields are missing
            showDialog = true
            Log.d("CreateButton", "Some required fields are missing.")
        }
    }) {
        Text(text = "Create", modifier = Modifier.padding(horizontal = 16.dp))
    }

    if (showDialog) {
        MissingFieldsDialog(onDismiss = { showDialog = false })
    }
}




@Preview
@Composable
fun NewEventScreenPreview() {
    val navController = rememberNavController()
    ShoppingBuddyAppTheme {
        NewEventScreen(navController = navController, userId = 2, eventId = -1)
    }
}

