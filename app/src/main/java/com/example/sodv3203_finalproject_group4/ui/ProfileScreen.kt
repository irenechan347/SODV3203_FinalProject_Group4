package com.example.sodv3203_finalproject_group4.ui

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sodv3203_finalproject_group4.R
import android.content.Context
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sodv3203_finalproject_group4.ui.theme.ShoppingBuddyAppTheme
import com.example.sodv3203_finalproject_group4.util.UserSessionManager

@Composable
fun ProfileScreen(navController: NavController, userId:Int) {
    var username by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }

    /*
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {*/
    Box(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Group 4 and Email (Centered)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Group 4
                Text(
                    text = "Oliver",
                    style = MaterialTheme.typography.h5
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Email
                Text(
                    text = "oliver@mybvc.ca",
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Divider line
            Divider(
                //color = Color.Black,
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Edit Profile
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start // Aligns children horizontally to the start (left)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.profileicon),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Edit Profile",
                    style = MaterialTheme.typography.button,
                    //color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Last Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = bio,
                    onValueChange = { bio = it },
                    label = { Text("Bio") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        // Perform action on button click
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Save")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            /*// Night Mode Toggle
            val isNightMode = remember { mutableStateOf(false) }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.nightmodeicon),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Night Mode",
                    style = MaterialTheme.typography.button,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = isNightMode.value,
                    onCheckedChange = { isChecked ->
                        // Handle night mode toggle
                        isNightMode.value = isChecked
                    }
                )
            }*/

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    UserSessionManager.logout()
                    navController.navigate("signIn") {
                        popUpTo("signIn") { inclusive = true } // Replace "signIn" with your actual start destination ID
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Logout")
            }
        }
    }
}
/*}*/
@Composable
fun ProfileInformationField(label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.width(120.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        TextField(
            value = value,
            onValueChange = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Preview
@Composable
fun ProfileScreenPreview() {
    ShoppingBuddyAppTheme {
        ProfileScreen(navController = rememberNavController(), 2)
    }
}