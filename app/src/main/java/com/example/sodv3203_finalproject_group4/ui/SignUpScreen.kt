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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sodv3203_finalproject_group4.R
import com.example.sodv3203_finalproject_group4.data.EventDataSource
import com.example.sodv3203_finalproject_group4.model.User
import com.example.sodv3203_finalproject_group4.ui.theme.ShoppingBuddyAppTheme
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*

@Composable
fun SignUpScreen(navController: NavController) {
    var displayName by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {

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
                text = "Create Profile",
                style = MaterialTheme.typography.button
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
                label = { Text("Name") },
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
                value = phoneNo,
                onValueChange = { phoneNo = it },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val newUser = User(
                        userId = 0, // Assign a temporary ID
                        displayName = displayName,
                        name = name,
                        email = email,
                        phoneNo = phoneNo,
                        password = password
                    )
                    EventDataSource.addUser(newUser) // Add the user to the data source
                    showDialog = true
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Submit")
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text(text = "Success") },
                    text = { Text(text = "Your profile has been created.") },
                    confirmButton = {
                        Button(
                            onClick = { navController.navigate("home") },
                        ) {
                            Text(text = "OK")
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    ShoppingBuddyAppTheme {
        SignUpScreen(navController = rememberNavController())
    }
}
