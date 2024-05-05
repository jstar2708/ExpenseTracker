package com.jaideep.expensetracker.presentation.screens.bottom

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jaideep.expensetracker.common.AppComponents
import com.jaideep.expensetracker.presentation.navigation.BottomNavigationGraph

@Composable
fun BottomNavigationScreen(navHostControllerRoot: NavHostController) {
    val navController: NavHostController = rememberNavController()
    Scaffold(modifier = Modifier.fillMaxWidth(), bottomBar = {
        BottomNavigation(navController)
    }) {
        BottomNavigationGraph(navController, navHostControllerRoot, it)
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomNavigation(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarDestination = AppComponents.list.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {

        NavigationBar(
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            AppComponents.list.forEachIndexed { _, bottomNavigationItem ->

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
                                contentDescription = bottomNavigationItem.title
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
    }
}