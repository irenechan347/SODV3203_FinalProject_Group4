package com.example.sodv3203_finalproject_group4

import EventScreen
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sodv3203_finalproject_group4.ui.EventViewModel
import com.example.sodv3203_finalproject_group4.ui.theme.ShoppingBuddyAppTheme
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.sodv3203_finalproject_group4.ui.BookmarkScreen
import com.example.sodv3203_finalproject_group4.ui.HistoryScreen
import com.example.sodv3203_finalproject_group4.ui.HomeScreen
import com.example.sodv3203_finalproject_group4.ui.NewEventScreen
import com.example.sodv3203_finalproject_group4.ui.ProfileScreen

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
    onTabSelected: (ShoppingBuddyScreen) -> Unit
) {
    BottomNavigation(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Color.White,
        elevation = 8.dp
    ) {
        BottomNavigationItem(
            selected = selectedTab == ShoppingBuddyScreen.Home,
            onClick = { onTabSelected(ShoppingBuddyScreen.Home) },
            icon = { IconWithText(Icons.Default.Home, stringResource(id = R.string.home)) }
        )
        BottomNavigationItem(
            selected = selectedTab == ShoppingBuddyScreen.History,
            onClick = { onTabSelected(ShoppingBuddyScreen.History) },
            icon = { IconWithText(Icons.Default.List, stringResource(id = R.string.history)) }
        )
        BottomNavigationItem(
            selected = selectedTab == ShoppingBuddyScreen.Bookmark,
            onClick = { onTabSelected(ShoppingBuddyScreen.Bookmark) },
            icon = { IconWithText(Icons.Default.FavoriteBorder, stringResource(id = R.string.bookmark)) }
        )
        BottomNavigationItem(
            selected = selectedTab == ShoppingBuddyScreen.Profile,
            onClick = { onTabSelected(ShoppingBuddyScreen.Profile) },
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

@Composable
fun ShoppingBuddyApp(
    viewModel: EventViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = ShoppingBuddyScreen.valueOf(
        backStackEntry?.destination?.route ?: ShoppingBuddyScreen.Home.name
    )
    //val (selectedMenuItem, setSelectedMenuItem) = remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            ShoppingBuddyAppBar(
                currentScreen = currentScreen,
                //canNavigateBack = navController.previousBackStackEntry != null,
                canNavigateBack = false,
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            MyBottomNavigationBar(
                selectedTab = currentScreen,
                onTabSelected = { screen ->
                    navController.navigate(screen.name)
                }
            )
        }
    ) { innerPadding ->
        //val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = ShoppingBuddyScreen.Home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = ShoppingBuddyScreen.Home.name) {
                // val context = LocalContext.current
                HomeScreen()
            }

            composable(route = ShoppingBuddyScreen.NewEvent.name) {
                NewEventScreen()
            }

            composable(route = ShoppingBuddyScreen.Event.name) {
                EventScreen()
            }

            composable(route = ShoppingBuddyScreen.History.name) {
                HistoryScreen()
            }

            composable(route = ShoppingBuddyScreen.Bookmark.name) {
                BookmarkScreen()
            }

            composable(route = ShoppingBuddyScreen.Profile.name) {
                ProfileScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingBuddyAppPreview2() {
    ShoppingBuddyAppTheme {
        ShoppingBuddyApp()
    }
}