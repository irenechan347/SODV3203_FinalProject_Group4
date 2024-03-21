import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sodv3203_finalproject_group4.R
import com.example.sodv3203_finalproject_group4.data.Datasource
import com.example.sodv3203_finalproject_group4.model.Event
import com.example.sodv3203_finalproject_group4.model.EventCategory
import com.example.sodv3203_finalproject_group4.ui.theme.ShoppingBuddyAppTheme
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable


@Composable
fun EventScreen(userId: Int, eventId: Int) {
    var searchText by remember { mutableStateOf(TextFieldValue()) }
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "User ID: $userId", style = MaterialTheme.typography.h6)
        Text(text = "Event ID: $eventId", style = MaterialTheme.typography.h6)
        // This is just to verify that now userId/eventId is available
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Plus button for creating a new event
            IconButton(onClick = { /* Navigate to NewEventScreen */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus_circle),
                    contentDescription = "Create new event"
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            // Search bar for filtering events by category
            BasicTextField(
                value = searchText,
                onValueChange = { searchText = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                textStyle = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        // Category placeholder text
                        Text(text = "Category", color = Color.Gray)
                        innerTextField()
                    }
                }
            )
        }

        // Display cards for each event category
        Datasource.categoryList.forEach { category ->
            val matchingEvents = Datasource.eventList.filter { it.categoryId == category.categoryId }
            if (matchingEvents.isNotEmpty() || searchText.text.isBlank()) {
                if (matchingEvents.isNotEmpty()) {
                    EventCategoryRow(category, matchingEvents)
                }
            }
        }
    }
}

@Composable
fun EventCategoryRow(category: EventCategory, events: List<Event>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            // Category name in bold
            Text(
                text = stringResource(id = category.categoryName),
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Event photos row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val visibleEvents = events.take(3)

                if (events.size > 3) {
                    IconButton(
                        onClick = { /* Navigate to previous images */ },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_left),
                            contentDescription = "Previous Images"
                        )
                    }
                }

                visibleEvents.forEach { event ->
                    Image(
                        painter = painterResource(id = event.imageId),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                }

                if (events.size > 3) {
                    IconButton(
                        onClick = { /* Navigate to next images */ },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_right),
                            contentDescription = "Next Images"
                        )
                    }
                }
            }
        }
    }
}






@Preview(showBackground = true)
@Composable
fun EventScreenPreview() {
    ShoppingBuddyAppTheme {
        EventScreen( 2,2)
    }
}
