package com.example.sodv3203_finalproject_group4

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sodv3203_finalproject_group4.ui.AppViewModelProvider
import com.example.sodv3203_finalproject_group4.ui.BookmarkScreen
import com.example.sodv3203_finalproject_group4.ui.EventScreen
import com.example.sodv3203_finalproject_group4.ui.EventViewModel
import com.example.sodv3203_finalproject_group4.ui.HistoryScreen
import com.example.sodv3203_finalproject_group4.ui.HomeScreen
import com.example.sodv3203_finalproject_group4.ui.NewEventScreen
import com.example.sodv3203_finalproject_group4.ui.ProfileScreen
import com.example.sodv3203_finalproject_group4.ui.SignInScreen
import com.example.sodv3203_finalproject_group4.ui.SignUpScreen
import com.example.sodv3203_finalproject_group4.ui.theme.ShoppingBuddyAppTheme
import com.example.sodv3203_finalproject_group4.util.UserSessionManager

enum class ShoppingBuddyScreen(@StringRes val title: Int) {
    Home(title = R.string.app_name),
    NewEvent(title = R.string.new_event),
    Event(title = R.string.event),
    History(title = R.string.history),
    Bookmark(title = R.string.bookmark),
    Profile(title = R.string.profile)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingBuddyAppBar(
    currentScreen: ShoppingBuddyScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
   TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.image_size))
                        .padding(dimensionResource(R.dimen.padding_small)),
                    painter = painterResource(R.drawable.shopping_buddy_icon),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.weight(0.5f))
                Text(
                    text = stringResource(currentScreen.title),
                    style = MaterialTheme.typography.displayLarge
                )
                Spacer(modifier = Modifier.weight(1f))
            }
         },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun MyBottomNavigationBar(
    selectedTab: ShoppingBuddyScreen,
    onTabSelected: (ShoppingBuddyScreen) -> Unit,
    navController: NavHostController
) {
    BottomNavigation(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colorScheme.surface,
        elevation = 8.dp
    ) {
        BottomNavigationItem(
            selected = selectedTab == ShoppingBuddyScreen.Home,
            onClick = {
                UserSessionManager.getCurrentUserId()?.let { userId ->
                    navController.navigate("home/$userId")
                } ?: run {
                    navController.navigate("signIn")
                }
            },
            icon = { IconWithText(Icons.Default.Home, stringResource(id = R.string.home)) }
        )
        BottomNavigationItem(
            selected = selectedTab == ShoppingBuddyScreen.History,
            onClick = {
                UserSessionManager.getCurrentUserId()?.let { userId ->
                    navController.navigate("${ShoppingBuddyScreen.History.name}/$userId")
                } ?: run {
                    navController.navigate("signIn")
                }
            },
            icon = { IconWithText(Icons.Default.List, stringResource(id = R.string.history)) }
        )
        BottomNavigationItem(
            selected = selectedTab == ShoppingBuddyScreen.Bookmark,
            onClick = {
                UserSessionManager.getCurrentUserId()?.let { userId ->
                    navController.navigate("${ShoppingBuddyScreen.Bookmark.name}/$userId")
                } ?: run {
                    navController.navigate("signIn")
                }
            },
            icon = { IconWithText(Icons.Default.FavoriteBorder, stringResource(id = R.string.bookmark)) }
        )
        BottomNavigationItem(
            selected = selectedTab == ShoppingBuddyScreen.Profile,
            onClick = {
                UserSessionManager.getCurrentUserId()?.let { userId ->
                    navController.navigate("${ShoppingBuddyScreen.Profile.name}/$userId")
                } ?: run {
                    navController.navigate("signIn")
                }
            },
            icon = { IconWithText(Icons.Default.AccountCircle, stringResource(id = R.string.profile)) }
        )
    }
}

@Composable
fun IconWithText(icon: ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
        Text(label)
    }
}

