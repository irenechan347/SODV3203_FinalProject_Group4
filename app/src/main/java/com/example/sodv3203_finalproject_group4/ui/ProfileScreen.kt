package com.example.sodv3203_finalproject_group4.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sodv3203_finalproject_group4.R
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sodv3203_finalproject_group4.data.EventDataSource
import com.example.sodv3203_finalproject_group4.model.User
import com.example.sodv3203_finalproject_group4.ui.theme.ShoppingBuddyAppTheme
import com.example.sodv3203_finalproject_group4.users
import com.example.sodv3203_finalproject_group4.util.UserSessionManager

@Composable
fun ProfileScreen(navController: NavController, userId:Int) {
    var users = users.toMutableList()
    val user = users.find { it.userId == userId } ?: User(-1, "", "", "", "") // Default user if not found
    //var user = Datasource.getUser(userId)
    var displayName by remember { mutableStateOf(user.displayName) }
    var name by remember { mutableStateOf(user.name) }
    //var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf(user.email) }
    var phoneNo  by remember { mutableStateOf(user.phoneNo) }
    var userIndex = users.indexOfFirst { it.userId == userId }


    /*
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {*/
    Box(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Display user's display name
            Text(
                text = user.displayName,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 16.dp),
                color = MaterialTheme.colors.background
            )

            // Group 4 and Email (Centered)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(4.dp))
                // Email
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.background
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Divider line
            Divider(
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                color = MaterialTheme.colors.background
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
                    modifier = Modifier.size(24.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Edit Profile",
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.background
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = displayName,
                    onValueChange = { displayName = it },
                    label = { Text("Display Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = phoneNo,
                    onValueChange = { phoneNo = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (userIndex != -1) { // Ensure user exists in the list
                            users[userIndex] = user.copy(
                                displayName = displayName,
                                name = name,
                                email = email,
                                phoneNo = phoneNo
                            )
                            // Notify DataSource about the update
                            EventDataSource.updateUser(user.userId, users[userIndex]) // Update the user in the Datasource
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Save")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))


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
        ProfileScreen(navController = rememberNavController(), 1)
    }
}