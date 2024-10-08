package com.jaideep.expensetracker.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.CurrencyRupee
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.jaideep.expensetracker.R

object AppComponents {

    val list = listOf(
        BottomNavigationItem(
            "Home", Icons.Filled.Home, Icons.Outlined.Home, MainScreen.HOME
        ), BottomNavigationItem(
            "Transactions",
            Icons.Filled.CurrencyRupee,
            Icons.Outlined.CurrencyRupee,
            MainScreen.TRANSACTION
        ), BottomNavigationItem(
            "Category", Icons.Filled.Category, Icons.Outlined.Category, MainScreen.CATEGORY
        )
    )

    fun getCategoryIconId(iconName: String): Int {
        return when (iconName) {
            "Food" -> R.drawable.food
            "Fuel" -> R.drawable.fuel
            "Entertainment" -> R.drawable.entertainment
            "Shopping" -> R.drawable.shopping
            "Travel" -> R.drawable.travel
            else -> R.drawable.category
        }
    }
}

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)