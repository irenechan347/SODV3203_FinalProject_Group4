package com.example.sodv3203_finalproject_group4.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sodv3203_finalproject_group4.model.User
import com.example.sodv3203_finalproject_group4.ui.theme.ShoppingBuddyAppTheme
import com.example.sodv3203_finalproject_group4.users
import com.example.sodv3203_finalproject_group4.util.UserSessionManager
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: EventViewModel,
    userId: Int
) {
    var users = users.toMutableList()
    val user = users.find { it.userId == userId } ?: User(-1, "", "", "", "", "")
    var displayName by remember { mutableStateOf(user.displayName) }
    var name by remember { mutableStateOf(user.name) }
    var email by remember { mutableStateOf(user.email) }
    var phoneNo by remember { mutableStateOf(user.phoneNo) }
    var password by remember { mutableStateOf(user.password) }
    var showDialog by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

            Box(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Display user's display name
                Text(
                    text = "${user.displayName}",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Email
                Text(
                    text = "${user.email}",
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Divider line
                Divider(
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Edit Profile
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
                            // Launch a coroutine to update user info
                            coroutineScope.launch {
                                // Update user data via ViewModel
                                var user: User? = null

                                viewModel.updateUser(
                                    userId = userId,
                                    displayName = displayName,
                                    name = name,
                                    email = email,
                                    phoneNo = phoneNo,
                                    password = password
                                )
                                showDialog = true // Show success dialog
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Save")
                    }

                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            title = { Text(text = "Success") },
                            text = { Text(text = "The profile is updated.") },
                            confirmButton = {
                                Button(
                                    onClick = { showDialog = false },
                                ) {
                                    Text(text = "OK")
                                }
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))


                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        UserSessionManager.logout()
                        navController.navigate("signIn") {
                            popUpTo("signIn") { inclusive = true }
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
    fun ProfileScreen(navController: NavHostController, viewModel: EventViewModel) {
        ShoppingBuddyAppTheme {
            // Inject EventViewModel into ProfileScreen
            ProfileScreen(navController = navController, viewModel = viewModel)
        }
    }

@Preview
@Composable
fun ProfileScreenPreview() {
        ShoppingBuddyAppTheme {
            ProfileScreen(navController = rememberNavController(), viewModel = viewModel())
        }
    }


