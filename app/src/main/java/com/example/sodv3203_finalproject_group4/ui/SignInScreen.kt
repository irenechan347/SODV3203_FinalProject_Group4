package com.example.sodv3203_finalproject_group4.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sodv3203_finalproject_group4.model.User
import com.example.sodv3203_finalproject_group4.util.UserSessionManager
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    navController: NavHostController,
    viewModel: EventViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
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
                    //val user = users.find { it.email == email }

                    var user: User? = null

                    //Log.d("SignInScreen", "1: "+user.toString())
                    viewModel.getUserByEmail(email).collect { collectedUser ->
                        user = collectedUser

                        //og.d("SignInScreen", "2: "+user.toString())
                        user?.let {
                            if (user != null && it.password == password) {
                                //Log.d("SignInScreen", "3: "+user.toString())
                                UserSessionManager.login(it.userId)
                                navController.navigate("home/${it.userId}")
                            } else {
                                showDialog = true
                                dialogMessage = "User Email or Password is wrong"
                            }
                        } ?: run {
                            showDialog = true
                            dialogMessage = "User not found"
                        }
                        // Return the user directly from the collect block using a label
                        //return@collect
                    }

                    /*
                    coroutineScope.launch {
                        viewModel.addAllEvents(events)
                        viewModel.addAllEventCategories(categories)
                        viewModel.addAllUsers(users)
                    }
                     */
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Sign In")
        }

        // Button to navigate to SignUp page
        Button(
            onClick = {
                navController.navigate("signUp")
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Sign Up")
        }
        
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Error") },
                text = { Text(dialogMessage) },
                confirmButton = {
                    Button(
                        onClick = { showDialog = false }
                    ) {
                        Text("OK")
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun SignInScreenPreview() {
    val navController = rememberNavController()
    SignInScreen(navController = navController, viewModel = viewModel())
}
