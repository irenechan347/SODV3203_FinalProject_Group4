package com.example.sodv3203_finalproject_group4.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sodv3203_finalproject_group4.R
import com.example.sodv3203_finalproject_group4.model.User
import com.example.sodv3203_finalproject_group4.ui.theme.ShoppingBuddyAppTheme
import com.example.sodv3203_finalproject_group4.util.UserSessionManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(navController: NavController, viewModel: EventViewModel) {
    var displayName by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showProfileCreatedDialog by remember { mutableStateOf(false) }
    var showEmailExistDialog by remember { mutableStateOf(false) }
    var userCreatedSuccessfully by remember { mutableStateOf(false) }
    val users by viewModel.getAllUsers().collectAsState(initial = emptyList())

    val coroutineScope = rememberCoroutineScope()

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
                    coroutineScope.launch {
                        try {
                            val newUser = User(
                                displayName = displayName,
                                name = name,
                                email = email,
                                phoneNo = phoneNo,
                                password = password
                            )
                            val userId = viewModel.insertUser(newUser)
                            if (userId != -1L) {
                                showProfileCreatedDialog = true
                            } else {
                                // Handle the case where user insertion failed due to conflict
                                showEmailExistDialog = true
                            }
                        } catch (e: Exception) {
                            // Handle the case where user insertion failed due to email conflict
                            showEmailExistDialog = true
                        }
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Submit")
            }

            Button(
                onClick = {
                    navController.navigate("signIn")
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Cancel")
            }

            val userToShow = users.find { it.email == email }
            if (showProfileCreatedDialog) {
                AlertDialog(
                    onDismissRequest = { showProfileCreatedDialog = false },
                    title = { Text(text = "Success") },
                    text = { Text(text = "Your profile has been created.") },
                    confirmButton = {
                        Button(
                            onClick = {
                                showProfileCreatedDialog = false
                                coroutineScope.launch {
                                    // Call getUserByEmail within a coroutine
                                    val user = viewModel.getUserByEmail(email).firstOrNull()
                                    val userId = user?.userId ?: -1
                                    if (userId != -1) {
                                        UserSessionManager.login(userId)
                                        navController.navigate("home/$userId")
                                    } else {
                                        // Handle error case where userId is not found
                                    }
                                }
                            }
                        ) {
                            Text(text = "OK")
                        }
                    }
                )
            }

            if (showEmailExistDialog) {
                AlertDialog(
                    onDismissRequest = { showEmailExistDialog = false },
                    title = { Text(text = "Error") },
                    text = { Text(text = "User email is already registered.") },
                    confirmButton = {
                        Button(
                            onClick = { showEmailExistDialog = false }
                        ) {
                            Text("OK")
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
        SignUpScreen(navController = rememberNavController(), viewModel = viewModel())
    }
}
