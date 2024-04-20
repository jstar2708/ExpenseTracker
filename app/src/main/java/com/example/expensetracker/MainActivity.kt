package com.example.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.common.AppComponents
import com.example.expensetracker.common.Routes
import com.example.expensetracker.presentation.screens.category_screen.CategoryScreen
import com.example.expensetracker.presentation.screens.home_screen.HomeScreen
import com.example.expensetracker.presentation.screens.settings.SettingsScreen
import com.example.expensetracker.presentation.screens.transaction_screen.TransactionScreen
import com.example.expensetracker.presentation.screens.transaction_screen.addtransactionscreen.AddTransaction
import com.example.expensetracker.presentation.theme.md_theme_light_primary
import com.example.expensetracker.presentation.theme.md_theme_light_primaryContainer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val navController = rememberNavController()
    val bottomBarState = remember {
        mutableStateOf(true)
    }
    Scaffold(modifier = Modifier.fillMaxWidth(), bottomBar = {
        BottomNavigation(navController, bottomBarState)
    }) {
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(it)
        ) {
            composable(Routes.HOME) {
                bottomBarState.value = true
                HomeScreen(navController)
            }
            composable(Routes.TRANSACTIONS) {
                bottomBarState.value = true
                TransactionScreen(navController)
            }
            composable(Routes.CATEGORY) {
                bottomBarState.value = true
                CategoryScreen(navController)
            }
            composable(Routes.SETTINGS) {
                bottomBarState.value = true
                SettingsScreen(navController)
            }
            composable(Routes.ADD_TRANSACTIONS) {
                bottomBarState.value = false
                AddTransaction(navController)
            }
        }
    }
}

@Composable
fun BottomNavigation(navController: NavHostController, bottomBarState: MutableState<Boolean>) {
    AnimatedVisibility(visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            NavigationBar(
                modifier = Modifier.padding(8.dp).clip(RoundedCornerShape(8.dp)),
                containerColor = md_theme_light_primaryContainer
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                AppComponents.list.forEachIndexed { index, bottomNavigationItem ->

                    NavigationBarItem(selected = currentDestination?.hierarchy?.any { it.route == bottomNavigationItem.route } == true,

                        onClick = {
                            navController.navigate(bottomNavigationItem.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },

                        label = {
                            if (currentDestination?.hierarchy?.any { it.route == bottomNavigationItem.route } == true) {
                                Text(text = bottomNavigationItem.title)
                            }
                        },

                        icon = {
                            if (currentDestination?.hierarchy?.any { it.route == bottomNavigationItem.route } == true) {
                                Icon(
                                    imageVector = bottomNavigationItem.selectedIcon,
                                    contentDescription = bottomNavigationItem.title,
                                    tint = md_theme_light_primary
                                )
                            } else {
                                Icon(
                                    imageVector = bottomNavigationItem.unselectedIcon,
                                    contentDescription = bottomNavigationItem.title
                                )
                            }
                        })
                }
            }

        });
}
