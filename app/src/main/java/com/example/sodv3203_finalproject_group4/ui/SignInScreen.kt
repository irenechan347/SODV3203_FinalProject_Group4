package com.example.sodv3203_finalproject_group4.ui

import androidx.compose.foundation.layout.*
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
import com.example.sodv3203_finalproject_group4.data.Datasource
@Composable
fun SignInScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") } // Not used for authentication in this example

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
                val user = Datasource.users.find { it.email == email }
                user?.let {
                    navController.navigate("home/${it.userId}")
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Sign In")
        }
    }
}

@Preview
@Composable
fun SignInScreenPreview() {
    val navController = rememberNavController()
    SignInScreen(navController = navController)
}
