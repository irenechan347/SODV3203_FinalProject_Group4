package com.example.sodv3203_finalproject_group4.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sodv3203_finalproject_group4.users
import com.example.sodv3203_finalproject_group4.util.UserSessionManager

@Composable
fun SignInScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

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
                val user = users.find { it.email == email }
                user?.let {
                    if (it.password == password) {
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
    SignInScreen(navController = navController)
}