fun getShoppingBuddyScreenByRoute(route: String?): ShoppingBuddyScreen {
    return try {
        if (route != null) ShoppingBuddyScreen.valueOf(route) else ShoppingBuddyScreen.Home
    } catch (e: IllegalArgumentException) {
        ShoppingBuddyScreen.Home
    }
}
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ShoppingBuddyApp(
    navController: NavHostController = rememberNavController(),
    eventViewModel: EventViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    // Initialize EventViewModel
    val context = LocalContext.current
    //val eventViewModel: EventViewModel = EventViewModel(context)

    // Collect the event UI state using State hoisting
    val eventUiState by eventViewModel.eventUiState.collectAsState()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = getShoppingBuddyScreenByRoute(
        backStackEntry?.destination?.route)

    /*
    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        eventViewModel.addAllEventCategories(categories)
    }
    */

    Scaffold(
        topBar = {
            ShoppingBuddyAppBar(
                currentScreen = currentScreen,
                canNavigateBack = false,
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            MyBottomNavigationBar(
                selectedTab = currentScreen,
                onTabSelected = { screen ->
                    navController.navigate(screen.name){

                    }
                },
                navController = navController
            )
        },
        containerColor = MaterialTheme.colorScheme.onSecondary
    ) { innerPadding ->

        // Display loading indicator if data is being fetched
        if (eventUiState.isLoading) {
            CircularProgressIndicator()
        } else {
            // Display events or error message based on the UI state
            if (eventUiState.error != null) {
                Text(text = eventUiState.error!!)
            } else {
                // Display the app content
                NavHost(
                    navController = navController,
                    startDestination = "signIn",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(route = "signIn") {
                        SignInScreen(navController = navController, viewModel = eventViewModel)
                    }

                    composable("signUp") {
                        SignUpScreen(navController = navController,viewModel = eventViewModel)
                    }

                    composable(route = "home/{userId}") { backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userId")
                        userId?.let {
                            HomeScreen(navController = navController, userId = it.toInt(),viewModel = eventViewModel)
                        }
                    }

                    composable(route = "NewEvent/{userId}"){backStackEntry ->
                        backStackEntry.arguments?.getString("userId")?.toIntOrNull()?.let { userId -> NewEventScreen(navController = navController, viewModel = eventViewModel, userId = userId) }

                    }

                    composable(route = "NewEvent/{userId}/{eventId}"){backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: throw IllegalArgumentException("User ID not found")
                        val eventId = backStackEntry.arguments?.getString("eventId")?.toInt() ?: throw IllegalArgumentException("Event ID not found")
                        NewEventScreen(userId = userId, navController = navController, viewModel = eventViewModel, eventId = eventId)
                    }

                    composable("eventScreen/{userId}/{eventId}") { backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: throw IllegalArgumentException("User ID not found")
                        val eventId = backStackEntry.arguments?.getString("eventId")?.toInt() ?: throw IllegalArgumentException("Event ID not found")
                        EventScreen(userId = userId, navController = navController, eventId = eventId, viewModel = eventViewModel)
                    }

                    composable(route = "${ShoppingBuddyScreen.History.name}/{userId}") {backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: throw IllegalArgumentException("User ID not found")
                        HistoryScreen(navController = navController, viewModel = eventViewModel, userId = userId)
                    }

                    composable(route = "${ShoppingBuddyScreen.Bookmark.name}/{userId}") {backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: throw IllegalArgumentException("User ID not found")
                        BookmarkScreen(navController = navController, viewModel = eventViewModel, userId = userId)
                    }

                    composable(route = "${ShoppingBuddyScreen.Profile.name}/{userId}") {backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: throw IllegalArgumentException("User ID not found")
                        ProfileScreen(navController = navController, viewModel = eventViewModel, userId = userId)
                    }
                }
            }
        }
    }
}

@Composable
fun LoadImage(imageName: String?, modifier: Modifier = Modifier, contentDescription: String? = null) : Painter {
    if (imageName != null) {
        val context = LocalContext.current
        val resourceId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
        return painterResource(id = resourceId)
    }
    return painterResource(R.drawable.placeholder_image)
}

@Preview(showBackground = true)
@Composable
fun ShoppingBuddyAppPreview2() {
    ShoppingBuddyAppTheme {
        ShoppingBuddyApp()
    }
}